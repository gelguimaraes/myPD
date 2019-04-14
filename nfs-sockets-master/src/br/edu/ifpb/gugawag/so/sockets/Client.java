package br.edu.ifpb.gugawag.so.sockets;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {
        Socket serversocket = new Socket("localhost", 7000);

        PrintWriter out = new PrintWriter(serversocket.getOutputStream(), true);
        out.println("readdir& &null&null"); //comando&diretorio&namefile1&namefile2
        //out.println("createdir&teste&null&null");
        //out.println("createfile&teste&teste.txt&null");
        //out.println("renamefile&teste&teste.txt&teste_renomeado.txt");
        // out.println("removefile&teste&teste3.txt&null");
        //out.println("removedir&teste&null&null");

        InputStream in = serversocket.getInputStream();
        Scanner scanner = new Scanner(in);
        while (scanner.hasNextLine()) {
            System.out.println(scanner.nextLine());
        }
        serversocket.close();
    }
}
