package com.xiaoleilu.loServer.sender;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.moquette.BrokerConstants;
import io.moquette.server.config.RabbitMqConfig;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class UserMessageSendMqSender {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(UserMessageSendMqSender.class);

    public static void send(Object context) throws Exception {
        String json = new ObjectMapper().writeValueAsString(context);
        RabbitMqConfig.channel.basicPublish(BrokerConstants.MQ_EXCHANGE_USER_MESSAGE_SEND, BrokerConstants.MQ_TOPIC_ROUTING_USER_MESSAGE_SEND, null, json.getBytes("UTF-8"));
//        LOG.info("UserMessageSendMqSender send json: {}", json);
    }
}
