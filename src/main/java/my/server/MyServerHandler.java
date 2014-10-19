package my.server;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import my.statistic.Connection;
import my.statistic.ServerStatistic;

import java.time.LocalDateTime;

/**
 * Handler for my server. Collects information about connections, and added it to statistics
 * Created by Sergey on 17.10.2014.
 *
 * @see my.statistic.Connection
 * @see my.statistic.ServerStatistic
 *
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter {

    //ip of connection
    private String ip;
    //uri of connection
    private String uri;
    //number of bytes that server sends
    private int sentBytes;
    //number of bytes that server gets
    private int recievedBytes;
    //time of connection
    private LocalDateTime time0;
    //time of receiving data
    private double time;

    public MyServerHandler(String ip){
        this.ip = ip;
        this.uri = "";
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        time = System.currentTimeMillis(); // time for calculating duration of receive data
        time0 = LocalDateTime.now();
        ServerStatistic.getInstance().incTotalRequestNumber(); // inc summary connections count
        ServerStatistic.getInstance().incActiveConnectionsNumber();  //  inc active connections count
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            recievedBytes = msg.toString().length();
            FullHttpResponse response = null;

            uri = ((HttpRequest) msg).getUri().substring(1);
            if (uri.startsWith("favicon.ico")) return;  //  ignore icon of my site

            //response = new MyServerRequestHandler().getResponce(uri); // slow variant
            response = MyRequestsPool.getFreeHandler().getResponce(uri); // get free handler for uri

            //   System.out.println("ip = " + ip + "\n---------");
            //   System.out.println("uri = " + uri + "\n---------");
            //   System.out.println(msg.toString() + "\n------------");

            if (response != null) {
                sentBytes = response.content().writerIndex();
                ctx.write(response).addListener(ChannelFutureListener.CLOSE);
            }
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        time  = (System.currentTimeMillis() - time) / 1000.0; // time of reading data from channel
        double speed = recievedBytes / time; // speed of receiving

        if (uri.startsWith("favicon.ico")) {    // ignore icon
            ServerStatistic.getInstance().decActiveConnectionsCount();
            return;
        }

        Connection connection = new Connection(ip, uri, time0, sentBytes, recievedBytes, speed);
        ServerStatistic.getInstance().addConnection(connection); // add connection to statistics

        ctx.flush();
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace(); // write exception to console
        ctx.close();
    }
}
