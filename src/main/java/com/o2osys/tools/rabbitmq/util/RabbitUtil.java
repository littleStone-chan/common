package com.o2osys.tools.rabbitmq.util;

import com.alibaba.fastjson.JSON;
import com.o2osys.tools.base.BaseException;
import com.o2osys.tools.commons.StringUtils;
import com.o2osys.tools.token.AccessTokenUtil;
import com.o2osys.tools.util.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class RabbitUtil {
    
    private static final Logger logger = LoggerFactory.getLogger(RabbitUtil.class);

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 转换Message对象
     * @param messageType 返回消息类型 MessageProperties类中常量
     * @param msg
     * @return
     */
    public Message getMessage(String messageType, Object msg){
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(messageType);
        Message message = new Message(JSON.toJSONString(msg).getBytes(),messageProperties);
        message.getMessageProperties().setHeader("X-uid",WebUtil.getUid());
        message.getMessageProperties().setHeader("X-token",WebUtil.getAccessToken().getToken());
        return message;
    }
 
    /**
     * 有绑定Key的Exchange发送
     * @param routingKey
     * @param msg
     */
    public void sendMessageToExchange(TopicExchange topicExchange, String routingKey, Object msg){
        Message message = getMessage(MessageProperties.CONTENT_TYPE_JSON,msg);
        rabbitTemplate.send(topicExchange.getName(), routingKey, message);
    }
 
    /**
     * 没有绑定KEY的Exchange发送
     * @param exchange
     * @param msg
     */
    public void sendMessageToExchange(TopicExchange topicExchange, AbstractExchange exchange, String msg){
        addExchange(exchange);
        logger.info("RabbitMQ send "+exchange.getName()+"->"+msg);
        rabbitTemplate.convertAndSend(topicExchange.getName(),msg);
    }
 
    /**
     * 给queue发送消息
     * @param queueName
     * @param msg
     */
    public void sendToQueue(String queueName, String msg){
        sendToQueue(DirectExchange.DEFAULT, queueName, msg);
    }
    
    /**
     * 给direct交换机指定queue发送消息
     * @param directExchange
     * @param queueName
     * @param msg
     */
    public void sendToQueue(DirectExchange directExchange, String queueName, String msg){
        Queue queue = new Queue(queueName);
        addQueue(queue);
        Binding binding = BindingBuilder.bind(queue).to(directExchange).withQueueName();
        rabbitAdmin.declareBinding(binding);
        //设置消息内容的类型，默认是 application/octet-stream 会是 ASCII 码值
        rabbitTemplate.convertAndSend(directExchange.getName(), queueName, msg);
    }
    
    /**
     * 给queue发送消息
     * @param queueName
     */
    public String receiveFromQueue(String queueName){
        return receiveFromQueue(DirectExchange.DEFAULT, queueName);
    }
    
    /**
     * 给direct交换机指定queue发送消息
     * @param directExchange
     * @param queueName
     */
    public String receiveFromQueue(DirectExchange directExchange, String queueName){
        Queue queue = new Queue(queueName);
        addQueue(queue);
        Binding binding = BindingBuilder.bind(queue).to(directExchange).withQueueName();
        rabbitAdmin.declareBinding(binding);
        String messages = (String)rabbitTemplate.receiveAndConvert(queueName);
        System.out.println("Receive:"+messages);
        return messages;
    }
    
    /**
     * 创建Exchange
     * @param exchange
     */
    public void addExchange(AbstractExchange exchange){
        rabbitAdmin.declareExchange(exchange);
    }
 
    /**
     * 删除一个Exchange
     * @param exchangeName
     */
    public boolean deleteExchange(String exchangeName){
        return rabbitAdmin.deleteExchange(exchangeName);
    }
 
 
    /**
     * Declare a queue whose name is automatically named. It is created with exclusive = true, autoDelete=true, and
     * durable = false.
     * @return Queue
     */
    public Queue addQueue(){
        return rabbitAdmin.declareQueue();
    }
 
    /**
     * 创建一个指定的Queue
     * @param queue
     * @return queueName
     */
    public String addQueue(Queue queue){
        return rabbitAdmin.declareQueue(queue);
    }
 
    /**
     * Delete a queue.
     * @param queueName the name of the queue.
     * @param unused true if the queue should be deleted only if not in use.
     * @param empty true if the queue should be deleted only if empty.
     */
    public void deleteQueue(String queueName, boolean unused, boolean empty){
        rabbitAdmin.deleteQueue(queueName,unused,empty);
    }
 
    /**
     * 删除一个queue
     * @return true if the queue existed and was deleted.
     * @param queueName
     */
    public boolean deleteQueue(String queueName){
        return rabbitAdmin.deleteQueue(queueName);
    }
 
    /**
     * 绑定一个队列到一个匹配型交换器使用一个routingKey
     * @param queue
     * @param exchange
     * @param routingKey
     */
    public void addBinding(Queue queue ,TopicExchange exchange,String routingKey){
        Binding binding = BindingBuilder.bind(queue).to(exchange).with(routingKey);
        rabbitAdmin.declareBinding(binding);
    }
    
    /**
     * 绑定一个Exchange到一个匹配型Exchange 使用一个routingKey
     * @param exchange
     * @param topicExchange
     * @param routingKey
     */
    public void addBinding(Exchange exchange,TopicExchange topicExchange,String routingKey){
        Binding binding = BindingBuilder.bind(exchange).to(topicExchange).with(routingKey);
        rabbitAdmin.declareBinding(binding);
    }
    
    /**
     * 去掉一个binding
     * @param binding
     */
    public void removeBinding(Binding binding){
        rabbitAdmin.removeBinding(binding);
    }

    /**
     * 校验 uid 和 token 信息
     * 失败 会抛出异常
     */
    public void vaildUidToken(org.springframework.messaging.Message message){

        MessageHeaders headers = message.getHeaders();
        if (ObjectUtils.isEmpty(headers.get("contentType"))) {
            throw new BaseException("请求头contentType为空");
        }
        String contentType = (String) headers.get("contentType");
        if (!StringUtils.equals(contentType, MessageProperties.CONTENT_TYPE_JSON)) {
            throw new BaseException("请求头contentType,需要是" + MessageProperties.CONTENT_TYPE_JSON);
        }

        String uid = headers.get("X-uid").toString();
        String token = headers.get("X-token").toString();
        if (!AccessTokenUtil.certification(uid, token)) {
            throw new BaseException("登录认证失败，uid：" + uid + ",token" + token);
        }
    }
}
