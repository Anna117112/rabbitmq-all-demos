package com.flamexander.rabbitmq.console.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Scanner;

public class DirectExchange {
    private static final String EXCHANGE_NAME = "directExchanger";

    public static void main(String[] argv) throws Exception {
        // можно не создавать EXCHANGE а пользоваться готовыми если он уже есть
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
//            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

            String message = "info: Hello World!";
            String topic = "php";
            // еслм вводить в консоли тему и сообщение
//            String userMessage = "";
//            do {
//            System.out.println("Введите тему и сообщение через пробел ");
//            Scanner in = new Scanner(System.in);
//             userMessage = in.nextLine();
 //           }
//             while (userMessage.isEmpty()) {
//            String[] userMessageSplit = userMessage.split(" ");
//            String topic = userMessageSplit[0];
//            int topicLenght = topic.length();
//            String message = userMessage.substring(topicLenght + 1);
            channel.basicPublish(EXCHANGE_NAME, topic, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + message + "'");


//            channel.basicPublish(EXCHANGE_NAME, "programming.best-practices.java", null, message.getBytes("UTF-8"));


        }
    }
}