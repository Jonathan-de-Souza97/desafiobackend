package br.com.grupocasasbahia.desafiobackend.vendedor.configuration;

import br.com.grupocasasbahia.desafiobackend.vendedor.infra.databases.JdbcTemplateAdapter;
import br.com.grupocasasbahia.desafiobackend.vendedor.infra.interfaces.ConexaoBancoDeDados;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class BeansConfiguration {

    @Bean
    public ConexaoBancoDeDados conexaoBancoDeDados(JdbcTemplate jdbcTemplate){
        return new JdbcTemplateAdapter(jdbcTemplate);
    }
}
