package br.edu.ifpb.pd;

import com.google.gson.Gson;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import java.util.Map;

public class ConsumidorBanco {
    public ConsumidorBanco() {
    }

    public static void main(String[] args) throws Exception {
        System.out.println("ConsumidorBanco");
        String NOME_FILA = "sendClienteBanco";
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        Connection connection = connectionFactory.newConnection();
        Channel canal = connection.createChannel();
        canal.queueDeclare(NOME_FILA, false, false, false, (Map)null);
        DeliverCallback callback = (consumerTag, delivery) -> {
            String msgJson = new String(delivery.getBody());
            System.out.println("Mensagem: " + msgJson);
            Gson g = new Gson();
            Banco b = (Banco)g.fromJson(msgJson, Banco.class);
            System.out.println(g);
        };
        canal.basicConsume(NOME_FILA, true, callback, (consumerTag) -> {
        });
        System.out.printf("FimConsumidorBanco");
    }
}
