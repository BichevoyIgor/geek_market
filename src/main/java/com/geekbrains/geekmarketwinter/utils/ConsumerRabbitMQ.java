package com.geekbrains.geekmarketwinter.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.stereotype.Component;

@Component
public class ConsumerRabbitMQ {
    private final static String QUEUE_NAME = "hello";

    public ConsumerRabbitMQ() {
        new Thread(() -> {
            rabbitStart();
        }).start();
    }

    private void rabbitStart() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (
                Connection connection = factory.newConnection();
                Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            while (true) {
                final DeliverCallback deliverCallback = (consumerTag, delivery) -> { //реализация приемника
                    String msg = new String(delivery.getBody(), "UTF-8");
                    System.out.println("Получил сообщение: " + msg);
                };
                boolean autoAck = true;
                channel.basicConsume(QUEUE_NAME, autoAck, deliverCallback, consumerTag -> {
                });
            }
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }
