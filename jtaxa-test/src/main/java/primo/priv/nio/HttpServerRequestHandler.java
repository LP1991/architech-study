/********************** 版权声明 *************************
 * 文件名: HttpServerRequestHandler.java
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

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

public class HttpServerRequestHandler extends SimpleChannelInboundHandler {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ctx.writeAndFlush("lp is a good man");
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/html;charset=utf-8");
        response.content().writeBytes("lp is a good man".getBytes());
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);

    }
}
