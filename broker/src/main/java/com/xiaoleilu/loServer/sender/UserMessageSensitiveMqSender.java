package com.xiaoleilu.loServer.sender;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.moquette.BrokerConstants;
import io.moquette.server.config.RabbitMqConfig;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class UserMessageSensitiveMqSender {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(UserMessageSensitiveMqSender.class);

    public static void send(Object context) throws Exception {
        String json = new ObjectMapper().writeValueAsString(context);
        RabbitMqConfig.channel.basicPublish(BrokerConstants.MQ_EXCHANGE_USER_MESSAGE_SENSITIVE, BrokerConstants.MQ_TOPIC_ROUTING_USER_MESSAGE_SENSITIVE, null, json.getBytes("UTF-8"));
        LOG.info("UserMessageSensitiveMqSender send json: {}", json);
    }
}
