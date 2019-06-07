package br.edu.ifpb.pd;

public class Middleware {

    public String send_timed(String msg, int tsProcess) throws Exception{
        return  tsProcess + ":" + msg;

    }

    public String receive_timed(int tsPorcess, String msgTimed) throws Exception{

        String msgSplit[] = msgTimed.split(":");
        int ts = Integer.parseInt(msgSplit[0]);
        String mensagem = msgSplit[1];
        int newts;
        if(tsPorcess < ts){
            newts = ts +1;
        }else {
            newts = tsPorcess +1;
        }
        return newts + ":"+ mensagem;
    }
}
