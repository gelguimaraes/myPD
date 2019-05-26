package br.edu.ifpb.pd;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class ProdutorBanco {
    public ProdutorBanco(String msgJson) throws Exception{
        System.out.println("Inicio Produtor Banco");
        String FILA_BANCO_VISA = "sendBancoVisa";
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);

        try (Connection connection = connectionFactory.newConnection();Channel channel = connection.createChannel()) {
            ((com.rabbitmq.client.Channel) channel).queueDeclare(FILA_BANCO_VISA, true, false, false, null);
            ((com.rabbitmq.client.Channel) channel).basicPublish("", FILA_BANCO_VISA, MessageProperties.PERSISTENT_TEXT_PLAIN, msgJson.getBytes());
            System.out.println("Enviando mensagem para fila do visa: " + msgJson);

        }
        System.out.println("Fim Produtor Banco");

    }

}
