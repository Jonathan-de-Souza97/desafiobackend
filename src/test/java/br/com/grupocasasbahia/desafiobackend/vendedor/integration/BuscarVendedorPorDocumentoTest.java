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
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
public class BuscarVendedorPorDocumentoTest {

    @Autowired
    private IVendedorQueryServices _queryServices;
    @Autowired
    private ICriarVendedor _criarVendedorUseCase;
    @Test
    public void DeveRetornarVendedorDeAcordoComODocumento(){
        //Arrange
        InputCriarVendedor input1 = new InputCriarVendedor("Jonathan de Souza", LocalDate.parse("1964-01-24"), "658.108.500-65", "john1@gmail.com",TipoDeContratacao.CLT, 1);

        //Act
        _criarVendedorUseCase.executeAsync(input1).join();


        Resposta<List<OutputVendedor>> vendedor = _queryServices.buscarVendedorPorDocumentoAsync(input1.documento()).join();

        //Assert
        assertTrue(vendedor.Sucedido());
        assertEquals("Operação realizada", vendedor.getMensagem());
        assertNotNull(vendedor.getDados().size() == 1);
        assertEquals(input1.nome(), vendedor.getDados().get(0).nome());
        assertEquals(input1.email(), vendedor.getDados().get(0).email());
        assertEquals("65810850065", vendedor.getDados().get(0).documento());
        assertEquals(input1.dataDeNascimento(), vendedor.getDados().get(0).dataDeNascimento());
        assertEquals(input1.numeroFilial(), vendedor.getDados().get(0).filial().getId());
        assertEquals("Berrini", vendedor.getDados().get(0).filial().getNome());
        assertEquals("66.235.624/0001-04", vendedor.getDados().get(0).filial().getCnpj());
        assertEquals("São Paulo", vendedor.getDados().get(0).filial().getCidade());
        assertEquals("SP", vendedor.getDados().get(0).filial().getUf());
        assertEquals("Rua", vendedor.getDados().get(0).filial().getTipo());
        assertTrue(vendedor.getDados().get(0).filial().getAtivo());
        assertEquals(LocalDateTime.of(2020,06,10, 10,20), vendedor.getDados().get(0).filial().getDataCadastro());
        assertEquals(LocalDateTime.of(2023,01,13, 12,53), vendedor.getDados().get(0).filial().getUltimaAtualizacao());

    }
}
