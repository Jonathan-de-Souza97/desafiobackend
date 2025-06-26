package br.com.grupocasasbahia.desafiobackend.vendedor.integration;

import br.com.grupocasasbahia.desafiobackend.vendedor.application.inputs.InputCriarVendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces.ICriarVendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces.IVendedorQueryServices;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.outputs.OutputVendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.response.Resposta;
import br.com.grupocasasbahia.desafiobackend.vendedor.core.enums.TipoDeContratacao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@SpringBootTest
public class BuscarTodosVendedoresTest {

    @Autowired
    private IVendedorQueryServices _queryServices;
    @Autowired
    private ICriarVendedor _criarVendedorUseCase;
    @Test
    public void DeveRetornarTodosVendedoresCadastrados(){
        //Arrange
        InputCriarVendedor input1 = new InputCriarVendedor("Jonathan de Souza", LocalDate.parse("1964-01-24"), "22.921.015/0001-25", "john1@gmail.com",TipoDeContratacao.PJ, 1);
        InputCriarVendedor input2 = new InputCriarVendedor("Jonathan de Santos", LocalDate.parse("1978-01-24"), "418.291.230-68", "john2@gmail.com",TipoDeContratacao.CLT, 2);
        InputCriarVendedor input3 = new InputCriarVendedor("Jonathan de Silva", LocalDate.parse("2000-01-24"), "398.772.510-94", "john3@gmail.com",TipoDeContratacao.Outsourcing, 3);

        //Act
        var act1 = _criarVendedorUseCase.executeAsync(input1).join();
        var act2 =_criarVendedorUseCase.executeAsync(input2).join();
        var act3 =_criarVendedorUseCase.executeAsync(input3).join();

        Resposta<List<OutputVendedor>> vendedores = _queryServices.buscarTodosVendedoresAsync().join();

        //Assert
        assertTrue(vendedores.Sucedido());
        assertEquals("Operação realizada", vendedores.getMensagem());
        assertTrue(vendedores.getDados().size() > 0);

    }
}
