/********************** 版权声明 *************************
 * 文件名: HttpServer.java
 * 包名: priv.primo.primo.priv.nio
 * 版权:	杭州华量软件  jtaxatest
 * 职责: 
 ********************************************************
 *
 * 创建者：Primo  创建时间：2019/5/22
 * 文件版本：V1.0
 *
 *******************************************************/
package primo.priv.nio;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

public class HttpServer {
    public static void main(String[] args) throws Exception {
        HttpServer server = new HttpServer();
        server.start(8080);
    }

    public void start(int port)throws Exception{
        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup work = new NioEventLoopGroup(8);
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(boss,work);

        serverBootstrap.channel(NioServerSocketChannel.class).childHandler(new HttpServerHandler());

        ChannelFuture f = serverBootstrap.bind(port).sync();
        System.out.println("server 等待连接：");
        //等待服务器socket关闭
        f.channel().closeFuture().sync();
    }

    private class HttpServerHandler extends ChannelInitializer {
        @Override
        protected void initChannel(Channel ch) throws Exception {
            ch.pipeline().addLast("HttpRequestDecoder",new HttpRequestDecoder());
            ch.pipeline().addLast("HttpResponseEncoder",new HttpResponseEncoder());
            ch.pipeline().addLast("HttpServerRequestHandler",new HttpServerRequestHandler());
        }
    }
}
