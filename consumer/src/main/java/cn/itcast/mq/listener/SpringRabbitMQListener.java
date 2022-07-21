package cn.itcast.mq.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
public class SpringRabbitMQListener {
    /**
     * 消费者要做的事情是去监听消息，那么Spring已经替我们跟RabbitMQ建立了连接，你唯一要操心的是就是：
     * 你要监听什么队列；
     * 监听到队列要干什么事；
     * @param message 发消息时发的是什么，接收的时候就是什么，spring会帮我们处理
     */
    @RabbitListener(queues = "simple.queue")
    public void listSimpleQueue(String message) {
        System.out.println("消费者接收到simple.queue的消息【" + message +"】");
    }
}
