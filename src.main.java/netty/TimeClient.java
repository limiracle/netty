package netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import nio.TimeClientHandle;

/**
 * Created by lizhi on 2016/11/23.
 */
public class TimeClient {
    public void connect(int port,String host){
        EventLoopGroup group=new NioEventLoopGroup();
        Bootstrap b=new Bootstrap();
        b.group(group).channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY,true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new TimeClientHandler());
                    }
                });
        try {
            ChannelFuture f=b.connect(host,port).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args){
        int port=8080;
        if(args!=null&&args.length>0){
            port=Integer.valueOf(args[0]);
        }
        new TimeClient().connect(port,"127.0.0.1");
    }
}
