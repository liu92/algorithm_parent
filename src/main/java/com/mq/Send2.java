/**
 * Copyright (C), 2018-2019, XXX有限公司
 * FileName: Send2
 * Author:   lin
 * Date:     2019/3/6 15:58
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.mq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeoutException;

/**
 * 功能描述: <br>
 * 〈〉
 * 测试第三种：异步confirm模式：提供一个回调方法，服务端confirm了一条或者多条消息后Client端会回调这个方法。
 * @since: 1.0.0
 * @Author:lin
 * @Date: 2019/3/6 15:58
 */

public class Send2 {

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
         * 定义交互机
         *
         */
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        //声明创建队列
        Map<String, Object> mp = new HashMap<>(16);
        //设置过期时间
        mp.put("x-message-ttl",6000);

        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        /**
         * 定义一组路由键
         */
        String routingKey = "error";
        /**
         *  发送的消息
         */
        String message = "Hello world";
        /**
         * QUEUE_NAME 队列名称
         * EXCHANGE_NAME 交换器名称
         * routingKey 路由键
         */
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, routingKey);
        SortedSet<Long> confirmSet = Collections.synchronizedSortedSet(new TreeSet<Long>());

        // 将信道设置为 publisher confirm 模式
        channel.confirmSelect();
        /**
         * 功能描述: <br>
         * 〈〉
         *
         Channel对象提供的ConfirmListener()回调方法只包含deliveryTag（当前Chanel发出的消息序号）
         我们需要自己为每一个Channel维护一个unconfirm的消息序号集合，每publish一条数据，集合中元素加1，
         每回调一次handleAck方法，unconfirm集合删掉相应的一条（multiple=false）或多条（multiple=true）记录。
         从程序运行效率上看，这个unconfirm集合最好采用有序集合SortedSet存储结构。
           confirm 异步处理
         */
        channel.addConfirmListener(new ConfirmListener() {
            /**
             * Basic.Ack
             * @param deliveryTag 这个参数在 publisher confirm 模式 用来标记消息的唯一有序序号，
             *                    我们需要为每一个信道维护一个“unconfirm”的消息序号集合，每发送一条消息，集合中的元素加1.
             *                    每当调用confirmListener 中 handleAck方法时，“unconfirm”集合中删掉相应的一条（multiple设置为false）
             *                    或者多条（multiple设置为true）记录，从程序运行效率上来看，这个“unconfirm” 集合最好采用有序集合sortedSet的存储结构，
             *                    事实上，java客户端sdk 中waitForConfirms方法也是通过SortedSet维护消息序号的，
             *
             * @param multiple
             * @throws IOException
             */
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                if(multiple){
                    confirmSet.headSet(deliveryTag+1).clear();
                }else{
                    confirmSet.remove(deliveryTag);
                }
            }

            /**
             * Basic.Nack
             * @param deliveryTag
             * @param multiple
             * @throws IOException
             */
            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("Nack, SeqNo: " + deliveryTag + ", multiple: " + multiple);
                if(multiple){
                    confirmSet.headSet(deliveryTag+1).clear();
                }else{
                    confirmSet.remove(deliveryTag);
                }
                //注意这里需要添加处理消息重发 的场景
            }
        });

        // 这里演示消息一直重发 的场景
         while (true){
             long nextSeqNo = channel.getNextPublishSeqNo();
             channel.basicPublish(EXCHANGE_NAME, routingKey, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
             System.out.println("Sent '"+routingKey +":" + message + "'");
             confirmSet.add(nextSeqNo);
         }

        // 这里没有关闭 ，它会一直添加数据。
    }
}
