package br.edu.ifpb.pd;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


public class ProdutorVisa {
    public  ProdutorVisa(String msg) throws Exception{
            System.out.println("Inicio Produtor Visa");
            String FILA_VISA_CLIENTE = "sendVisaCliente";
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost("localhost");
            connectionFactory.setPort(5672);

            try (Connection connection = connectionFactory.newConnection();Channel channel = connection.createChannel()) {
                ((com.rabbitmq.client.Channel) channel).queueDeclare(FILA_VISA_CLIENTE, true, false, false, null);
                ((com.rabbitmq.client.Channel) channel).basicPublish("", FILA_VISA_CLIENTE, null, msg.getBytes());
                System.out.println("Enviando mensagem para fila do cliente: " + msg);

            }
            System.out.println("Fim Produtor Visa");

        }
}
