package com.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 *
 */
public class RabbitMqDemo {

    private static final  String QUEUE_NAME="Queue";
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        /**
         * 创建一个连接
         */
        Connection connection = factory.newConnection();
        /**
         * 创建一个频道
         */
        Channel channel = connection.createChannel();
        /**
         *  指定一个队列
         */
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        /**
         *  发送的消息
         */
        String message = "Hello world";
        /**
         * //往队列中发出一条消息
         */
        channel.basicPublish("",QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());
        System.out.println("Sent '" + message + "'");
        /**
         * 关闭频道和连接
         */
        channel.close();
        connection.close();
    }
}
