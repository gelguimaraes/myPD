package br.edu.ifpb.gugawag.so.sockets;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(7000);
        System.out.println("Servidor Iniciado !");

        while (true) {
            Socket clientSocket = serverSocket.accept();

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            Commands c = new Commands();

            InputStream in = clientSocket.getInputStream();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(in));

            String path = buffer.readLine();
            String[] part = path.split("&");
            String comando = part[0];
            String dir = part[1];
            String namefile1 = part[2];
            String namefile2 = part[3];

            try {
                String result = c.setCmd(comando, dir, namefile1, namefile2);
                out.println(result);
                System.out.println(result);
                clientSocket.close();

            } catch (Exception e) {
                System.err.println(e);
            }
        }

    }
}
