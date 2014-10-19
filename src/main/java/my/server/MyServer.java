package my.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 *
 * Describes the server
 *
 * Created by Sergey on 17.10.2014.
 */
public class MyServer {
    /**
     * Default port
     */
    private static final int DEFAULT_PORT = 8080;

    /**
     * Port, wich used by server
     */
    private final int port;

    /**
     * Constructor
     * @param port
     */
    public MyServer(int port){
        this.port = port;
    }


    public void serve(){
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try{
            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ServerInitializer());

            bootstrap.bind(port).sync().channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally{
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }


    public static void main(String[] args) {
        int port = DEFAULT_PORT;
        if (args.length > 0){
           port = Integer.parseInt(args[0]);
        }
        new MyServer(port).serve();
    }
}
