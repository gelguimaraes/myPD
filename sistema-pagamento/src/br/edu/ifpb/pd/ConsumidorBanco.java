package br.edu.ifpb.pd;

import com.google.gson.Gson;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import java.util.Map;

public class ConsumidorBanco {

    public static void main(String[] args) throws Exception {
        System.out.println("Consumidor Banco");
        String FILA_CLIENTE_BANCO = "sendClienteBanco";
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        Connection connection = connectionFactory.newConnection();
        Channel canal = connection.createChannel();
        canal.queueDeclare(FILA_CLIENTE_BANCO, true, false, false, (Map)null);
        DeliverCallback callback = (consumerTag, delivery) -> {
            String msgJson = new String(delivery.getBody());
            System.out.println("Recebendo mensagem da fila : " + msgJson);
            Gson g = new Gson();
            Banco b = (Banco)g.fromJson(msgJson, Banco.class);

            System.out.println(g);

            int cartao = Integer.parseInt(b.getCartao());
            if (cartao % 2 == 0) {
                //valido
               try {
                   new ProdutorBanco(msgJson);
               }catch (Exception e){
                   System.out.println(e);
               }
            } else {
                //invalido
            }

        };
        canal.basicConsume(FILA_CLIENTE_BANCO, true, callback, (consumerTag) -> {
        });
        System.out.printf("Fim Consumidor Banco");
    }
}
