package br.com.grupocasasbahia.desafiobackend.vendedor.integration;

import br.com.grupocasasbahia.desafiobackend.vendedor.application.inputs.InputCriarVendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces.ICriarVendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.core.enums.TipoDeContratacao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;


import java.time.LocalDate;

@ActiveProfiles("test")
@SpringBootTest
public class VendedorIntegrationTest {

    @Autowired
    private ICriarVendedor _criarVendedorUseCase;

    @Test
    public void DeveCadastrarVendedorPJ(){
        //arrange
        String nome = "Jonathan de Souza";
        LocalDate dataNascimento = LocalDate.parse("1997-01-24");
        String documento = "33041260094711";
        String email = "john@gmail.com";
        TipoDeContratacao tipoContratacao = TipoDeContratacao.PJ;
        int numeroFilial = 1;

        //act
        InputCriarVendedor input = new InputCriarVendedor(nome, dataNascimento, documento, email,tipoContratacao, numeroFilial);
        var resultado = _criarVendedorUseCase.executeAsync(input).join();

        //assert
        assertEquals("00000001-PJ", resultado.getDados().matricula());
    }
}
