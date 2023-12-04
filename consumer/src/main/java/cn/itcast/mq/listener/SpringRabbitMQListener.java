package cn.itcast.mq.listener;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Map;


@Component
public class SpringRabbitMQListener {
    /**
     * 基本消息队列：Hello World
     * 消费者要做的事情是去监听消息，那么Spring已经替我们跟RabbitMQ建立了连接，你唯一要操心的是就是：
     * 1：你要监听什么队列；
     * 2：监听到队列要干什么事；
     *
     * @param message 发消息时发的是什么，接收的时候就是什么，spring会帮我们处理
     */
    @RabbitListener(queues = "simple.queue")
    public void listenSimpleQueue(String message) {
        System.out.println("消费者接收到simple.queue的消息【" + message + "】");
    }


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
     *
     * 消息预取问题：
     *    当有大量的消息到达队列时，队列会把消息进行投递，各个consumer的通道Channel会提前把消息拿过来，
     * 就是消息预取，各个consumer的通道Channel会轮流拿消息，不管对应的Consumer能不能处理，先
     * 拿过来再说。没有考虑消费者的能力，于是各个消费者就会平均分配所有的消息。但是消费者处理消息速度有快慢，
     * 这样子会导致处理消息的时间变长。
     *
     * 解决消息预取问题：
     *    要解决这个问题就是不能让Consumer的Channel拿太多消息，应该是消费者没处理完液体个消息，再去队
     * 列拿一个！要配置：listener.simple.prefetch=1（每次只能获取一条消息，处理完成才能获取下一个消息）
     */

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
     * <p>
     * 运行后，会发现RabbitMQ管理平添自动创建好对应的交换机、队列、以及交换机和队列的绑定关系
     * ，也可以证明，交换机、队列、交换机和队列的绑定关系为啥更适合在消费者出声明！
     *
     * @param message
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "direct.queue1"),  // 队列名（创建队列）
            // 交换机名（创建Direct路由类型的交换机， 默认就是路由类型）
            exchange = @Exchange(name = "hyh.direct", type = ExchangeTypes.DIRECT),
            key = {"red", "blue"}  // RoutingKey 或叫 BindingKey
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
            key = {"red", "yellow"}  // RoutingKey 或叫 BindingKey
    ))
    public void listenDirectQueue2(String message) {
        System.err.println("消费者2接收到direct.queue2的消息【" + message + "】");
    }

    /**
     * 话题：Topic
     *
     * @param message
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "topic.queue1"),
            exchange = @Exchange(name = "hyh.topic", type = ExchangeTypes.TOPIC),
            key = "china.#"
    ))
    public void listenTopicQueue1(String message) {
        System.err.println("消费者1接收到topic.queue1的消息【" + message + "】");
    }

    /**
     * 话题：Topic
     *
     * @param message
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "topic.queue2"),
            exchange = @Exchange(name = "hyh.topic", type = ExchangeTypes.TOPIC),
            key = {"#.news"}
    ))
    public void listenTopicQueue2(String message) {
        System.err.println("消费者2接收到topic.queue2的消息【" + message + "】");
    }

    /**
     * 消息转换器
     *
     * @param msg
     */
    @RabbitListener(queues = "object.queue")
    public void listenObjectQueue(Map<String, Object> msg) {
        System.out.println("接受到object.queue的消息：" + msg);
    }
}
