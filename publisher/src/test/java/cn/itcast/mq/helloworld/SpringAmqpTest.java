package cn.itcast.mq.helloworld;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

// @RunWith(SpringRunner.class) // 注释掉，junit5不用加RunWith
@SpringBootTest
public class SpringAmqpTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 基本消息队列：HelloWorld
     */
    @Test
    void testSendMessage2SimpleQueue() {
        String queueName = "simple.queue";
        String message = "hello, spring amqp";
        // 这段代码没有队列不会自动创建
        rabbitTemplate.convertAndSend(queueName, message);
    }


    /**
     * 工作消息队列：Work Queue
     */
    @Test
    void testSendMessage2WorkQueue() throws InterruptedException {
        String queueName = "simple.queue";
        String message = "hello, message_";
        for (int i = 0; i < 50; i++) {
            // 这段代码没有队列不会自动创建
            rabbitTemplate.convertAndSend(queueName, message + i);
            // 大体模拟1秒发50条消息
            Thread.sleep(20);
        }
    }

    /**
     * 广播Fanout：Fanout Exchange
     */
    @Test
    void testSendFanoutExchange() throws InterruptedException {
        // 交换机名
        String exchangeName = "hyh.fanout";
        // 消息
        String message = "hello, every one!";
        // 发送消息，参数分别是：交换机名称、RoutingKey（暂时为空）、消息
        rabbitTemplate.convertAndSend(exchangeName, "", message);
    }

    /**
     * 路由Direct：Direct Exchange
     */
    @Test
    void testSendDirectExchange() throws InterruptedException {
        // 交换机名
        String exchangeName = "hyh.direct";
        // 消息
        String message = "hello, red!";
        // RoutingKey
        String routingKey = "red";
        // 发送消息，参数分别是：交换机名称、RoutingKey（暂时为空）、消息
        rabbitTemplate.convertAndSend(exchangeName, routingKey, message);
    }
}
