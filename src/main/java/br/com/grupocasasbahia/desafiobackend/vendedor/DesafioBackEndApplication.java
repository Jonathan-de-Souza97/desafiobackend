package br.com.grupocasasbahia.desafiobackend.vendedor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication

public class DesafioBackEndApplication {
    public static void main(String[] args) {
        SpringApplication.run(DesafioBackEndApplication.class,args);
    }
}
