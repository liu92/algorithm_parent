/**
 * Copyright (C), 2018-2019, XXX有限公司
 * FileName: Send1
 * Author:   lin
 * Date:     2019/3/6 12:40
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.mq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
/**
 * 功能描述: <br>
 * 〈〉
 普通confirm模式
 * @return:
 * @since: 1.0.0
 * @Author:lin
 * @Date: 2019/3/6 12:55
 */

public class Send1 {


    /**
     定义交换器的名字
     */
    private static final  String EXCHANGE_NAME = "test_exchange";
    private static final  String QUEUE_NAME = "queue_name";
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
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
         * 定义交互机
         *
         */
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        //声明创建队列
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        /**
         * 定义一组路由键
         */
        String routingKey = "error";
        /**
         *  发送的消息
         */
        String message = "Hello world";

        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, routingKey);

        channel.basicPublish(EXCHANGE_NAME, routingKey, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        System.out.println("Sent '"+routingKey +":" + message + "'");
        /**
         * 普通confirm模式：每发送一条消息后，调用waitForConfirms()方法，等待服务器端confirm。实际上是一种串行confirm了。
         *
         * 这里是第一种模式：普通confirm模式
         */
        if(!channel.waitForConfirms()){
            System.out.println("send message failed....");
        }

        /**
         * 关闭频道和连接
         */
        channel.close();
        connection.close();
    }
}
