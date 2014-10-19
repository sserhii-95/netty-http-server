package my.server.requests.impl;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;
import my.server.requests.MyRequest;

/**
 * Waits 10 seconds and returns html page with "Hello world!!"
 *
 * Created by Sergey on 17.10.2014.
 */
public class MyHelloRequest implements MyRequest{

    // Time of waiting - 10 seconds
    private int mills = 10 * 1000;

    @Override
    public FullHttpResponse respone(String[] args) {

        String hello = "Hello World!!";
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,
                Unpooled.copiedBuffer(hello, CharsetUtil.UTF_8));

        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return  response;
    }
}
