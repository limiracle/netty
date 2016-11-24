package aio;

import nio.MultiplexerTimeServer;

/**
 * Created by lizhi on 2016/11/22.
 */
public class TimeServer {
    public static void main(String[] args){
        int port=8080;
        if(args!=null&&args.length>0){
            try{
                port=Integer.valueOf(args[0]);
            }catch(NumberFormatException e){
            }
        }
        AsyncTimeServerhandler timeServer=new AsyncTimeServerhandler(port);
        new Thread(timeServer,"AIO-MultiplexerTimeServer-001").start();

    }
}
