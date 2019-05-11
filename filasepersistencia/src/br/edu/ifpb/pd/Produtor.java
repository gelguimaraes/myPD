package br.edu.ifpb.pd;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class Produtor {
    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        String NOME_FILA = "filaOla";

        try (Connection connection = connectionFactory.newConnection(); Channel channel = connection.createChannel()){
            boolean duravel = true;
            ((Channel) channel).queueDeclare(NOME_FILA, duravel, false, false, null);
            String mensagem = "Olá mundo!";
            ((Channel) channel).basicPublish("", NOME_FILA, MessageProperties.PERSISTENT_TEXT_PLAIN, mensagem.getBytes());
            System.out.println("Enviei mensagem: " + mensagem);
        }
    }
}
