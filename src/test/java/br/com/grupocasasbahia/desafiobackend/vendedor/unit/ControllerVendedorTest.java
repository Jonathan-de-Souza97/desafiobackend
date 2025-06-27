package br.com.grupocasasbahia.desafiobackend.vendedor.unit;

import br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces.IApagarVendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces.ICriarVendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces.IEditarVendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces.IVendedorQueryServices;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.outputs.OutputVendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.response.Resposta;
import br.com.grupocasasbahia.desafiobackend.vendedor.controller.VendedorController;
import br.com.grupocasasbahia.desafiobackend.vendedor.core.entities.Filial;
import br.com.grupocasasbahia.desafiobackend.vendedor.core.enums.TipoDeContratacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

public class ControllerVendedorTest {
    private ICriarVendedor _criarVendedorUseCase;
    private IEditarVendedor _editarVendedor;
    private IApagarVendedor _apagarVendedor;
    private IVendedorQueryServices _queryServices;
    private VendedorController _controller;

    @BeforeEach
    public void setUp(){
        _criarVendedorUseCase = Mockito.mock(ICriarVendedor.class);
        _editarVendedor = Mockito.mock(IEditarVendedor.class);
        _apagarVendedor = Mockito.mock(IApagarVendedor.class);
        _queryServices = Mockito.mock(IVendedorQueryServices.class);
        _controller = new VendedorController(_criarVendedorUseCase, _editarVendedor, _apagarVendedor, _queryServices);
    }

    private List<OutputVendedor> vendedoresFake(){
        //arrange
        String matricula = "00000001-CLT";
        String nome = "Jonathan de Souza";
        LocalDate dataNascimento = LocalDate.parse("1997-01-24");
        String documento = "33041260094711";
        String email = "john@gmail.com";
        TipoDeContratacao tipoContratacao = TipoDeContratacao.PJ;
        int numeroFilial = 1;

        Filial filial = new Filial(1, "Berrini", "66.235.624/0001-04", "São Paulo", "SP", "Rua", true, LocalDateTime.of(2020,06,10, 10,20), LocalDateTime.of(2023,01,13, 12,53));


        OutputVendedor vendedor1 = new OutputVendedor(matricula, nome, dataNascimento, documento,tipoContratacao, email, filial);
        OutputVendedor vendedor2 = new OutputVendedor(matricula, nome, dataNascimento, documento,tipoContratacao, email, filial);

        List<OutputVendedor> vendedores = new ArrayList<>();

        vendedores.add(vendedor1);
        vendedores.add(vendedor2);

        return vendedores;

    }

    private OutputVendedor vendedorFake(){
        //arrange
        String matricula = "00000001-CLT";
        String nome = "Jonathan de Souza";
        LocalDate dataNascimento = LocalDate.parse("1997-01-24");
        String documento = "33041260094711";
        String email = "john@gmail.com";
        TipoDeContratacao tipoContratacao = TipoDeContratacao.PJ;
        int numeroFilial = 1;
        Filial filial = new Filial(1, "Berrini", "66.235.624/0001-04", "São Paulo", "SP", "Rua", true, LocalDateTime.of(2020,06,10, 10,20), LocalDateTime.of(2023,01,13, 12,53));

        OutputVendedor vendedor = new OutputVendedor(matricula, nome, dataNascimento, documento,tipoContratacao, email, filial);

        return vendedor;

    }

    @Test
    public void DeveRetornar200QuandoCriarVendedor(){
        //arrange
        Resposta<String> resposta = Resposta.successo("00000001-PJ");
        Mockito.when(_criarVendedorUseCase.executeAsync(any()))
                .thenReturn(CompletableFuture.completedFuture(resposta));

        //act
        ResponseEntity<Resposta<String>> acao = _controller.criar(any()).join();

        //assert
        assertEquals(200, acao.getStatusCode().value());
        assertTrue(acao.getBody().Sucedido());
        assertEquals("Operação realizada",acao.getBody().getMensagem());
        assertEquals("00000001-PJ", acao.getBody().getDados());
    }

