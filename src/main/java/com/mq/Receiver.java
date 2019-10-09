/**
 * Copyright (C), 2018-2019, XXX有限公司
 * FileName: Receiver
 * Author:   lin
 * Date:     2019/3/5 23:21
 * Description: 消息消费者
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.mq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 〈一句话功能简述〉<br> 
 * 〈消息消费者〉
 *
 * @author lin
 * @create 2019/3/5
 * @since 1.0.0
 */
public class Receiver {
    /**
     定义交换器的名字
     */
    private static final  String EXCHANGE_NAME = "test_exchange";

    private static final  String QUEUE_NAME = "queue_name";
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
         通过信道声明一个交换器 第一个参数时交换器的名字 第二个参数时交换器的种类
         交换机类型为 direct-exchange ，所有发送到 direct-exchange 的消息都会被转发到RouteKey指定的queue.
         RouteKey必须完全匹配。才会收到消息，否则该消息会被抛弃.
         */
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        //声明创建队列
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);


        /**
         声明一个只消费错误日志的路由键error
         */
        String routingKey = "error";

        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,routingKey);
        System.out.println("Waiting message.......");

        /*
         设置一个监听器监听消费消息
         *
         **/
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body,"UTF-8");
                String routingKey = envelope.getRoutingKey();
                System.out.println("Accept:"+routingKey+":"+message);
                String contentType = properties.getContentType();
                long deliveryTag = envelope.getDeliveryTag();
                // ack 设置为false ，然后在接收到消息后 进行显示ack操作，对于消费者来说这个设置非常有必要的。可以防止消息不必要的丢失。
                channel.basicAck(deliveryTag, false);

            }
        };

        // ack 设置成false即不自动确认。上面 已经做了处理，所以设置为false
        channel.basicConsume(QUEUE_NAME,false,consumer);
    }
}
