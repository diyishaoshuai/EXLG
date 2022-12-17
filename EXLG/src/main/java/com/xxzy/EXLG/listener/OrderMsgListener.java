package com.xxzy.EXLG.listener;

import com.rabbitmq.client.Channel;
import com.xxzy.EXLG.common.utils.thirdParty.TencentMessageService;
import com.xxzy.EXLG.entity.OrderEntity;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Random;

/**
 *  发送短信
 */
@Component
@RabbitListener(queues = "order.msg.queue")
public class OrderMsgListener {

    @Autowired
    private TencentMessageService tencentMessageService;

    @RabbitHandler
    public void listener(OrderEntity orderEntity, Channel channel, Message message) throws IOException {
        try {
            //  随机生成验证码  截取时间戳
            String signCode = "";
            Random random = new Random();
            for (int i = 0; i < 6; i++) {
                signCode += random.nextInt(10);
                if (i == 1){
                    signCode += "-";
                }
            }
            tencentMessageService.sendMessage(orderEntity.getReceiveUserTel(),signCode);
            /**
             *  表示成功确认,使用此回执方法后,消息会被broker删除
             *  参数一(long): deliveryTag：表示消息投递序号，每次消费消息或者消息重新投递后，deliveryTag都会增加。手动消息确认模式下，我们可以对指定deliveryTag的消息进行ack、nack、reject等操作。
             *  参数二(boolean): multiple：是否批量确认，值为 true 则会一次性删除ack小于当前消息的deliveryTag的消息。
             */
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            /**
             *  消息被拒绝后
             * 参数一:  deliveryTag：表示消息投递序号。
             * 参数二:  requeue：值为 true 消息将重新入队列。
             */
            channel.basicReject(message.getMessageProperties().getDeliveryTag(),true);
        }
    }
}
