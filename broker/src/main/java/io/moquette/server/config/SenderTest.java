package io.moquette.server.config;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class SenderTest {
    private final static String QUEUE_NAME = "dev.queue.user.message.send";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.1.32");
        factory.setUsername("guest");
        factory.setPassword("11111111");
        factory.setPort(5672);
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);

            for (int i = 0; i < 10; i++) {
                String message = "Hello World! " + i;
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
                System.out.println(" [x] Sent '" + message + "'");
            }
        }
    }
}
