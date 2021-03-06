package br.edu.ifpb.gugawag.so.sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {
        System.out.println("Cliente Iniciado!");

        Socket socket = new Socket("192.168.15.11", 7000);


        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        DataInputStream dis = new DataInputStream(socket.getInputStream());

        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        DataOutputStream dosmsg = null;
        DataInputStream dismsg = null;

        while (true) {
            Scanner teclado = new Scanner(System.in);
            System.out.print("Digite um tópico: ");
            dos.writeUTF(teclado.nextLine());

            // lendo o que o servidor enviou
            String mensagem = dis.readUTF();
            System.out.println(mensagem);
            dos.writeUTF("sendMeNew");
            Integer porta = Integer.parseInt(dis.readUTF());
            if(serverSocket == null)
                serverSocket = new ServerSocket(porta);

            if (clientSocket == null) clientSocket = serverSocket.accept();

            if (dismsg == null){
                dismsg   = new DataInputStream(clientSocket.getInputStream());
            }
            // DataOutputStream(clientSocket.getOutputStream());

            System.out.println(dismsg.readUTF());

        }
        /*
         * Observe o while acima. Perceba que primeiro se escreve para o servidor (linha 27), depois se lê do canal de
         * entrada (linha 30), vindo do servidor. Agora observe o código while do Servidor2. Lá, primeiro se lê,
         * depois se escreve. De outra forma, haveria um deadlock.
         */
    }
}