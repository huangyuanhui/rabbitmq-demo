package cn.itcast.mq.helloworld;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消息消费测试
 */
public class ConsumerTest {

    public static void main(String[] args) throws IOException, TimeoutException {
        // 1.建立连接
        ConnectionFactory factory = new ConnectionFactory();
        // 1.1.设置连接参数，分别是：主机名、端口号、vhost、用户名、密码
        factory.setHost("120.77.168.189");
        factory.setPort(5672);
        factory.setVirtualHost("/");
        factory.setUsername("hyh");
        factory.setPassword("123456");
        // 1.2.建立连接
        Connection connection = factory.newConnection();

        // 2.创建通道Channel
        Channel channel = connection.createChannel();

        // 3.创建队列
        String queueName = "simple.queue";
        /**
         * 问：什么明明Publisher已经声明、创建过队列了，这里Consumer为什么又要声明、创建一次？
         * 答：这是因为生产者和消费者启动的顺序是不确定的，万一消费者先启动，那么找队列就不存在，
         * 为了避免这种事发生，Publisher、Consumer都会去声明队列，但是是不会重复创建队列的！
         */
        channel.queueDeclare(queueName, false, false, false, null);

        // 4.订阅消息
        channel.basicConsume(queueName, true, new DefaultConsumer(channel) {
            /**
             * 5.处理、消费消息的回调函数，会发现在管理平台消息没了，也就是说只要消息一旦被消
             * 费掉，消息会立即被删除，即所谓的而阅后即焚，这是RabbitMQ的机制
             */
            @Override
            public void handleDelivery(String consumerTag,
                                       Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                String message = new String(body);
                System.out.println("接收到消息：【" + message + "】");
            }
        });
        System.out.println("等待接收消息。。。。");
    }
}
