package aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

/**
 * Created by lizhi on 2016/11/22.
 */
public class AsyncTimeServerhandler implements Runnable {

    private int port;
    CountDownLatch latch;

    AsynchronousServerSocketChannel asynchronousServerSocketChannel;

    public AsyncTimeServerhandler(int port){
        this.port=port;
        try {
            asynchronousServerSocketChannel=AsynchronousServerSocketChannel.open();
            asynchronousServerSocketChannel.bind(new InetSocketAddress(port));
            System.out.println("The time server is start in port:"+port);
        }catch(IOException e){
            e.printStackTrace();
            System.exit(1);
        }
    }


    @Override
    public void run() {
        latch=new CountDownLatch(1);
        doAccept();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void doAccept(){
        asynchronousServerSocketChannel.accept(this,new AcceptCompletionHandler());
    }
}
