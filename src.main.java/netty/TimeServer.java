package netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by lizhi on 2016/11/23.
 */
public class TimeServer {

    public void bind(int port){

        EventLoopGroup bossGroup=new NioEventLoopGroup();
        EventLoopGroup workerGroup =new NioEventLoopGroup();

        ServerBootstrap b=new ServerBootstrap();
        b.group(bossGroup,workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,1024)
                .childHandler(new ChildChannelHandler());

        try{
            ChannelFuture f=b.bind(port).sync();

            f.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel>{

        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            socketChannel.pipeline().addLast(new TimeServerHandler());
        }
    }

    public static void main(String[] args){
        int port=8080;
        if(args!=null&&args.length>0){
            port=Integer.valueOf(args[0]);
        }
        new TimeServer().bind(port);
    }
}
