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
    private  Map<String, String> noticias;

    public ServerThread(Socket clientSocket, Map<String, ArrayList<Subscritor>> topicos, Map<String, String> noticias) {
        this.clientSocket = clientSocket;
        this.topicos = topicos;
        this.noticias = noticias;
    }

    public void run() {
        DataOutputStream dos = null;
        DataInputStream dis = null;
        String topico = "";
        String ip = "";
        int porta = 0;
        Subscritor subscritor;
        ArrayList<Subscritor> subscritors;
        Socket socket = null;
        DataOutputStream dosmsg = null;
        DataInputStream dismsg =  null;

        try {
            dos = new DataOutputStream(clientSocket.getOutputStream());
            dis = new DataInputStream(clientSocket.getInputStream());
            ip = clientSocket.getInetAddress().getHostAddress();
            porta = clientSocket.getPort();
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

            if (!topico.equals("porta")) {
                if (this.topicos.get(topico) == null) {
                    subscritors = new ArrayList<>();
                    subscritor = new Subscritor(ip, porta);
                    subscritors.add(subscritor);
                    this.topicos.put(topico, subscritors);
                    try {
                        dos.writeUTF("Novo inscrito e novo tópico adicionado: " + topico);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
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
                    if (!isSbuscritor) {
                        subscritors.add(new Subscritor(ip, porta));
                        try {
                            dos.writeUTF("Adicionado ao topico: " + topico);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            } else {
                try {

                    dos.writeUTF(Integer.toString(7100));

                    if (socket == null) socket = new Socket(ip, 7100);

                    if(dosmsg == null) dosmsg = new DataOutputStream(socket.getOutputStream());
                    if(dismsg == null) dismsg = new DataInputStream(socket.getInputStream());

                    SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                    String dateNowString = format.format(new Date());
                    try {
                        long timeMin = format.parse("14:00").getTime();
                        long timeMax = format.parse("15:50").getTime();
                        long timeNow = format.parse(dateNowString).getTime();
                        if (timeNow > timeMin && timeNow < timeMax) {

                            for (Map.Entry<String, String> entry : this.noticias.entrySet()) {
                                String t = entry.getKey();
                                String n = entry.getValue();
                                if(t.equals(topico)){
                                    dosmsg.writeUTF(n);
                                }
                            }
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }



            for (Map.Entry<String, ArrayList<Subscritor>> entry : this.topicos.entrySet()) {
                String texto = "";
                String t = entry.getKey();
                texto += "Tópico: " + t + "\nSubscriptors:\n";
                subscritors = entry.getValue();
                for (Subscritor s : subscritors) {
                    texto += "Ip " + s.getIp() + " Porta " + s.getPorta() + "\n";
                }
                System.out.println(texto);
            }
        }
    }
}
