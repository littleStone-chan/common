package com.o2osys.tools.rabbitmq.config;

import com.o2osys.tools.rabbitmq.callback.SendConfirmCallback;
import com.o2osys.tools.rabbitmq.callback.SendReturnCallback;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value(value = "${spring.rabbitmq.host:192.168.0.158 }")
    private String host;

    @Value(value = "${spring.rabbitmq.port:5672}")
    private String port;

    @Value(value = "${spring.rabbitmq.username:guest}")
    private String username;

    @Value(value = "${spring.rabbitmq.password:guest}")
    private String password;

    @Value(value = "${spring.rabbitmq.virtual-host:/}")
    private String virtualHost;

    @Autowired
    SendConfirmCallback sendConfirmCallback;

    @Autowired
    SendReturnCallback sendReturnCallback;

	@Bean
	public ConnectionFactory connectionFactory(){
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		connectionFactory.setAddresses(host + ":" + port);
		connectionFactory.setUsername(username);
		connectionFactory.setPassword(password);
		connectionFactory.setVirtualHost(virtualHost);
		return connectionFactory;
	}
	
	@Bean
	public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
		RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
		rabbitAdmin.setAutoStartup(true);
		return rabbitAdmin;
	}


    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback(sendConfirmCallback);
        rabbitTemplate.setReturnCallback(sendReturnCallback);
        return rabbitTemplate;
    }

}
