package br.com.grupocasasbahia.desafiobackend.vendedor.integration;

import br.com.grupocasasbahia.desafiobackend.vendedor.application.inputs.InputCriarVendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces.ICriarVendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces.IProcessarQueryServices;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces.IVendedorQueryServices;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.outputs.OutputProcessamento;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.outputs.OutputVendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.response.Resposta;
import br.com.grupocasasbahia.desafiobackend.vendedor.core.enums.TipoDeContratacao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;


import static org.junit.jupiter.api.Assertions.*;
@ActiveProfiles("test")
@EmbeddedKafka(partitions = 1, topics = {"criar-vendedor"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CriarVendedorTest {

    @Autowired
    private ICriarVendedor criarVendedorUseCase;

    @Autowired
    private IProcessarQueryServices processarQueryServices;

    @Autowired
    private IVendedorQueryServices vendedorService;

    @Test
    void deveCriarVendedorComKafkaEBDReal() throws Exception {
        // Arrange
        String nome = "Jonathan de Souza";
        LocalDate dataNascimento = LocalDate.parse("1997-01-24");
        String documento = "542.983.940-00";
        String email = "john@gmail.com";
        TipoDeContratacao tipoContratacao = TipoDeContratacao.CLT;
        int numeroFilial = 1;

        InputCriarVendedor input = new InputCriarVendedor(
                nome,
                dataNascimento,
                documento,
                email,
                tipoContratacao,
                numeroFilial
        );

        // Act: envia a mensagem para Kafka
        CompletableFuture<Resposta<String>> resultadoFuture = criarVendedorUseCase.executeAsync(input);
        Resposta<String> resultado = resultadoFuture.get(10, TimeUnit.SECONDS);

        // Assert: resposta imediata do use case
        assertNotNull(resultado);
        assertTrue(resultado.Sucedido());
        assertEquals("Solicitação em processamento", resultado.getMensagem());
        assertNotNull(resultado.getDados()); // requisicaoId

        String requisicaoId = resultado.getDados();

        // Polling: espera o consumidor processar a mensagem e atualizar o status
        Resposta<OutputProcessamento> processo = null;
        int tentativas = 0;
        int maxTentativas = 30; // espera até 5s (10 * 500ms)
        while (tentativas < maxTentativas) {
            processo = processarQueryServices.buscarPorRequisicaoId(requisicaoId).join();
            if (processo != null && processo.getDados() != null) {
                break;
            }
            Thread.sleep(500);
            tentativas++;
        }

        // Assert final: verifica se o consumidor processou a mensagem
        assertTrue(processo.Sucedido(), "Processo falhou");
        assertNotNull(processo.getDados(), "Dados do processo null");
        assertEquals(requisicaoId, processo.getDados().requisicaoId(), "RequisicaoId não bate");


        String matricula = processo.getDados().mensagem().replace("Matricula: ", "");
        Resposta<OutputVendedor>  outputVendedor = vendedorService.buscarVendedorPorMatriculaAsync(matricula).join();

        assertEquals(input.getNome(), outputVendedor.getDados().nome());
        assertEquals(input.getDataDeNascimento(), outputVendedor.getDados().dataDeNascimento());
        assertEquals(input.getDocumento().replace(".", "").replace("-",""), outputVendedor.getDados().documento());
        assertEquals(input.getTipoDeContratacao(), outputVendedor.getDados().tipoDeContratacao());
        assertEquals(input.getEmail(), outputVendedor.getDados().email());
        assertEquals(input.getNumeroFilial(), outputVendedor.getDados().filial().getId());

    }

}
