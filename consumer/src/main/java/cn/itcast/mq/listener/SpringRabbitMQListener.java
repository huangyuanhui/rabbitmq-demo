package cn.itcast.mq.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalTime;


@Component
public class SpringRabbitMQListener {
    /**
     * 基本消息队列：Hello World
     * 消费者要做的事情是去监听消息，那么Spring已经替我们跟RabbitMQ建立了连接，你唯一要操心的是就是：
     * 你要监听什么队列；
     * 监听到队列要干什么事；
     * @param message 发消息时发的是什么，接收的时候就是什么，spring会帮我们处理
     */
   /* @RabbitListener(queues = "simple.queue")
    public void listenSimpleQueue(String message) {
        System.out.println("消费者接收到simple.queue的消息【" + message +"】");
    }*/


    /**
     * 工作队列：Work Queue
     * 消费者1每秒处理50条消息
     *
     * @param message
     */
    @RabbitListener(queues = "simple.queue")
    public void listenWorkQueue1(String message) throws InterruptedException {
        System.out.println("消费者 1111111111 接收到消息【" + message + "】" + LocalTime.now());
        Thread.sleep(20);
    }

    /**
     * 工作队列：Work Queue
     * 消费者2每秒处理10条消息，
     *
     * @param message
     */
    @RabbitListener(queues = "simple.queue")
    public void listenWorkQueue2(String message) throws InterruptedException {
        System.err.println("消费者 2222222222 接收到消息【" + message + "】" + LocalTime.now());
        Thread.sleep(100);
    }


    /**
     * 广播Fanout：监听fanout.queue1
     * @param message
     */
    @RabbitListener(queues = "fanout.queue1")
    public void listenFanoutQueue1(String message) {
        System.out.println("消费者1111111111接收消息【" + message +"】");
    }

    /**
     * 广播Fanout：监听fanout.queue2
     * @param message
     */
    @RabbitListener(queues = "fanout.queue2")
    public void listenFanoutQueue2(String message) {
        System.err.println("消费者2222222222接收到消息【" + message +"】");
    }
}
