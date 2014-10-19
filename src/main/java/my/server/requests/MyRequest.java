package my.server.requests;

import io.netty.handler.codec.http.FullHttpResponse;

/**
 * Describes type of request
 */
public interface MyRequest{

    /**
     * Returns response on request with params
     */
    public FullHttpResponse respone(String[] params);
}
