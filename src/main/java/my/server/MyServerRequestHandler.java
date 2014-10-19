package my.server;

import io.netty.handler.codec.http.*;
import my.server.requests.MyRequest;
import my.server.requests.impl.MyHelloRequest;
import my.server.requests.impl.MyNotFoundRequest;
import my.server.requests.impl.MyRedirectRequest;
import my.server.requests.impl.MyStatisticRequest;

import java.util.HashMap;

/**
 * Processes requests from a client
 *
 * Created by Sergey on 17.10.2014.
 */
public class MyServerRequestHandler {

    private static String lp = "%3C"; // code of symbol '<'
    private static String rp = "%3E"; // code of symbol '>'

    protected boolean free = true; // is handler free

    // map of requests and handlers for they
    private HashMap<String, MyRequest> requests = new HashMap<String, MyRequest>(){
        {
            put("hello", new MyHelloRequest());
            put("redirect", new MyRedirectRequest());
            put("status", new MyStatisticRequest());
        }

        // never returns null;
        @Override
        public MyRequest get(Object key) {
            MyRequest r =  super.get(key);
            if (r == null) return new MyNotFoundRequest();
            return r;
        }
    };

    /**
     * Returns response for uri
     */
    public FullHttpResponse getResponce(String uri){
        String[] params = new String[0];

        int idx = uri.indexOf("?");
        if (idx >= 0){
            params = parseParams(uri);
            uri = uri.substring(0, idx);
        }

        MyRequest request = requests.get(uri);

        FullHttpResponse response =  request.respone(params);
        free = true; // becomes free after calculating response
        return response;
    }

    /**
     * Returns all params like "url = <some_url>" in string array
     * @param uri
     */
    private String[] parseParams(String uri) {
        String[] params = new String[10];
        String buffer = uri+"";
        int l = buffer.indexOf(lp);
        int r = buffer.indexOf(rp);
        int idx = 0;
        while (l > -1 && r > -1){
           params[idx++] = buffer.substring(l+3, r);
           buffer = buffer.substring(r+3);

           l = buffer.indexOf(lp);
           r = buffer.indexOf(rp);
        }

        return params;
    }

    public boolean isFree(){
        return free;
    }

    public void setFree(boolean b) {
        free = b;
    }
}
