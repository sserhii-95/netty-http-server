package my.server.requests.impl;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;
import my.server.requests.MyRequest;
import my.statistic.ServerStatistic;

/**
 * Returns statistics of site
 * Created by Sergey on 17.10.2014.
 */
public class MyStatisticRequest implements MyRequest {

    @Override
    public FullHttpResponse respone(String[] args) {
        String notFound = "Statistic\n" +
                ServerStatistic.getInstance().getFirstTable() + "\n" +
                ServerStatistic.getInstance().getSecondTable() + "\n" +
                ServerStatistic.getInstance().getThirdTable();

        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,
                Unpooled.copiedBuffer(notFound, CharsetUtil.UTF_8));
        return response;
    }
}
