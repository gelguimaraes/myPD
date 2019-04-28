package br.edu.ifpb.gugawag.so.sockets;

import java.net.Socket;
import java.util.ArrayList;

public class Subscritor {

    private String ip;
    private String porta;

    private Socket socket;

    public Subscritor(String ip, String porta, Socket socket) {
        this.ip = ip;
        this.porta = porta;
        this.socket = socket;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPorta() {
        return porta;
    }

    public void setPorta(String porta) {
        this.porta = porta;
    }

    public Boolean isSubscritor(String ip, String porta) {
        if ((this.ip.equals(ip)) && (this.porta.equals(porta))) {
            return true;
        }
        return false;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
