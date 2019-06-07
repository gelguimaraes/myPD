package br.edu.ifpb.pd;

import com.rabbitmq.client.*;

import java.util.Map;
import java.util.Random;

public class ProcessoB {
    public static void main(String[] args) throws Exception {
        Middleware m = new Middleware();
        Random random = new Random();
        int ts = random.nextInt(10);
        System.out.println("Ts de B: " + ts);
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("AparaB", false, false, false, (Map)null);

        DeliverCallback callback = (consumerTag, delivery) -> {String msgTimedA = new String(delivery.getBody());

            try{
                System.out.println("Receiver (" + msgTimedA + ")");
                String msgTimed  = m.receive_timed(ts, msgTimedA);
                String msgSplit[] = msgTimed.split(":");
                int newts = Integer.parseInt(msgSplit[0]);
                String msg = "MensagemB";
                String newMsgTimedA = m.send_timed(msg, newts);
                System.out.println("Middleware send (" + newMsgTimedA + ")");
                connectionFactory.setHost("localhost");
                connectionFactory.setPort(5672);
                channel.queueDeclare("BparaA", false, false, false, null);
                channel.basicPublish("", "BparaA", null, newMsgTimedA.getBytes());
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        };
        channel.basicConsume("AparaB", true, callback, (consumerTag) -> {
        });

    }
}
