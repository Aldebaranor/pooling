package com.soul.pooling.mqtt.config;

import com.soul.online.mqtt.subscribe.MqttHashMsgSubscribe;
import com.soul.online.mqtt.subscribe.MqttListMsgSubscribe;
import com.soul.online.mqtt.subscribe.MqttMsgSubscribe;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

/**
 * @ClassName: MqttSubscribeConfig
 * @Description: mqtt订阅消息配置
 * @Author: jodi
 * @Date: 2021/6/29 15:51
 * @Version: 1.0
 */
@Configuration
@ConditionalOnProperty(value = "mqtt.subscribe.enabled", havingValue = "true")
@ConfigurationProperties(prefix = "mqtt.subscribe")
@Data
public class MqttSubscribeConfig {

    public static final String OUTBOUND_CHANNEL = "mqttOutboundChannel";
    public static final String OUTBOUND_CHANNEL_HASH = "mqttOutboundChannelHash";
    public static final String OUTBOUND_CHANNEL_LIST = "mqttOutboundChannelList";

    private String consumerValueTopics="juntai_zhuhai_test";

    private String consumerHashTopics="juntai_zhuhai_test";

    private String consumerListTopics="juntai_zhuhai_test";

    private int defaultQos;

    private int completionTimeout;


    private final MqttMsgSubscribe subscriber;


    private final MqttHashMsgSubscribe subscriberHash;


    private final MqttListMsgSubscribe subscriberList;



    /**
     * 指定订阅的主题,理解为消息的生产者
     * @param factory mqtt客户端
     * @return
     */
    @Bean(name = "channelInbound")
    public MessageProducer channelInbound( MqttPahoClientFactory factory) {
        String clientId = "mqtt-subscribe-" + System.currentTimeMillis();
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(clientId, factory, consumerValueTopics.split(","));
        adapter.setCompletionTimeout(completionTimeout);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(defaultQos);
        adapter.setOutputChannel(mqttSubscribeChannel());
        return adapter;
    }

    @Bean(name = "channelInboundList")
    public MessageProducer channelInboundList( MqttPahoClientFactory factory) {
        String clientId = "mqtt-subscribe-list-" + System.currentTimeMillis();
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(clientId, factory, consumerListTopics.split(","));
        adapter.setCompletionTimeout(completionTimeout);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(defaultQos);
        adapter.setOutputChannel(mqttSubscribeChannelList());
        return adapter;
    }

    @Bean(name = "channelInboundHash")
    public MessageProducer channelInboundHash( MqttPahoClientFactory factory) {
        String clientId = "mqtt-subscribe-hash-" + System.currentTimeMillis();
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(clientId, factory, consumerHashTopics.split(","));
        adapter.setCompletionTimeout(completionTimeout);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(defaultQos);
        adapter.setOutputChannel(mqttSubscribeChannelHash());
        return adapter;
    }

    /**
     * 订阅消息处理类
     * @return
     */
    @Bean
    @ServiceActivator(inputChannel = OUTBOUND_CHANNEL)
    public MessageHandler mqttMessageHandler(){
        return subscriber;
    }
    /**
     * 订阅消息处理类
     * @return
     */
    @Bean
    @ServiceActivator(inputChannel = OUTBOUND_CHANNEL_HASH)
    public MessageHandler mqttMessageHandlerHash(){
        return subscriberHash;
    }
    /**
     * 订阅消息处理类
     * @return
     */
    @Bean
    @ServiceActivator(inputChannel = OUTBOUND_CHANNEL_LIST)
    public MessageHandler mqttMessageHandlerList(){
        return subscriberList;
    }

    /**
     * mqtt消息订阅消息通道
     *
     * @return
     */
    @Bean(value = OUTBOUND_CHANNEL)
    public MessageChannel mqttSubscribeChannel() {
        return new DirectChannel();
    }

    /**
     * mqtt消息订阅消息通道
     *
     * @return
     */
    @Bean(value = OUTBOUND_CHANNEL_HASH)
    public MessageChannel mqttSubscribeChannelHash() {
        return new DirectChannel();
    }
    /**
     * mqtt消息订阅消息通道
     *
     * @return
     */
    @Bean(value = OUTBOUND_CHANNEL_LIST)
    public MessageChannel mqttSubscribeChannelList() {
        return new DirectChannel();
    }
}
