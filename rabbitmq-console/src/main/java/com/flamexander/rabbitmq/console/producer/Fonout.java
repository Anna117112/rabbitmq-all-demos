package com.flamexander.rabbitmq.console.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
//Lesson9 Spring_2
public class Fonout {
    // создаем очередь
    private final static String QUEUE_NAME = "hello";
    // обменник
    private final static String EXCHANGER_NAME = "hello_exchanger";

    public static void main(String[] args) throws Exception {
        // фабрика соединений
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            // создаем на сервере MQ
            channel.exchangeDeclare(EXCHANGER_NAME, BuiltinExchangeType.DIRECT);
            // создаем очередь
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            // привязываем  EXCHANGER к очереди с ключом java
            channel.queueBind(QUEUE_NAME, EXCHANGER_NAME, "java");

            String message = "Hello World!";
            channel.basicPublish(EXCHANGER_NAME, "java", null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
























