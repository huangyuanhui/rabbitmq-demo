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
        String queueName = "simple.queue2";
        String message = "hello, spring amqp";
        // 这段代码没有队列不会自动创建
        rabbitTemplate.convertAndSend(queueName, message);
    }
}
