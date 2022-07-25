package cn.itcast.mq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 广播消息队列
 * 通过这个配置类，我们通过@Bean的方式声明：交换机、队列、以及交换机和队列的绑定关系，
 * 项目启动，Spring读取到这些Bean，就会帮助我们到RabbitMQ中去声明交换机、队列、以及交换机和队列的绑定关系：
 * 证明，运行程序，到RabbitMQ管理平台就一目了然
 */
@Configuration
public class FanoutConfig {

    /**
     * 声明交换机：hyh.fanout
     *
     * @return
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("hyh.fanout");
    }

    /**
     * 声明队列：fanout.queue1
     *
     * @return
     */
    @Bean
    public Queue fanoutQueue1() {
        return new Queue("fanout.queue1");
    }

    /**
     * 绑定队列fanout.queue1到交换机hyh.fanout
     *
     * @return
     */
    @Bean
    public Binding fanoutBinding1(Queue fanoutQueue1, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutQueue1).to(fanoutExchange);
    }

    /**
     * 声明队列：fanout.queue2
     */
    @Bean
    public Queue fanoutQueue2() {
        return new Queue("fanout.queue2");
    }

    /**
     * 绑定队列fanout.queue2到交换机hyh.fanout
     *
     * @return
     */
    @Bean
    public Binding fanoutBinding2(Queue fanoutQueue2, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutQueue2).to(fanoutExchange);
    }

    @Bean
    public Queue ObjectQueue() {
        return new Queue("object.queue");
    }
}
