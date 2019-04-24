package br.edu.ifpb.gugawag.so.sockets;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
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
        ArrayList<Subscritor> allsubscritors;

        try {
            dos = new DataOutputStream(clientSocket.getOutputStream());
            dis = new DataInputStream(clientSocket.getInputStream());
            ip = clientSocket.getInetAddress().toString();
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
            if (this.topicos.get(topico) == null) {
                this.topicos.put(topico, new ArrayList<Subscritor>());
            }
            subscritors = this.topicos.get(topico);
            if (subscritors.size() == 0) {
                subscritor = new Subscritor(ip, porta);
                subscritors.add(subscritor);
                try {
                    dos.writeUTF("Novo inscrito e adicionado ao tópico: " + topico);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                for (Map.Entry<String, ArrayList<Subscritor>> entry : this.topicos.entrySet()) {
                    allsubscritors = entry.getValue();
                    for (Subscritor s : allsubscritors) {
                        if (s.getSubscritor(ip, porta) != null) {
                            subscritors.add(s);
                            try {
                                dos.writeUTF("Adicionado: " + topico);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                    }
                }
            }

            for (Map.Entry<String, ArrayList<Subscritor>> entry : this.topicos.entrySet()) {
                String t = entry.getKey();
                System.out.println("Tópico: " + t);
                System.out.println("Subscriptors:");
                subscritors = entry.getValue();
                for (Subscritor s : subscritors) {
                    System.out.println("IP " + s.getIp() + " Porta " + s.porta);
                }
            }
        }
    }
}
