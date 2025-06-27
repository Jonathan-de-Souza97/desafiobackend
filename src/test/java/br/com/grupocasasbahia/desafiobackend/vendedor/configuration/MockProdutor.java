package br.com.grupocasasbahia.desafiobackend.vendedor.configuration;

import br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces.IProdutor;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class MockProdutor {
    @Bean
    public IProdutor produtor() {
        return Mockito.mock(IProdutor.class);
    }
}
