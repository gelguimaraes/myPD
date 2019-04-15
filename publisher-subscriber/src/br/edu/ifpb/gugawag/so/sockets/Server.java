package br.edu.ifpb.gugawag.so.sockets;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Server {

    private static HashMap< String, ArrayList<String>> topicos = new HashMap<String, ArrayList<String>>() ;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(7000);
        System.out.println("Servidor Iniciado !");

        while (true) {
            Socket clientSocket = serverSocket.accept();

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            InputStream in = clientSocket.getInputStream();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(in));

            String topico = buffer.readLine();
            if(topicos.get(topico) == null)
                topicos.put(topico, new ArrayList<String>());
            topicos.get(topico).add(clientSocket.getInetAddress().toString());
            System.out.println(topicos);

            /*try {
                String result = c.setCmd(comando, dir, namefile1, namefile2);
                out.println(result);
                System.out.println(result);
                clientSocket.close();

            } catch (Exception e) {
                System.err.println(e);
            }*/
        }

    }
}
