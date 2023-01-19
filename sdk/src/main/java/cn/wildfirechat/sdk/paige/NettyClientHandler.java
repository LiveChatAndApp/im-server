package cn.wildfirechat.sdk.paige;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client,channelActive");
//        ByteBuf byteBuf = Unpooled.copiedBuffer("你好，客户端", Charset.forName("utf-8"));

        MqttMessageType type = MqttMessageType.CONNECT;
        MqttQoS os = MqttQoS.AT_MOST_ONCE;

        MqttFixedHeader fixed = new MqttFixedHeader(type, false, os, false, 138);
        MqttConnectVariableHeader header = new MqttConnectVariableHeader(
            "MQTT", 6, true, true, false, 0, false, true, 300
        );

        MqttConnectPayload payload = new MqttConnectPayload("f100c4a8-dd7a-4b90-8eb7-8bd4fa03b4081669365489789", null, null , "7ogqmws2k", "password".getBytes());

        MqttMessage msg = new MqttMessage(fixed, header, payload);
        ctx.writeAndFlush(msg);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Client,接收到服务端发来的消息:" + msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("Client,exceptionCaught");
        cause.printStackTrace();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client,channelInactive");
    }
}
