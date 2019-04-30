package br.edu.ifpb.gugawag.so.sockets;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Server {

    public static void main(String[] args) throws IOException {
        System.out.println("Servidor Iniciado !");

        ServerSocket serverSocket = new ServerSocket(7000);

        Map<String, ArrayList<Subscritor>> topicos = new HashMap<String, ArrayList<Subscritor>>();
        Map<String, String> noticias = new HashMap<String, String>();
        noticias.put("Esporte","Notícia de Esporte");
        noticias.put("Policial","Noticia Policial");
        noticias.put("Tecnologia","Noticia sobre Tecnologias");

        while (true) {
            Socket clientSocket = null;
            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
            }
            // new thread for a client
            ServerThread st = new ServerThread(clientSocket, topicos, noticias);
            st.start();
        }
    }
}