    @Test
    public void DeveRetornar200QuandoEditarVendedor(){
        //arrange
        Resposta<String> resposta = Resposta.successo("00000001-PJ");
        Mockito.when(_editarVendedor.executeAsync(any()))
                .thenReturn(CompletableFuture.completedFuture(resposta));

        //act
        ResponseEntity<Resposta<String>> acao = _controller.editar(any()).join();

        //assert
        assertEquals(200, acao.getStatusCode().value());
        assertTrue(acao.getBody().Sucedido());
        assertEquals("Operação realizada",acao.getBody().getMensagem());
        assertEquals("00000001-PJ", acao.getBody().getDados());
    }

    @Test
    public void DeveRetornar500QuandoEditarVendedorFalhaInterna(){
        //arrange
        Resposta<String> resposta = Resposta.erro("Falha interna tente novamente");
        Mockito.when(_editarVendedor.executeAsync(any()))
                .thenReturn(CompletableFuture.completedFuture(resposta));

        //act
        ResponseEntity<Resposta<String>> acao = _controller.editar(any()).join();

        //assert
        assertEquals(500, acao.getStatusCode().value());
        assertFalse(acao.getBody().Sucedido());
        assertEquals("Falha interna tente novamente",acao.getBody().getMensagem());
        assertEquals(null, acao.getBody().getDados());
    }

    @Test
    public void DeveRetornar400QuandoEditarVendedorEmailInvalido(){
        //arrange
        Resposta<String> resposta = Resposta.erro("Email inválido");
        Mockito.when(_editarVendedor.executeAsync(any()))
                .thenReturn(CompletableFuture.completedFuture(resposta));

        //act
        ResponseEntity<Resposta<String>> acao = _controller.editar(any()).join();

        //assert
        assertEquals(400, acao.getStatusCode().value());
        assertFalse(acao.getBody().Sucedido());
        assertEquals("Email inválido",acao.getBody().getMensagem());
        assertEquals(null, acao.getBody().getDados());
    }

    @Test
    public void DeveRetornar200QuandoApagarVendedor(){
        //arrange
        Resposta<String> resposta = Resposta.successo("00000001-PJ");
        Mockito.when(_apagarVendedor.executeAsync(any()))
                .thenReturn(CompletableFuture.completedFuture(resposta));

        //act
        ResponseEntity<Resposta<String>> acao = _controller.apagar(any()).join();

        //assert
        assertEquals(200, acao.getStatusCode().value());
        assertTrue(acao.getBody().Sucedido());
        assertEquals("Operação realizada",acao.getBody().getMensagem());
        assertEquals("00000001-PJ", acao.getBody().getDados());
    }

    @Test
    public void DeveRetornar500QuandoApagarVendedorFalhaInterna(){
        //arrange
        Resposta<String> resposta = Resposta.erro("Falha interna tente novamente");
        Mockito.when(_apagarVendedor.executeAsync(any()))
                .thenReturn(CompletableFuture.completedFuture(resposta));

        //act
        ResponseEntity<Resposta<String>> acao = _controller.apagar(any()).join();

        //assert
        assertEquals(500, acao.getStatusCode().value());
        assertFalse(acao.getBody().Sucedido());
        assertEquals("Falha interna tente novamente",acao.getBody().getMensagem());
        assertEquals(null, acao.getBody().getDados());
    }

    @Test
    public void DeveRetornar400QuandoApagarVendedorNaoCadastrado(){
        //arrange
        Resposta<String> resposta = Resposta.erro("Vendedor não cadastrado");
        Mockito.when(_apagarVendedor.executeAsync(any()))
                .thenReturn(CompletableFuture.completedFuture(resposta));

        //act
        ResponseEntity<Resposta<String>> acao = _controller.apagar(any()).join();

        //assert
        assertEquals(400, acao.getStatusCode().value());
        assertFalse(acao.getBody().Sucedido());
        assertEquals("Vendedor não cadastrado",acao.getBody().getMensagem());
        assertEquals(null, acao.getBody().getDados());
    }

