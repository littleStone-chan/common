package com.chen.tools.rabbitmq.callback;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * 消息未路由到队列监听类
 * @author by peng
 * @date in 2019-06-01 21:32
 */
@Component
public class SendReturnCallback implements RabbitTemplate.ReturnCallback {
    
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        System.err.println("Fail... message:{},从交换机exchange:{},以路由键routingKey:{}," +
                        "未找到匹配队列，replyCode:{},replyText:{}");
    }
}