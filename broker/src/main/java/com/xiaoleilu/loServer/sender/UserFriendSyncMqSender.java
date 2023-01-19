package com.xiaoleilu.loServer.sender;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.moquette.BrokerConstants;
import io.moquette.server.config.RabbitMqConfig;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
public class UserFriendSyncMqSender {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(UserFriendSyncMqSender.class);

    public static Integer ADD_FRIEND = 0;
    public static Integer BLOCK_FRIEND = 2;
    public static Integer DELETE_FRIEND = 1;

    public static void send(Object context) throws Exception {
        String json = new ObjectMapper().writeValueAsString(context);
        RabbitMqConfig.channel.basicPublish(BrokerConstants.MQ_EXCHANGE_USER_FRIEND_SYNC, BrokerConstants.MQ_TOPIC_ROUTING_USER_FRIEND_SYNC, null, json.getBytes("UTF-8"));
        LOG.info("UserFriendSyncMqSender send json: {}", json);
    }

    public static void sendAddFriend(String selfUid, String targetUid, Long addTimeStamp, Integer blacked, Integer action) {
        Map<String, Object> msgMap = new HashMap<>();
        msgMap.put("selfUid", selfUid);
        msgMap.put("targetUid", targetUid);
        msgMap.put("addTime", addTimeStamp);
        msgMap.put("blacked", blacked);
        msgMap.put("action", action);
        try {
            LOG.info("[IM][RabbitMQ]UserFriendSyncMqSender Data:{}", msgMap);
            send(msgMap);
        } catch (Exception e) {
            LOG.error("[IM][RabbitMQ] UserFriendSyncMqSender error: {}", msgMap, e);
        }
    }
}
