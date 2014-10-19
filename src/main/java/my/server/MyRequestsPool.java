package my.server;

import java.util.ArrayList;

/**
 * Describes a pool of handlers, that will process a requests
 *
 * Created by Sergey on 18.10.2014.
 */
public class MyRequestsPool {

    // list of handler that has 4 handlers on start
    private static ArrayList<MyServerRequestHandler> handlers = new ArrayList<MyServerRequestHandler>(){{
        for(int i = 0; i < 4; i++)
            add(new MyServerRequestHandler());
    }};

    /**
     * Returns a free handler
     */
    public static synchronized  MyServerRequestHandler getFreeHandler(){
     //   System.out.println(handlers.size());
        for(MyServerRequestHandler handler : handlers){
            if (handler.isFree()){
                handler.setFree(false);
                return handler;
            }
        }

        MyServerRequestHandler handler = new MyServerRequestHandler();
        handlers.add(handler);
        return handler;
    }


}
