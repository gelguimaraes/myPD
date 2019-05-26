package br.edu.ifpb.pd;


import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.util.Map;

public class ConsumidorCliente {

    public static void main(String[] args) throws Exception {
        System.out.println("Inicio Consumidor Cliente");
        String FILA_VISA_CLIENTE = "sendVisaCliente";
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        Connection connection = connectionFactory.newConnection();
        Channel canal = connection.createChannel();
        canal.queueDeclare(FILA_VISA_CLIENTE, true, false, false, (Map)null);
        DeliverCallback callback = (consumerTag, delivery) -> {
            String msg = new String(delivery.getBody());
            System.out.println(msg);
        };
        canal.basicConsume(FILA_VISA_CLIENTE, true, callback, (consumerTag) -> {
        });
        System.out.println("Fim Consumidor Cliente");
    }
}
