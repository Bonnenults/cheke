package com.autozi.cheke.mobile.web;

import com.autozi.cheke.article.entity.ArticleMqEntity;
import com.autozi.cheke.mobile.facade.ArticleRedisApiFacade;
import com.rabbitmq.client.Channel;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * mq消息监听
 */
public class RabbitmqReceiver implements ChannelAwareMessageListener {

    /**
     * 销售收货确认结算rabbitmq日志
     */
    private static Logger logger = LoggerFactory.getLogger("RABBITMQ");

    @Autowired
    private RabbitmqUtil rabbitmqUtil;

    @Autowired
    private ArticleRedisApiFacade articleRedisApiFacade;

    /**
     * @param channel
     * @Title: handMessage
     * @Description: 处理单个销售订单
     * @author wei.wang
     * @date 2017年04月25日17:33:55
     */
    public void handMessage(final ArticleMqEntity articleMqEntity, Channel channel, long deliveryTag) {
        try {
            if (articleMqEntity != null) {
                logger.info("MqReceiver.handMessage## 文章点击事件[" + articleMqEntity.getPkId() + "]处理开始");

                //处理统计countInfo、order、share表中数据
                articleRedisApiFacade.countOrderShareMq(articleMqEntity.getArticleId(),articleMqEntity.getUserId(),articleMqEntity.getShareType());

                rabbitmqUtil.basicAck(channel, deliveryTag);
                logger.info("MqReceiver.handMessage## 文章点击事件[" + articleMqEntity.getPkId() + "]处理结束");
            } else {
                logger.error("MqReceiver.handMessage## 文章点击事件NULL消息未处理成功");
            }
        } catch (Exception e) {
            logger.error("MqReceiver.handMessage## 文章点击事件[" + articleMqEntity.getPkId() + "],异常信息" + e.getMessage());
            e.printStackTrace();
            rabbitmqUtil.basicNack(channel, deliveryTag);
        }
    }

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        try {
            logger.info("MqReceiver.onMessage## 消息[ " + message.toString() + " ],处理开始");
            byte[] body = message.getBody();
            long deliveryTag = message.getMessageProperties().getDeliveryTag();
            ObjectMapper objectMapper = new ObjectMapper().setVisibility(JsonMethod.FIELD, Visibility.ANY);
            objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            ArticleMqEntity articleMqEntity = objectMapper.readValue(body, ArticleMqEntity.class);
            handMessage(articleMqEntity, channel, deliveryTag);
            logger.info("MqReceiver.onMessage## 消息[ " + message.toString() + " ],处理结束");
        } catch (Exception e) {
            logger.error("MqReceiver.onMessage## 异常消息[ " + message.toString() + " ],异常信息" + e.getMessage());
        }
    }

}
