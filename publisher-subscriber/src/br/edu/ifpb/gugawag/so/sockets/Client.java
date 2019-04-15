package br.edu.ifpb.gugawag.so.sockets;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class Client {

    public static Socket socket;
    public static InputStream inputStream;
    public static BufferedReader bin;
    public static Scanner userIn = new Scanner(System.in);
    public static PrintWriter pout;

    public static void main(String[] args)  {

        Boolean running = true;
        String outStr;
        while(running) {
            try {
                Client.start();
                running = Client.readCommand();
                while( (outStr = bin.readLine() ) != null )
                    System.out.println(outStr);
                } catch (Exception ioe) {
                System.err.println(ioe);
            }
        }
    }

    public static void start() throws Exception {
        socket = new Socket("localhost",7000);
        inputStream = Client.socket.getInputStream();
        bin = new BufferedReader(new InputStreamReader(Client.inputStream));
        pout = new PrintWriter(socket.getOutputStream(), true);

    }

    public static boolean readCommand() throws Exception {

        String input = userIn.nextLine();
        pout.println(input);
        return true;
    }

    public static void end() throws Exception {
        Client.socket.close();

    }
}
