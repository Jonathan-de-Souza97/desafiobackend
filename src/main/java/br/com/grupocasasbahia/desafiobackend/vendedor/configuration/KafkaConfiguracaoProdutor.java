package br.com.grupocasasbahia.desafiobackend.vendedor.configuration;


import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfiguracaoProdutor {
    @Bean
    public ProducerFactory<String, Object> fabricaProdutor(){
        Map<String, Object> configuracoes = new HashMap<>();
        configuracoes.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configuracoes.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configuracoes.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configuracoes);
    }

    @Bean
    public KafkaTemplate<String,Object>TempleteKafka(){
        return new KafkaTemplate<>(fabricaProdutor());
    }
}
