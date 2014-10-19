package my.statistic;

import java.time.LocalDateTime;

/**
 *
 * Class, wich collects information about one connection
 * Created by Sergey on 17.10.2014.
 *
 */
public class Connection {

    private String ip;  // ip of a client
    private String uri; // uri of a client
    private LocalDateTime connectionTime;   //date and time of connection
    private int sentBytes;  //number of sent bytes
    private int receivedBytes; // number of received bytes
    private double speed;   // speed of receiving data

    public Connection(String ip, String uri, LocalDateTime time, int sentBytes, int recievedBytes, double speed) {
        this.ip = ip;
        this.uri = uri;
        this.connectionTime = time;
        this.sentBytes = sentBytes;
        this.receivedBytes = recievedBytes;
        this.speed = speed;

        if (uri.indexOf("url") > -1){
            this.uri = uri.substring(uri.indexOf("%3C")+3, uri.indexOf("%3E"));
        }
    }

    public String getIp() {
        return ip;
    }

    public String getUri() {
        return uri;
    }

    public String getConnectionTime() {
        return connectionTime.toString();
    }

    public int getSentBytes() {
        return sentBytes;
    }

    public int getReceivedBytes() {
        return receivedBytes;
    }

    public String getSpeed() {
        return (int)(speed * 1000) / 1000.0 / 1000.0 + "kb/s";
    }

    public String toString(){
        return ip + " " + uri + " " + connectionTime + " " + sentBytes + " " + receivedBytes + " " + getSpeed();
    }
}
