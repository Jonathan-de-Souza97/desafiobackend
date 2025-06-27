package br.com.grupocasasbahia.desafiobackend.vendedor.unit;

import br.com.grupocasasbahia.desafiobackend.vendedor.application.inputs.InputCriarVendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces.ICriarVendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces.IProcessarQuery;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces.IProdutor;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.outputs.OutputProcessamento;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.response.Resposta;
import br.com.grupocasasbahia.desafiobackend.vendedor.core.enums.StatusProcessamento;
import br.com.grupocasasbahia.desafiobackend.vendedor.core.enums.TipoDeContratacao;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicReference;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;


@EmbeddedKafka(
        partitions = 1,
        topics = { "criar-vendedor" },
        bootstrapServersProperty = "spring.kafka.bootstrap-servers"
)
@ActiveProfiles("test")
@EnableKafka
@SpringBootTest
public class ProcessarCriacaoVendedorTest {

    @Autowired
    private ICriarVendedor _criarVendedorUseCase;

    @Autowired
    private IProdutor _produtor;

    @Autowired
    private IProcessarQuery _processarQuery;

    @Test
    public void DeveCadastrarVendedorCLT() throws InterruptedException {
        // arrange
        String nome = "Jonathan de Souza";
        LocalDate dataNascimento = LocalDate.parse("1997-01-24");
        String documento = "542.983.940-00";
        String email = "john@gmail.com";
        TipoDeContratacao tipoContratacao = TipoDeContratacao.CLT;
        int numeroFilial = 1;

        // act
        InputCriarVendedor input = new InputCriarVendedor(nome, dataNascimento, documento, email, tipoContratacao, numeroFilial);
        Resposta<String> respostaCriacao = _criarVendedorUseCase.executeAsync(input).join();

        Resposta<OutputProcessamento> respostaProcessamento = _processarQuery.buscarStatusProcessamento(respostaCriacao.getDados()).join();

        // assert
        assertNotNull(respostaProcessamento);
        assertTrue(respostaProcessamento.Sucedido());
        assertEquals("Operação realizada", respostaProcessamento.getMensagem());
        assertEquals(respostaCriacao.getDados(), respostaProcessamento.getDados().requisicaoId());
        assertEquals(StatusProcessamento.Sucesso, respostaProcessamento.getDados().statusProcessamento());
        assertTrue(respostaProcessamento.getDados().mensagem().contains("Matricula: "));
    }

//    @Test
//    public void NaoDeveCadastrarVendedorDocumentoJaCadastrado(){
//        //arrange
//        String nome = "Jonathan de Souza";
//        LocalDate dataNascimento = LocalDate.parse("1997-01-24");
//        String documento = "428.867.440-93";
//        String email = "john@gmail.com";
//        TipoDeContratacao tipoContratacao = TipoDeContratacao.Outsourcing;
//        int numeroFilial = 1;
//
//        //act
//        InputCriarVendedor input = new InputCriarVendedor(nome, dataNascimento, documento, email,tipoContratacao, numeroFilial);
//        var PrimeiroInput = _criarVendedorUseCase.executeAsync(input).join();
//        var resultado = _criarVendedorUseCase.executeAsync(input).join();
//
//        //assert
//        assertFalse(resultado.Sucedido());
//        assertEquals("Documento já cadastrado para esse tipo de contratação", resultado.getMensagem());
//        assertNull(resultado.getDados());
//    }
//
//    @Test
//    public void NaoDeveCadastrarVendedorEmailInvalido(){
//        //arrange
//        String nome = "Jonathan de Souza";
//        LocalDate dataNascimento = LocalDate.parse("1997-01-24");
//        String documento = "282.112.900-90";
//        String email = "john_gmail.com";
//        TipoDeContratacao tipoContratacao = TipoDeContratacao.Outsourcing;
//        int numeroFilial = 1;
//
//        //act
//        InputCriarVendedor input = new InputCriarVendedor(nome, dataNascimento, documento, email,tipoContratacao, numeroFilial);
//        var resultado = _criarVendedorUseCase.executeAsync(input).join();
//
//        //assert
//        assertFalse(resultado.Sucedido());
//        assertEquals("Email inválido", resultado.getMensagem());
//        assertNull(resultado.getDados());
//    }
//
//    @Test
//    public void NaoDeveCadastrarVendedorFilialNaoCadastrada(){
//        //arrange
//        String nome = "Jonathan de Souza";
//        LocalDate dataNascimento = LocalDate.parse("1997-01-24");
//        String documento = "956.356.060-40";
//        String email = "john@gmail.com";
//        TipoDeContratacao tipoContratacao = TipoDeContratacao.Outsourcing;
//        int numeroFilial = 4;
//
//        //act
//        InputCriarVendedor input = new InputCriarVendedor(nome, dataNascimento, documento, email,tipoContratacao, numeroFilial);
//        var resultado = _criarVendedorUseCase.executeAsync(input).join();
//
//        //assert
//        assertFalse(resultado.Sucedido());
//        assertEquals("Filial não cadastrada", resultado.getMensagem());
//        assertNull(resultado.getDados());
//    }
}
