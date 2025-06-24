package br.com.grupocasasbahia.desafiobackend.vendedor.integration;

import br.com.grupocasasbahia.desafiobackend.vendedor.application.inputs.InputCriarVendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces.IApagarVendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces.ICriarVendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.outputs.OutputCriarUsuario;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.response.Resposta;
import br.com.grupocasasbahia.desafiobackend.vendedor.core.enums.TipoDeContratacao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
public class ApagarVendedorTest {

    @Autowired
    private IApagarVendedor _apagarVendedorUseCase;
    @Autowired
    private ICriarVendedor _criarVendedorUseCase;

    @Test
    public void DeveApagarUmVendedor(){

        //arrange
        String matricula = "00000001-CLT";
        String nome = "Jonathan de Souza";
        LocalDate dataNascimento = LocalDate.parse("1997-01-24");
        String documento = "85.684.876/0001-57";
        String email = "john@gmail.com";
        TipoDeContratacao tipoContratacao = TipoDeContratacao.PJ;
        int numeroFilial = 1;

        //act
        InputCriarVendedor input = new InputCriarVendedor(nome, dataNascimento, documento, email,tipoContratacao, numeroFilial);
        Resposta<OutputCriarUsuario> vendedor = _criarVendedorUseCase.executeAsync(input).join();
        Resposta<String> resultado = _apagarVendedorUseCase.executeAsync(vendedor.getDados().matricula()).join();

        //Assert
        assertTrue(resultado.Sucedido());
        assertEquals("Operação realizada", resultado.getMensagem());
        assertEquals(vendedor.getDados().matricula(), resultado.getDados());
    }

    @Test
    public void NaoDeveApagarUmVendedorMatriculaNaoCadastrada(){

        //arrange
        String matricula = "99999999-CLT";

        //act
        Resposta<String> resultado = _apagarVendedorUseCase.executeAsync(matricula).join();

        //Assert
        assertFalse(resultado.Sucedido());
        assertNull(resultado.getDados());
        assertEquals("Vendedor não cadastrado", resultado.getMensagem());
    }
}