    @Test
    public void DeveRetornar200QuandoBuscarTodosVendedores(){
        //arrange
        Resposta<List<OutputVendedor>> resposta = Resposta.successo(vendedoresFake());
        Mockito.when(_queryServices.buscarTodosVendedoresAsync())
                .thenReturn(CompletableFuture.completedFuture(resposta));

        //act
        ResponseEntity<Resposta<List<OutputVendedor>>> acao = _controller.buscarTodos().join();

        //assert
        assertEquals(200, acao.getStatusCode().value());
        assertTrue(acao.getBody().Sucedido());
        assertEquals("Operação realizada",acao.getBody().getMensagem());
        assertNotNull( acao.getBody().getDados());
    }

    @Test
    public void DeveRetornar500QuandoBuscarTodosVendedoresEDerFalhaInterna(){
        //arrange
        Resposta<List<OutputVendedor>> resposta = Resposta.erro("Falha interna tente novamente");
        Mockito.when(_queryServices.buscarTodosVendedoresAsync())
                .thenReturn(CompletableFuture.completedFuture(resposta));

        //act
        ResponseEntity<Resposta<List<OutputVendedor>>> acao = _controller.buscarTodos().join();

        //assert
        assertEquals(500, acao.getStatusCode().value());
        assertFalse(acao.getBody().Sucedido());
        assertEquals("Falha interna tente novamente",acao.getBody().getMensagem());
        assertNull( acao.getBody().getDados());
    }

    @Test
    public void DeveRetornar200QuandoBuscarVendedorPorMatricula(){
        //arrange
        Resposta<OutputVendedor> resposta = Resposta.successo(vendedorFake());
        Mockito.when(_queryServices.buscarVendedorPorMatriculaAsync(any()))
                .thenReturn(CompletableFuture.completedFuture(resposta));

        //act
        ResponseEntity<Resposta<OutputVendedor>> acao = _controller.buscarPorMatricula(any()).join();

        //assert
        assertEquals(200, acao.getStatusCode().value());
        assertTrue(acao.getBody().Sucedido());
        assertEquals("Operação realizada",acao.getBody().getMensagem());
        assertNotNull(acao.getBody().getDados());
    }

    @Test
    public void DeveRetornar500QuandoBuscarVendedorPorMatricula(){
        //arrange
        Resposta<OutputVendedor> resposta = Resposta.erro("Falha interna tente novamente");
        Mockito.when(_queryServices.buscarVendedorPorMatriculaAsync(any()))
                .thenReturn(CompletableFuture.completedFuture(resposta));

        //act
        ResponseEntity<Resposta<OutputVendedor>> acao = _controller.buscarPorMatricula(any()).join();

        //assert
        assertEquals(500, acao.getStatusCode().value());
        assertFalse(acao.getBody().Sucedido());
        assertEquals("Falha interna tente novamente",acao.getBody().getMensagem());
        assertNull( acao.getBody().getDados());
    }

    @Test
    public void DeveRetornar200QuandoBuscarVendedorPorDocumento(){
        //arrange
        Resposta<List<OutputVendedor>> resposta = Resposta.successo(vendedoresFake());

        Mockito.when(_queryServices.buscarVendedorPorDocumentoAsync(any()))
                .thenReturn(CompletableFuture.completedFuture(resposta));

        //act
        ResponseEntity<Resposta<List<OutputVendedor>>> acao = _controller.buscarPorDocumento(any()).join();

        //assert
        assertEquals(200, acao.getStatusCode().value());
        assertTrue(acao.getBody().Sucedido());
        assertEquals("Operação realizada",acao.getBody().getMensagem());
        assertNotNull( acao.getBody().getDados());
    }

    @Test
    public void DeveRetornar500QuandoBuscarVendedorPorDocumento(){
        //arrange
        Resposta<List<OutputVendedor>> resposta = Resposta.erro("Falha interna tente novamente");
        Mockito.when(_queryServices.buscarVendedorPorDocumentoAsync(any()))
                .thenReturn(CompletableFuture.completedFuture(resposta));

        //act
        ResponseEntity<Resposta<List<OutputVendedor>>> acao = _controller.buscarPorDocumento(any()).join();

        //assert
        assertEquals(500, acao.getStatusCode().value());
        assertFalse(acao.getBody().Sucedido());
        assertEquals("Falha interna tente novamente",acao.getBody().getMensagem());
        assertNull( acao.getBody().getDados());
    }
}
