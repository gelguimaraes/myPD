package pd;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.tools.json.JSONWriter;

import java.util.ArrayList;
import java.util.List;

public class Produtor {
    public static void main(String[] args) throws Exception {
        String NOME_FILA = "filaJson";

        List<Livro> livros = new ArrayList<>();
        for (int i=0; i<11; i++) {
            Livro livro = new Livro("Livro"+i, "Letras");
            livros.add(livro);
        }
        JSONWriter jsonWriter = new JSONWriter();
        String mensagem = jsonWriter.write(livros);

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);

        try (Connection connection = connectionFactory.newConnection();Channel channel = connection.createChannel()) {
            ((com.rabbitmq.client.Channel) channel).queueDeclare(NOME_FILA, false, false, false, null);
            ((com.rabbitmq.client.Channel) channel).basicPublish("", NOME_FILA, null, mensagem.getBytes());
            System.out.println("Mensagem: " + mensagem);
        }
    }
}
