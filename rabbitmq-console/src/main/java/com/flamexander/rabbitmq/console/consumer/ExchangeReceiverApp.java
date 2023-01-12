package com.flamexander.rabbitmq.console.consumer;

import com.rabbitmq.client.*;

import java.util.ArrayList;
import java.util.Scanner;

public class ExchangeReceiverApp {
    private static final String EXCHANGE_NAME = "directExchanger";
    public static ArrayList<String> listTopic = new ArrayList<String>();

    public static void main(String[] argv) throws Exception {


        String userMessage = "";
        String topic = "";
        do {
            System.out.println("Введите команду "); //'set_topic php'
            Scanner in = new Scanner(System.in);
            userMessage = in.nextLine();
        }
        while (userMessage.isEmpty());


        String[]  userMessageSplit = userMessage.split(" ");

        topic = userMessageSplit[1];
        for (String list : listTopic){
            if (!list.equals(topic)){
                listTopic.add(topic);
            }
        }




        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
// проверяем что существкет
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
// имя очереди
        String queueName = channel.queueDeclare().getQueue();
        System.out.println("My queue name: " + queueName);
// привязываемся с темой php
       // String n = "php";
        channel.queueBind(queueName, EXCHANGE_NAME, topic);

        System.out.println(" [*] Waiting for messages");
        // пробовала добавить подписку но не получилось наверное нужно в новом потоке делать Scaner блокирует и сообщения не появляются по подписанным темам
// ждем сообщение и обрабатываем
//        System.out.println("если хотите: \n отписалься от темы  введите 1 и тему через пробел \n подписаться на тему- 2 и тему через пробел");
//        Scanner in = new Scanner(System.in);
//        String userText = in.nextLine();
//
//        if (!userText.isEmpty())  {
//            String[]  userTextSplit = userMessage.split(" ");
//
//            if (userText.startsWith("2")){
//                String subscription = userTextSplit[2];
//                for (String list : listTopic){
//                    if (list.equals(subscription)){
//                        return;
//                    }
//                    else {
//                        channel.queueBind(queueName, EXCHANGE_NAME, subscription);
//                        listTopic.add(subscription);
//                    }
//                }
//
//            }
//            else if (userText.startsWith("1")){
//                String subscription = userTextSplit[2];
//                for (String list : listTopic){
//                    if (list.equals(subscription)){
//                        listTopic.remove(subscription);
//                    }
//                }
//            }
//            else {
//                System.out.println("Вы не ввели нужное значение");}
//        }

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(Thread.currentThread().getName());
            System.out.println(" [x] Received '" + message + "'");
        };


        //        System.out.println(Thread.getAllStackTraces().keySet());
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });

// две темы на одной очереди
       //  channel.queueBind(queueName, EXCHANGE_NAME, "c++");
    }
}
