package br.edu.ifpb.pd;

import com.rabbitmq.client.*;

import java.util.Map;
import java.util.Random;

public class ProcessoA {
    public static void main(String[] args) throws Exception {
        Random random = new Random();
        int ts = random.nextInt(10);
        System.out.println("Ts de A: " + ts);
        Middleware m = new Middleware();
        String msg = "MensagemA";
        String msgTimedA = m.send_timed(msg, ts);
        System.out.println("Middleware send (" + msgTimedA + ")");
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);

        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare("AparaB", false, false, false, null);
            channel.basicPublish("", "AparaB", null, msgTimedA.getBytes());
        }
        catch (Exception e){
            System.out.println(e.getMessage());

        }

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("BparaA", false, false, false, (Map)null);

        DeliverCallback callback = (consumerTag, delivery) -> {
            String msgTimedB = new String(delivery.getBody());
            System.out.println("Receiver (" + msgTimedB + ")");
         try{
             String newMsgTimed = m.receive_timed(ts, msgTimedB);
             String msgSplit[] = newMsgTimed.split(":");
             int newts = Integer.parseInt(msgSplit[0]);
             System.out.println("Novo ts de A: " +newts);
         }
         catch (Exception e){
             System.out.println(e.getMessage());
         }

        };
        channel.basicConsume("BparaA", true, callback, (consumerTag) -> {
        });

    }

}
