package com.xxzy.EXLG.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 作者: gjq
 * 描述: 给MQ中添加Binding  Queue  Exchange
 */
@Configuration
public class MQConfig {
    /**
     * 腾讯云短信服务队列
     * @return
     */
    @Bean
    public Queue tencentMsgQueue() {
        return new Queue("order.msg.queue", true, false, false);
    }

    /**
     * 模式匹配
     * 处理订单的交换机
     */
    @Bean
    public Exchange orderExchange() {
        return new TopicExchange("order-event-exchange",true,false);
    }

    /**
     *  创建发送订单消息队列和订单交换机的绑定关系
     * @return
     */
    @Bean
    public Binding orderMsgBinding(){
        return new Binding(
                "order.msg.queue",
                Binding.DestinationType.QUEUE,
        "order-event-exchange",
                "order-msg-queue",
                null
        );
    }
}
