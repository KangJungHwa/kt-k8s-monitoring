package com.kt.monitoring.util;


import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

//메세지 큐 발생을 위한 테스트 클래스
//@Component
public class RabbitRunner implements CommandLineRunner{

    private static final String topicExchange = "topic.nlu";
    private static final String sendRoutingKey="routekey.send";
    private static final String receiveRoutingKey="routekey.receive";


    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public void run(String... args) {
        amqpTemplate.convertAndSend(topicExchange, sendRoutingKey, "send second message success~~~");

        System.out.println("Message sent to the RabbitMQ Topic Exchange Successfully") ;

        amqpTemplate.convertAndSend(topicExchange, receiveRoutingKey, "receive second message success~~~");

        System.out.println("Message receive to the RabbitMQ Topic Exchange Successfully") ;

    }


}
