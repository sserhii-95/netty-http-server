package my.server.requests.impl;

import io.netty.handler.codec.http.*;
import my.server.requests.MyRequest;
import my.statistic.ServerStatistic;

/**
 * Returns web page, that has address  that  equals to first parameter
 * Created by Sergey on 17.10.2014.
 */
public class MyRedirectRequest implements MyRequest {
    @Override
    public FullHttpResponse respone(String[] params) {
        String url = params[0];
        System.out.println("Going to "+url);
        ServerStatistic.getInstance().addURL(url);
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FOUND);
        response.headers().set(HttpHeaders.Names.LOCATION, url);
        return response;
    }
}
