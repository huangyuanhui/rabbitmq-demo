package cn.itcast.mq.listener;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
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
     *
     * @param message
     */
    @RabbitListener(queues = "fanout.queue1")
    public void listenFanoutQueue1(String message) {
        System.out.println("消费者1接收到fanout.queue1的消息【" + message + "】");
    }

    /**
     * 广播Fanout：监听fanout.queue2
     *
     * @param message
     */
    @RabbitListener(queues = "fanout.queue2")
    public void listenFanoutQueue2(String message) {
        System.err.println("消费者2接收到fanout.queue2的消息【" + message + "】");
    }


    /**
     * (在括号里 ctrl + p )
     * 路由Direct
     *
     * 运行后，会发现RabbitMQ管理平添自动创建好对应的交换机、队列、以及交换机和队列的绑定关系
     * ，也可以证明，交换机、队列、交换机和队列的绑定关系为啥更适合在消费者出声明！
     *
     * @param message
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "direct.queue1"),  // 队列名（创建队列）
            // 交换机名（创建Direct路由类型的交换机， 默认就是路由类型）
            exchange = @Exchange(name = "hyh.direct", type = ExchangeTypes.DIRECT),
            key = {"red", "blue"}  // RoutingKey
    ))
    public void listenDirectQueue1(String message) {
        System.out.println("消费者1接收到direct.queue1的消息【" + message + "】");
    }

    /**
     * 路由Direct
     *
     * @param message
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "direct.queue2"),
            exchange = @Exchange(name = "hyh.direct", type = ExchangeTypes.DIRECT),
            key = {"red", "yellow"}
    ))
    public void listenDirectQueue2(String message) {
        System.err.println("消费者2接收到direct.queue2的消息【" + message + "】");
    }
}
