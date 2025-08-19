package br.com.grupocasasbahia.desafiobackend.vendedor.infra.drivers;

import br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces.IProdutor;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.response.Resposta;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaVendedorProdutor implements IProdutor {
    private final KafkaTemplate<String,Object> _kafkaTemplate;

    public KafkaVendedorProdutor(KafkaTemplate<String, Object> kafkaTemplate) {
        _kafkaTemplate = kafkaTemplate;
    }

    @Override
    public CompletableFuture<Resposta<String>> enviarMensagem(String topico, String requisicaoId, Object input) {

        try
        {
            _kafkaTemplate.send(topico,requisicaoId, input).join();
            return CompletableFuture.completedFuture(Resposta.successo(null));
        }
        catch (Exception ex)
        {
            return CompletableFuture.completedFuture(Resposta.erro("Falha ao enviar mensagem"));
        }
    }
}
