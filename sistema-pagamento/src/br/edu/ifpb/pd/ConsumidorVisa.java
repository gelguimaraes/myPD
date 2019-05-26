package br.edu.ifpb.pd;


import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.util.Map;

public class ConsumidorVisa {
    private static int visaCont = 0;
    public static void main(String[] args) throws Exception {
        System.out.println("Inicio Consumidor Visa");
        String FILA_BANCO_VISA = "sendBancoVisa";
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        Connection connection = connectionFactory.newConnection();
        Channel canal = connection.createChannel();
        canal.queueDeclare(FILA_BANCO_VISA, true, false, false, (Map)null);
        DeliverCallback callback = (consumerTag, delivery) -> {
            String msgJson = new String(delivery.getBody());
            System.out.println("Recebendo mensagem da fila sendBancoVisa: " + msgJson);
            Gson g = new Gson();
            Banco b = (Banco)g.fromJson(msgJson, Banco.class);

            System.out.println(b.toString());

            String msgretorno = "Pagamento com cartao "+ b.getCartao() + " realizado com sucesso!";
            visaCont++;
            System.out.println("Pagamentos registrados: " + visaCont);
            try {
                new ProdutorVisa(msgretorno);
            }catch (Exception e){
                System.out.println(e);
            }

        };
        canal.basicConsume(FILA_BANCO_VISA, true, callback, (consumerTag) -> {
        });
        System.out.println("Fim Consumidor Visa");
    }
}
