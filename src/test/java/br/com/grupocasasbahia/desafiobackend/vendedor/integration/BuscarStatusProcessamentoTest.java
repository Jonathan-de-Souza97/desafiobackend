package br.com.grupocasasbahia.desafiobackend.vendedor.integration;

import br.com.grupocasasbahia.desafiobackend.vendedor.application.outputs.OutputProcessamento;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.response.Resposta;
import br.com.grupocasasbahia.desafiobackend.vendedor.core.enums.StatusProcessamento;
import br.com.grupocasasbahia.desafiobackend.vendedor.infra.querys.ProcessarQuery;
import br.com.grupocasasbahia.desafiobackend.vendedor.infra.repository.ProcessarRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@SpringBootTest
public class BuscarStatusProcessamentoTest {

    @Autowired
    ProcessarRepository _repository;

    @Autowired
    ProcessarQuery _query;

    @Test
    public void DeveRetornarStatusProcessamentoSucesso(){
        //Arrange
        String requisicaoId = UUID.randomUUID().toString();
        StatusProcessamento status = StatusProcessamento.Sucesso;
        String mensagem = "Matricula: " + requisicaoId;

        //Act
        var act1 = _repository.salvarStatus(requisicaoId, status, mensagem).join();
        Resposta<OutputProcessamento> output = _query.buscarStatusProcessamento(requisicaoId).join();

        //Assert
        assertTrue(output.Sucedido());
        assertEquals("Operação realizada", output.getMensagem());
        assertEquals(mensagem,  output.getDados().mensagem());
        assertEquals(requisicaoId,  output.getDados().requisicaoId());
        assertEquals(status,  output.getDados().statusProcessamento());

    }

}
