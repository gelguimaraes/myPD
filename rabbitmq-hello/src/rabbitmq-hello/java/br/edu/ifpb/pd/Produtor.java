package br.edu.ifpb.pd;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Produtor {
    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        String NOME_FILA = "filaOla";

        try ( Connection connection = connectionFactory.newConnection();
                Channel channel = connection.createChannel()
                )
        {

            ((com.rabbitmq.client.Channel) channel).queueDeclare(NOME_FILA, false, false, false, null);
            String mensagem = "Olá mundo!";
            ((com.rabbitmq.client.Channel) channel).basicPublish("", NOME_FILA, null, mensagem.getBytes());
            System.out.println("Enviei mensagem: " + mensagem);
        }


    }
}
