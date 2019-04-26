package br.edu.ifpb.gugawag.so.sockets;

import java.util.ArrayList;

public class Subscritor {

    private String ip;
    private int porta;

    public Subscritor(String ip, int porta) {
        this.ip = ip;
        this.porta = porta;
    }


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPorta() {
        return porta;
    }

    public void setPorta(int porta) {
        this.porta = porta;
    }

    public Boolean isSubscritor(String ip, int porta) {
        if ((this.ip.equals(ip)) && (this.porta == porta)) {
            return true;
        }
        return false;
    }

}
