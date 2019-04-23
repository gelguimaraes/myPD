package br.edu.ifpb.gugawag.so.sockets;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServerThread extends Thread {

        private Socket clientSocket;
        private Map<String, ArrayList<Subscritor>> topicos = new HashMap<String, ArrayList<Subscritor>>() ;

        public ServerThread(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        public void run() {
            DataOutputStream dos = null;
            DataInputStream dis;
            String topico = "";
            String ip = "";
            String porta = "";
            Subscritor subscritor;
            ArrayList<Subscritor> subscritors;

            try {
                dos = new DataOutputStream(clientSocket.getOutputStream());
                dis = new DataInputStream(clientSocket.getInputStream());
                ip = clientSocket.getInetAddress().toString();
                porta = Integer.toString(clientSocket.getPort());

                topico =  dis.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }
           // System.out.println(topico);

            if(topicos.get(topico) == null){
                topicos.put(topico, new ArrayList<Subscritor>());
                try {
                    dos.writeUTF("Novo tópico : " + topico);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            subscritors = topicos.get(topico);
            if(subscritors.size() == 0){
                subscritor = new Subscritor(ip, porta);
                subscritors.add(subscritor);
                try {
                    dos.writeUTF("Adicionado ao tópico: " + topico);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                for (Subscritor s: subscritors) {
                    if(s.getSubscritor(ip, porta) != null){
                        try {
                            dos.writeUTF("já adicionado: " + topico);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
            }

            for(Map.Entry<String, ArrayList<Subscritor>> entry : topicos.entrySet()) {
                String t = entry.getKey();
                System.out.println("Tópico: " + t);
                System.out.println("Subscriptors:");
                subscritors =  entry.getValue();
                for (Subscritor s: subscritors){
                    System.out.println("IP " +s.getIp() + " Porta " +s.porta);
                }
            }
        }
}
