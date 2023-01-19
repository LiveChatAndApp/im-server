package io.moquette.server.config;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.moquette.BrokerConstants;
import org.springframework.context.annotation.Configuration;
import java.io.IOException;

@Configuration
public class RabbitMqConfig {
    public static Channel channel;

    public static void connectionFactory(String host, String port, String userName, String password) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setPort(Integer.valueOf(port));
        factory.setUsername(userName);
        factory.setPassword(password);

        Connection connection = factory.newConnection();
        channel = connection.createChannel();

        // 讯息
        initUserMessageSend(BrokerConstants.MQ_EXCHANGE_USER_MESSAGE_SEND, BrokerConstants.MQ_TOPIC_ROUTING_USER_MESSAGE_SEND, BrokerConstants.MQ_QUEUE_USER_MESSAGE_SEND);

        // 好友同步
        initUserMessageSend(BrokerConstants.MQ_EXCHANGE_USER_FRIEND_SYNC, BrokerConstants.MQ_TOPIC_ROUTING_USER_FRIEND_SYNC, BrokerConstants.MQ_QUEUE_USER_FRIEND_SYNC);

        // 群组同步
        initUserMessageSend(BrokerConstants.MQ_EXCHANGE_GROUP_SYNC, BrokerConstants.MQ_TOPIC_ROUTING_GROUP_SYNC, BrokerConstants.MQ_QUEUE_GROUP_SYNC);

        // 群组解散
        initUserMessageSend(BrokerConstants.MQ_EXCHANGE_GROUP_DISSOLVE, BrokerConstants.MQ_TOPIC_ROUTING_GROUP_DISSOLVE, BrokerConstants.MQ_QUEUE_GROUP_DISSOLVE);

        // 敏感词
        initUserMessageSend(BrokerConstants.MQ_EXCHANGE_USER_MESSAGE_SENSITIVE, BrokerConstants.MQ_TOPIC_ROUTING_USER_MESSAGE_SENSITIVE, BrokerConstants.MQ_QUEUE_USER_MESSAGE_SENSITIVE);
    }

    private static void initUserMessageSend(String exchange, String topic, String queue) throws IOException {
        // 创建交换器
        channel.exchangeDeclare(exchange, "topic", true, false, null);

        // 绑定交换器
        channel.exchangeBind(exchange, exchange, topic);

        // 创建列队
        channel.queueDeclare(queue, true, false, false, null);
    }
}
