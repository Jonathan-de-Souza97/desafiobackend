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
public class CriarVendedorTest {

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
        assertTrue(resultado.Sucedido());
        assertEquals("Operação realizada", resultado.getMensagem());
        assertEquals(11,resultado.getDados().length());
        assertEquals("PJ",resultado.getDados().substring(resultado.getDados().length() -2));
    }

    @Test
    public void DeveCadastrarVendedorCLT(){
        //arrange
        String nome = "Jonathan de Souza";
        LocalDate dataNascimento = LocalDate.parse("1997-01-24");
        String documento = "542.983.940-00";
        String email = "john@gmail.com";
        TipoDeContratacao tipoContratacao = TipoDeContratacao.CLT;
        int numeroFilial = 1;

        //act
        InputCriarVendedor input = new InputCriarVendedor(nome, dataNascimento, documento, email,tipoContratacao, numeroFilial);
        var resultado = _criarVendedorUseCase.executeAsync(input).join();

        //assert
        assertTrue(resultado.Sucedido());
        assertEquals("Operação realizada", resultado.getMensagem());
        assertEquals(12,resultado.getDados().length());
        assertEquals("CLT",resultado.getDados().substring(resultado.getDados().length() -3));
    }

    @Test
    public void DeveCadastrarVendedorOUT(){
        //arrange
        String nome = "Jonathan de Souza";
        LocalDate dataNascimento = LocalDate.parse("1997-01-24");
        String documento = "428.867.440-93";
        String email = "john@gmail.com";
        TipoDeContratacao tipoContratacao = TipoDeContratacao.Outsourcing;
        int numeroFilial = 1;

        //act
        InputCriarVendedor input = new InputCriarVendedor(nome, dataNascimento, documento, email,tipoContratacao, numeroFilial);
        var resultado = _criarVendedorUseCase.executeAsync(input).join();

        //assert
        assertTrue(resultado.Sucedido());
        assertEquals("Operação realizada", resultado.getMensagem());
        assertEquals(12,resultado.getDados().length());
        assertEquals("OUT",resultado.getDados().substring(resultado.getDados().length() -3));
    }

    @Test
    public void NaoDeveCadastrarVendedorDocumentoJaCadastrado(){
        //arrange
        String nome = "Jonathan de Souza";
        LocalDate dataNascimento = LocalDate.parse("1997-01-24");
        String documento = "428.867.440-93";
        String email = "john@gmail.com";
        TipoDeContratacao tipoContratacao = TipoDeContratacao.Outsourcing;
        int numeroFilial = 1;

        //act
        InputCriarVendedor input = new InputCriarVendedor(nome, dataNascimento, documento, email,tipoContratacao, numeroFilial);
        var PrimeiroInput = _criarVendedorUseCase.executeAsync(input).join();
        var resultado = _criarVendedorUseCase.executeAsync(input).join();

        //assert
        assertFalse(resultado.Sucedido());
        assertEquals("Documento já cadastrado", resultado.getMensagem());
        assertNull(resultado.getDados());
    }

    @Test
    public void NaoDeveCadastrarVendedorEmailInvalido(){
        //arrange
        String nome = "Jonathan de Souza";
        LocalDate dataNascimento = LocalDate.parse("1997-01-24");
        String documento = "282.112.900-90";
        String email = "john_gmail.com";
        TipoDeContratacao tipoContratacao = TipoDeContratacao.Outsourcing;
        int numeroFilial = 1;

        //act
        InputCriarVendedor input = new InputCriarVendedor(nome, dataNascimento, documento, email,tipoContratacao, numeroFilial);
        var resultado = _criarVendedorUseCase.executeAsync(input).join();

        //assert
        assertFalse(resultado.Sucedido());
        assertEquals("Email inválido", resultado.getMensagem());
        assertNull(resultado.getDados());
    }
}
