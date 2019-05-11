package br.edu.ifpb.pd;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class Consumidor {
    public static void main(String[] args) throws Exception{
        System.out.println("Consumidor");

        String NOME_FILA = "filaOla";

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        Connection connection = connectionFactory.newConnection();

        //criando um canal e declarando uma fila
        Channel canal = connection.createChannel();
        boolean duravel = true;
        canal.queueDeclare(NOME_FILA, duravel, false, false, null);

        //Definindo a funcao callback
        DeliverCallback callback = (consumerTag, delivery) -> {
            String mensagem = new String(delivery.getBody());
            System.out.println("Recebi a mensagem: " + mensagem);
        };

        //Consome da fila
        canal.basicConsume(NOME_FILA, true, callback, consumerTag -> {});
        System.out.println("Continuarei executando outras atividades enquanto não chega mensagem...");
    }
}
