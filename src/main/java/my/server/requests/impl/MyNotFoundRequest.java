package my.server.requests.impl;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;
import my.server.requests.MyRequest;

/**
 * Returns page with "Not found"
 * Created by Sergey on 17.10.2014.
 */
public class MyNotFoundRequest implements MyRequest{

    @Override
    public FullHttpResponse respone(String[] args) {
        String notFound = "Not found";
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,
                Unpooled.copiedBuffer(notFound, CharsetUtil.UTF_8));
        return response;
    }
}
