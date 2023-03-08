package cn.itcast.mq.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消息发送测试
 */
public class PublisherTest {
    @Test
    public void testSendMessage() throws IOException, TimeoutException {
        // 1.建立连接
        ConnectionFactory factory = new ConnectionFactory();
        // 1.1.设置连接参数，分别是：主机名、端口号、vhost、用户名、密码
        factory.setHost("120.77.168.189");
        factory.setPort(5672);
        factory.setVirtualHost("/");
        factory.setUsername("hyh");
        factory.setPassword("123456");
        // 1.2.建立连接，这行代码执行完，管理平台上Connections界面就有连接进来
        Connection connection = factory.newConnection();

        // 2.创建通道Channel，这行代码执行完，管理平台上Channels界面就有新的通道被创建
        // 创建通道可以看作是消费者或者发布者跟MQ建立连接后，消费者或者发布者跟MQ进行消息通讯的管道
        Channel channel = connection.createChannel();

        // 3.创建队列
        String queueName = "simple.queue";
        // 这行代码执行完，管理平台上Queues界面就有对应的simple.queue队列创建
        channel.queueDeclare(queueName, false, false, false, null);

        // 4.发送消息
        String message = "hello, rabbitmq!";
        // 这行代码执行完，管理平台上Queues界面点击队列名可以查看对应消息
        channel.basicPublish("", queueName, null, message.getBytes());
        System.out.println("发送消息成功：【" + message + "】");

        // 5.关闭通道和连接
        channel.close();
        connection.close();

    }
}
