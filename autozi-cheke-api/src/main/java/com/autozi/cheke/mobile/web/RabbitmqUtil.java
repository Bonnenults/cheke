package com.autozi.cheke.mobile.web;

import com.alibaba.fastjson.JSONObject;
import com.autozi.cheke.article.entity.ArticleMqEntity;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.amqp.support.converter.JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

/**
 * rabbitMQ工具类
 */
@Component
public class RabbitmqUtil {

    /**
     * 销售收货确认结算rabbitmq日志
     */
    private static Logger logger = LoggerFactory.getLogger("RABBITMQ");

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * @param queueName
     * @param object
     * @Title: sendMessageInJsonToQueue
     * @Description: 发送消息到队列
     * @author wei.wang
     * @date 2017年04月25日13:46:26
     */
    public void sendMessageInJsonToQueue(String queueName, Object object) {
        rabbitTemplate.setMessageConverter(new JsonMessageConverter());
        rabbitTemplate.convertAndSend(queueName, object);
        rabbitTemplate.setChannelTransacted(false);
    }

    /**
     * 发送 文章访问事件到mq
     * @param articleMqEntity
     */
    @SuppressWarnings("unchecked")
	public void sendMessageInJsonToExchange(ArticleMqEntity articleMqEntity) {
        try {
            logger.info("RabbitMQUtil.sendMessageInJsonToExchange## 消息[ " + JSONObject.toJSONString(articleMqEntity) + " ],发送消息到交换机开始");
            rabbitTemplate.setMessageConverter(new JsonMessageConverter());
            CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
            rabbitTemplate.correlationConvertAndSend(articleMqEntity, correlationId);//exchange:交换机名称, routingKey:路由关键字, object:发送的消息内容 ,correlationData:消息ID
            rabbitTemplate.setChannelTransacted(false);
            logger.info("RabbitMQUtil.sendMessageInJsonToExchange## 消息[ " + JSONObject.toJSONString(articleMqEntity) + " ],发送消息到交换机结束");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param channel
     * @param deliveryTag
     * @Title: basicAck
     * @Description: 消息确认-消息从队列中删除
     * @author wei.wang
     * @date 2017年04月25日13:47:19
     */
    public void basicAck(Channel channel, long deliveryTag) {
        try {
            logger.info("RabbitMQUtil.basicAck## 消息确认开始");
            channel.basicAck(deliveryTag, Boolean.FALSE);
            logger.info("RabbitMQUtil.basicAck## 消息确认结束");
        } catch (IOException e) {
            logger.error("RabbitMQUtil.basicAck## 消息确认处理异常,异常信息" + e.getMessage());
        }
    }

    /**
     * @param channel
     * @param deliveryTag
     * @Title: basicNack
     * @Description: 消息确认-消息重新放回到队列
     * @author wei.wang
     * @date 2017年04月25日13:47:19
     */
    public void basicNack(Channel channel, long deliveryTag) {
        try {
            logger.info("RabbitMQUtil.basicNack## 消息确认开始");
            channel.basicNack(deliveryTag, Boolean.FALSE, Boolean.TRUE);
            logger.info("RabbitMQUtil.basicNack## 消息确认结束");
        } catch (IOException e) {
            logger.error("RabbitMQUtil.basicNack## 消息确认处理异常,异常信息" + e.getMessage());
        }
    }

}
