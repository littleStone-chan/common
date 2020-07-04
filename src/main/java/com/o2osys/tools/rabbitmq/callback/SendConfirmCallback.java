package com.o2osys.tools.rabbitmq.callback;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class SendConfirmCallback implements RabbitTemplate.ConfirmCallback {
   
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            System.err.println("Success... 消息成功发送到交换机! correlationData:{}" + correlationData.toString());
        } else {
            System.err.println("Fail... 消息发送到交换机失败! correlationData:{}" + correlationData.toString());
        }
    }
}
