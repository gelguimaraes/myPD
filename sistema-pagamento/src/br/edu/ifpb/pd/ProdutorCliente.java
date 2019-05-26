package br.edu.ifpb.pd;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


public class ProdutorCliente {
    public static void main(String[] args) throws Exception {
        System.out.println("Produtor Cliente");
        String FILA_CLIENTE_BANCO = "sendClienteBanco";

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);

        Banco cliente = new Banco("2370","55698", "454545", "1235487548790", 100.00);
        Gson gson = new Gson();
        String mensagem = gson.toJson(cliente);


        try (Connection connection = connectionFactory.newConnection();Channel channel = connection.createChannel()) {
            ((Channel) channel).queueDeclare(FILA_CLIENTE_BANCO, true, false, false, null);
            ((Channel) channel).basicPublish("", FILA_CLIENTE_BANCO, null, mensagem.getBytes());
            System.out.println("Mensagem: " + mensagem);
        }
        System.out.println("Fim Produtor Cliente");
    }
}
