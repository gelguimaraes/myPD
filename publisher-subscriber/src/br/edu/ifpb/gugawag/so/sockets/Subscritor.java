package br.edu.ifpb.gugawag.so.sockets;

public class Subscritor {

    public String ip;
    public String porta;


    public Subscritor(String ip, String porta) {
        this.ip = ip;
        this.porta = porta;
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

    public Subscritor getSubscritor (String ip, String porta){
        if((this.ip.equals(ip)) && (this.porta.equals(porta))){
            return this;
        }
        return null;
    }
}
