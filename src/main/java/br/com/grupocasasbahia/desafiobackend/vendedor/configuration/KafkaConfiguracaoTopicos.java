package br.com.grupocasasbahia.desafiobackend.vendedor.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfiguracaoTopicos {
    @Bean
    public NewTopic CriarVendedorTopico(){
        return TopicBuilder.name("criar-vendedor")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
