package br.com.grupocasasbahia.desafiobackend.vendedor.infra.drivers;

import br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces.IProdutor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaVendedorProdutor implements IProdutor {
    private final KafkaTemplate<String,Object> _kafkaTemplate;

    public KafkaVendedorProdutor(KafkaTemplate<String, Object> kafkaTemplate) {
        _kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void enviarMensagem(String topico, String requisicaoId, Object input) {

        _kafkaTemplate.send(topico,requisicaoId, input);
    }
}
