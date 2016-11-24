package netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.io.UnsupportedEncodingException;

/**
 * Created by lizhi on 2016/11/23.
 */
public class TimeClientHandler extends ChannelHandlerAdapter {
    private final ByteBuf firstMessage;

    public TimeClientHandler(){
        byte[] req="QUERY TIME ORDER".getBytes();
        firstMessage= Unpooled.buffer(req.length);
        firstMessage.writeBytes(req);
    }

    public void channelActive(ChannelHandlerContext ctx){
        ctx.writeAndFlush(firstMessage);
    }

    public void channelRead(ChannelHandlerContext ctx,Object msg) throws UnsupportedEncodingException {
        ByteBuf buf=(ByteBuf)msg;
        byte[] req=new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body=new String(req,"UTF-8");
        System.out.println("Now is :"+body);
    }

    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause){
        ctx.close();
    }
}
