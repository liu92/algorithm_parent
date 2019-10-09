package com.mq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * MQ 消息推送者
 */
public class Send {



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
         .通过信道声明一个交换器 第一个参数时交换器的名字 第二个参数时交换器的种类
         */

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        //声明创建队列


        /**
         *
         *  RabbitMQ可以给消息和RabbitMQ设置TTL。 目前有两种方法可以设置，第一种方法是通过队列设置，
         *  队列中所有消息都有相同的过期时间，第二种是对消息进行单独设置。每条消息TTL可以不同。如果上述两种方法同时使用，
         *  则消息的过期时间以两者之间TTL较小的那个数值为准。消息队列中生存时间一旦超过设置的TTL值，就称为dead message, 消费者将无法再收到该消息。
         */

         Map<String, Object> argss = new HashMap<String, Object>();
         argss.put("vhost", "/");
         argss.put("username","root");
         argss.put("password", "root");
         /** 设置过期时间，
         // 如果不设置TTL,则表示此消息不会过期。
          如果将TTL设置为0，则表示除非此时可以直接将消息投递到消费者，
          否则该消息会被立即丢弃，这个特性可以部分替代RabbitMQ3.0以前支持的immediate参数，
          之所以所部分代替，是应为immediate参数在投递失败会有basic.return方法将消息体返回（这个功能可以利用死信队列来实现）。
         */


         argss.put("x-message-ttl",6000);

         channel.queueDeclare(QUEUE_NAME, true, false, false, null);



        String[] routingKeys = {"error","info","warning"};
        int count = 3;

        channel.confirmSelect();
        for (int i = 0; i < count; i++) {
            /**
             *  发送的消息
             */
            String message = "Hello world"+i;

            /**
             路由
             */

            String routingKey = routingKeys[i];
            /**
             * //往队列中发出一条消息
             * 1、先来测试消息 不是持久化的 情况
             *
             * 这里是有 MessageProperties.PERSISTENT_TEXT_PLAIN 表示的是消息持久化,也就是 deliveryMode =2
             */
            channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,routingKey);


            /**
             * 功能描述: <br>
             发送消息的时候设置消息deliverMode 将其设置为2 ，就是将消息设置为持久化，
             */

            channel.basicPublish(EXCHANGE_NAME, routingKey, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            System.out.println("Sent '"+routingKey +":" + message + "'");
        }

        /**
         * 1、普通confirm模式：每发送一条消息后，调用waitForConfirms()方法，等待服务器端confirm。实际上是一种串行confirm了。
         * 2、批量confirm模式：每发送一批消息后，调用waitForConfirms()方法，等待服务器端confirm。
         * 3、异步confirm模式：提供一个回调方法，服务端confirm了一条或者多条消息后Client端会回调这个方法。
         *
         * 这里是第二种模式：批量模式
         */
         if(!channel.waitForConfirms()){
           System.out.println("send message failed.");
         }

        /**
         * 关闭频道和连接
         */
        channel.close();
        connection.close();
    }
}
