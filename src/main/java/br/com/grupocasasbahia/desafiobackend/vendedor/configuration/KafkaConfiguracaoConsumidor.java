package br.com.grupocasasbahia.desafiobackend.vendedor.configuration;

import br.com.grupocasasbahia.desafiobackend.vendedor.application.inputs.InputCriarVendedor;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfiguracaoConsumidor {

    @Bean
    public ConsumerFactory<String, InputCriarVendedor> fabricaConsumidor(){
        Map<String, Object> configuracoes = new HashMap<>();
        configuracoes.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        configuracoes.put(ConsumerConfig.GROUP_ID_CONFIG, "grupo-vendedor");
        configuracoes.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configuracoes.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonSerialize.class);
        configuracoes.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return new DefaultKafkaConsumerFactory<>(configuracoes, new StringDeserializer(), new JsonDeserializer<>(InputCriarVendedor.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, InputCriarVendedor> fabricaDeContainersOuvintesKafka(){
        ConcurrentKafkaListenerContainerFactory<String, InputCriarVendedor> fabrica = new ConcurrentKafkaListenerContainerFactory<>();
        fabrica.setConsumerFactory(fabricaConsumidor());
        return fabrica;
    }
}
