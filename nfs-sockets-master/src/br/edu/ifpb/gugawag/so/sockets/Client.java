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
        String inputArgs[] = input.split(" ");
        String serverInput;

        if(inputArgs[0].equals("readdir")) {

            if(inputArgs.length > 2) {
                System.out.println("Muitos argumentos para comando readdir");
                return true;
            }
        }else if(inputArgs[0].equals("rename")) {
            if(inputArgs.length > 4) {
                System.out.println("Muitos argumentos para comando rename");
                return true;
            }
        } else if(inputArgs[0].equals("createdir")) {
            if(inputArgs.length > 3) {
                System.out.println("Muitos argumentos para comando create");
                return true;
                }
            } else if (inputArgs[0].equals("createfile")) {
                if(inputArgs.length > 3) {
                    System.out.println("Muitos argumentos para comando create");
                    return true;
                    }
                } else if(inputArgs[0].equals("remove")) {
            System.out.println("Comando remover");
            if(inputArgs.length > 3) {
                System.out.println("Muitos argumentos para comando remove");
                return true;
            }
        } else if(inputArgs[0].equals("exit")) {
            Client.end();
            return false;
        }else {
            System.out.println("Comando não encontrado");
            return true;
        }
        serverInput = input.replaceAll(" ", "&");

        if(inputArgs.length < 4){
            for(int i = 0; i < 4 - inputArgs.length ; i++){
                serverInput = serverInput + "& ";
            }
        }
        pout.println(serverInput);
        return true;
    }

    public static void end() throws Exception {
        Client.socket.close();

    }
}
