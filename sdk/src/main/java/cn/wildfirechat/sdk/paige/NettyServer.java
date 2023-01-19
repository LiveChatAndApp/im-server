package cn.wildfirechat.sdk.paige;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {

    private int mPort;

    public NettyServer(int port) {
        this.mPort = port;
    }

    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                // 指定连接队列大小
                .option(ChannelOption.SO_BACKLOG, 128)
                //KeepAlive
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                //Handler
                .childHandler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                        channel.pipeline().addLast(new NettyServerHandler());
                    }
                });
            ChannelFuture f = b.bind(mPort).sync();
            if (f.isSuccess()) {
                System.out.println("Server,启动Netty服务端成功，端口号:" + mPort);
            }
            // f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // workerGroup.shutdownGracefully();
            // bossGroup.shutdownGracefully();
        }
    }

}
