package br.edu.ifpb.gugawag.so.sockets;

import java.io.*;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ServerThread extends Thread {

    private Socket clientSocket;
    private Map<String, ArrayList<Subscritor>> topicos;

    public ServerThread(Socket clientSocket, Map<String, ArrayList<Subscritor>> topicos) {
        this.clientSocket = clientSocket;
        this.topicos = topicos;
    }

    public void run() {
        DataOutputStream dos = null;
        DataInputStream dis = null;
        String topico = "";
        String ip = "";
        String porta = "";
        Subscritor subscritor;
        ArrayList<Subscritor> subscritors;

        try {
            dos = new DataOutputStream(clientSocket.getOutputStream());
            dis = new DataInputStream(clientSocket.getInputStream());
            ip = clientSocket.getInetAddress().getHostAddress();
            porta = Integer.toString(clientSocket.getPort());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // System.out.println(topico);

        while (true) {
            try {
                topico = dis.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(this.topicos.get(topico) == null){
                subscritors = new ArrayList<>();
                subscritor = new Subscritor(ip, porta);
                subscritors.add(subscritor);
                this.topicos.put(topico, subscritors);
                try {
                    dos.writeUTF("Novo inscrito e novo tópico adicionado: " + topico);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                boolean isSbuscritor = false;
                subscritors = this.topicos.get(topico);
                    for (Subscritor s : subscritors) {
                        if (s.isSubscritor(ip, porta)) {
                            try {
                                dos.writeUTF("Já inscrito no tópico: " + topico);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            isSbuscritor = true;
                            break;
                        }
                    }
                    if (!isSbuscritor){
                        subscritors.add(new Subscritor(ip, porta));
                        try {
                            dos.writeUTF("Adicionado ao topico: " + topico);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }




            }

            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            String dateNowString = format.format(new Date());
            try {
                Date timeMin = format.parse("21:00");
                Date timeMax = format.parse("21:50");
                Date timeNow = format.parse(dateNowString);
                if (timeNow.getTime() > timeMin.getTime() && timeNow.getTime() < timeMax.getTime() ){
                    System.out.println("Disparo");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            for (Map.Entry<String, ArrayList<Subscritor>> entry : this.topicos.entrySet()) {
                String texto = "";
                String t = entry.getKey();
                texto += "Tópico: " + t +"\nSubscriptors:\n";
                subscritors = entry.getValue();
                for (Subscritor s : subscritors) {
                    texto += "Ip " + s.getIp() + " Porta " + s.getPorta() + "\n";
                }
                System.out.println(texto);
            }
        }
    }
}
