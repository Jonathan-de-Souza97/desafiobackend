package br.com.grupocasasbahia.desafiobackend.vendedor.integration;

import br.com.grupocasasbahia.desafiobackend.vendedor.application.inputs.InputCriarVendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.inputs.InputEditarVendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces.ICriarVendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces.IEditarVendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces.IVendedorRepository;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.response.Resposta;
import br.com.grupocasasbahia.desafiobackend.vendedor.core.entities.Vendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.core.enums.TipoDeContratacao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
public class EditarVendedorTest {

    @Autowired
    private IEditarVendedor _editarVendedorUseCase;

    @Autowired
    private IVendedorRepository _vendedorRepository;

    @Test
    public void DeveEditarVendedor(){
        //arrange
        int numeroMatricula = 15325646;
        String nome = "Jonathan de Souza";
        LocalDate dataNascimento = LocalDate.parse("1997-01-24");
        String documento = "59.931.636/0001-08";
        String email = "johnTest@gmail.com";
        TipoDeContratacao tipoContratacao = TipoDeContratacao.PJ;
        int numeroFilial = 1;

        //act
        Vendedor input = new Vendedor(nome, dataNascimento, documento, email,tipoContratacao, numeroFilial);
        input.setMatricula(numeroMatricula,tipoContratacao);
        var vendedorCriado = _vendedorRepository.salvar(input).join();


        //arrange
        String nomeEditado = "Joao de Souza";
        LocalDate dataNascimentoEditada = LocalDate.parse("2000-01-24");
        String documentoEditado = "720.404.200-02";
        String emailEditado = "johnEditado@gmail.com";
        TipoDeContratacao tipoContratacaoEditado = TipoDeContratacao.CLT;
        int numeroFilialEditada = 2;

        //act
        InputEditarVendedor inputEditarVendedor = new InputEditarVendedor(vendedorCriado.getDados(),nomeEditado, dataNascimentoEditada, documentoEditado, emailEditado, tipoContratacaoEditado, numeroFilialEditada);
        Resposta<String> resultado = _editarVendedorUseCase.executeAsync(inputEditarVendedor).join();



        //assert
        assertTrue(resultado.Sucedido());
        assertEquals("Operação realizada", resultado.getMensagem());
        assertTrue(resultado.getDados().contains("CLT"));
    }

    @Test
    public void NaoDeveEditarVendedorNaoCadastrado(){
        //arrange
        String nomeEditado = "Joao de Souza";
        LocalDate dataNascimentoEditada = LocalDate.parse("2000-01-24");
        String documentoEditado = "287.755.180-66";
        String emailEditado = "johnEditado@gmail.com";
        TipoDeContratacao tipoContratacaoEditado = TipoDeContratacao.CLT;
        int numeroFilialEditada = 2;

        //act
        InputEditarVendedor inputEditarVendedor = new InputEditarVendedor("00000000-PJ",nomeEditado, dataNascimentoEditada, documentoEditado, emailEditado, tipoContratacaoEditado, numeroFilialEditada);
        Resposta<String> resultado = _editarVendedorUseCase.executeAsync(inputEditarVendedor).join();



        //assert
        assertFalse(resultado.Sucedido());
        assertEquals("Vendedor não cadastrado", resultado.getMensagem());
        assertNull(resultado.getDados());
    }

    @Test
    public void NaoDeveEditarVendedorDocumentoJaCadastrado(){
        //arrange

        int numeroMatricula = 15326646;
        String nome = "Jonathan de Souza";
        LocalDate dataNascimento = LocalDate.parse("1997-01-24");
        String documento = "436.881.850-48";
        String email = "johnTest@gmail.com";
        TipoDeContratacao tipoContratacao = TipoDeContratacao.Outsourcing;
        int numeroFilial = 1;

        //act
        Vendedor input = new Vendedor(nome, dataNascimento, documento, email,tipoContratacao, numeroFilial);

        input.setMatricula(numeroMatricula, tipoContratacao);

        var vendedorCriado = _vendedorRepository.salvar(input).join();

        //arrange
        String nomeEditado = "Joao de Souza";
        LocalDate dataNascimentoEditada = LocalDate.parse("2000-01-24");
        String documentoEditado = "436.881.850-48";
        String emailEditado = "johnEditado@gmail.com";
        TipoDeContratacao tipoContratacaoEditado = TipoDeContratacao.Outsourcing;
        int numeroFilialEditada = 2;

        //act
        InputEditarVendedor inputEditarVendedor = new InputEditarVendedor(vendedorCriado.getDados(),nomeEditado, dataNascimentoEditada, documentoEditado, emailEditado, tipoContratacaoEditado, numeroFilialEditada);
        Resposta<String> resultado = _editarVendedorUseCase.executeAsync(inputEditarVendedor).join();



        //assert
        assertFalse(resultado.Sucedido());
        assertEquals("Documento já cadastrado para esse tipo de contratação", resultado.getMensagem());
        assertNull(resultado.getDados());
    }

    @Test
    public void NaoDeveEditarVendedorDocumentoInvalido(){
        //arrange
        String nomeEditado = "Joao de Souza";
        LocalDate dataNascimentoEditada = LocalDate.parse("2000-01-24");
        String documentoEditado = "16.820.720/0001-33";
        String emailEditado = "johnEditado@gmail.com";
        TipoDeContratacao tipoContratacaoEditado = TipoDeContratacao.CLT;
        int numeroFilialEditada = 2;

        //act
        InputEditarVendedor inputEditarVendedor = new InputEditarVendedor("00000000-PJ",nomeEditado, dataNascimentoEditada, documentoEditado, emailEditado, tipoContratacaoEditado, numeroFilialEditada);
        Resposta<String> resultado = _editarVendedorUseCase.executeAsync(inputEditarVendedor).join();



        //assert
        assertFalse(resultado.Sucedido());
        assertEquals("CPF inválido", resultado.getMensagem());
        assertNull(resultado.getDados());
    }

    @Test
    public void NaoDeveEditarVendedorDocumentoFilialNaoCadastrada(){
        //arrange
        int numeroMatricula = 15325646;
        String nome = "Jonathan de Souza";
        LocalDate dataNascimento = LocalDate.parse("1997-01-24");
        String documento = "552.013.890-74";
        String email = "johnTest@gmail.com";
        TipoDeContratacao tipoContratacao = TipoDeContratacao.CLT;
        int numeroFilial = 1;

        //act
        Vendedor input = new Vendedor(nome, dataNascimento, documento, email,tipoContratacao, numeroFilial);
        input.setMatricula(numeroMatricula,tipoContratacao);
        var vendedorCriado = _vendedorRepository.salvar(input).join();


        //arrange
        String nomeEditado = "Joao de Souza";
        LocalDate dataNascimentoEditada = LocalDate.parse("2000-01-24");
        String documentoEditado = "707.377.550-59";
        String emailEditado = "johnEditado@gmail.com";
        TipoDeContratacao tipoContratacaoEditado = TipoDeContratacao.CLT;
        int numeroFilialEditada = 4;

        //act
        InputEditarVendedor inputEditarVendedor = new InputEditarVendedor(vendedorCriado.getDados(),nomeEditado, dataNascimentoEditada, documentoEditado, emailEditado, tipoContratacaoEditado, numeroFilialEditada);
        Resposta<String> resultado = _editarVendedorUseCase.executeAsync(inputEditarVendedor).join();



        //assert
        assertFalse(resultado.Sucedido());
        assertEquals("Filial não cadastrada", resultado.getMensagem());
        assertNull(resultado.getDados());
    }
}
