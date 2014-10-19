package my.statistic;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Class that includes statistics about my site
 * Created by Sergey on 17.10.2014.
 */
public class ServerStatistic {

    // Instance of this class
    private static final ServerStatistic statistic = new  ServerStatistic();

    // Number of last connections, that will be show in statistics
    private static final int CONNECTIONS_COUNT_LIMIT = 16;

    // Last connections
    private ArrayList<Connection> connections;

    // Map of uniqeue urls(key - url, value - count)
    private HashMap<String, Integer> uniqURLs;

    // Set of unique ip-s
    private HashSet<String> uniqIPs;

    // Count of active connections
    private int activeConnectionsNubmer;

    // Count of summary requests
    private long totalRequestNumber;

    // Time of last connection
    private LocalDateTime lastConnection;


    private ServerStatistic(){
        activeConnectionsNubmer = 0;
        totalRequestNumber = 0;
        connections = new ArrayList<Connection>();
        uniqIPs = new HashSet<>();
        uniqURLs = new HashMap<>();
    }

    /**
     * Returns the instance of this class
     */
    public synchronized  static ServerStatistic getInstance(){
        return statistic;
    }

    public synchronized String getStaticPage(){
        return "";
    }

    /**
     * Adds connection to list of last connections
     */
    public synchronized void addConnection(Connection connection){
        activeConnectionsNubmer--;
        connections.add(0, connection);
        if (connections.size() > CONNECTIONS_COUNT_LIMIT)
            connections.remove(connections.size() - 1);
        lastConnection = LocalDateTime.parse(connection.getConnectionTime());
        uniqIPs.add(connection.getIp());
    }

    /**
     * Returns a count of active connections
     */
    public synchronized int getActiveConnectionsNubmer(){
        return activeConnectionsNubmer;
    }

    /**
     * Increments a count of active connections
     */
    public synchronized void incActiveConnectionsNumber(){
        activeConnectionsNubmer++;
    }

    /**
     * Decriments a count of active connections
     */
    public synchronized void decActiveConnectionsCount(){
        activeConnectionsNubmer--;
    }

    /**
     * Adds url to base of unique urls.
     * if statistics know about this url, it increments value for this key,
     * otherwise statistics adds to base this url, and sets its value on 1
     */
    public synchronized void addURL(String url){
        Integer t = uniqURLs.get(url);
        if (t == null)
            uniqURLs.put(url, 1);
        else
            uniqURLs.put(url, t+1);
    }

    /**
     * Returns summary count of requests
     */
    public synchronized long geTotalRequestNumber(){
        return totalRequestNumber;
    }

    /**
     * Increments summary count of requests
     */
    public synchronized void incTotalRequestNumber(){
        totalRequestNumber++;
    }

    public  synchronized String getFirstTable(){
        String buf = "";
        if (connections.size() > 0) {
            buf = "Total request count  - " + totalRequestNumber + "\n"
                + "Unique IPs count     - " + uniqIPs.size() + "\n"
                + "Active connections   - " + activeConnectionsNubmer + "\n"
                + "Last connection IP   - " + connections.get(connections.size() - 1).getIp() + "\n"
                + "Last connection time - "  + lastConnection;
        }
        return buf;
    }

    public synchronized  String getSecondTable(){
        String buf = "Unique URLs\n";

        for(String key : uniqURLs.keySet())
            buf += key + " "+uniqURLs.get(key) + "\n";
        return buf;
    }

    public synchronized String getThirdTable(){
        String buf = "Last " + CONNECTIONS_COUNT_LIMIT + " connections\n";

        for(Connection connection : connections)
            buf += connection.toString()  + "\n";
        return buf;

    }

}
