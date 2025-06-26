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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@SpringBootTest
public class BuscarVendedorPorMatriculaTest {

    @Autowired
    private IVendedorQueryServices _queryServices;
    @Autowired
    private ICriarVendedor _criarVendedorUseCase;
    @Test
    public void DeveRetornarVendedorDeAcordoComAMatricula(){
        //Arrange
        InputCriarVendedor input1 = new InputCriarVendedor("Jonathan de Souza", LocalDate.parse("1964-01-24"), "58.824.924/0001-92", "john1@gmail.com",TipoDeContratacao.PJ, 1);

        //Act
        var act1 = _criarVendedorUseCase.executeAsync(input1).join();


        Resposta<OutputVendedor> vendedor = _queryServices.buscarVendedorPorMatriculaAsync(act1.getDados()).join();

        //Assert
        assertTrue(vendedor.Sucedido());
        assertEquals("Operação realizada", vendedor.getMensagem());
        assertEquals(act1.getDados(),  vendedor.getDados().matricula());
        assertEquals(input1.nome(), vendedor.getDados().nome());
        assertEquals(input1.email(), vendedor.getDados().email());
        assertEquals("58824924000192", vendedor.getDados().documento());
        assertEquals(input1.dataDeNascimento(), vendedor.getDados().dataDeNascimento());
        assertEquals(input1.numeroFilial(), vendedor.getDados().filial().getId());
        assertEquals("Berrini", vendedor.getDados().filial().getNome());
        assertEquals("66.235.624/0001-04", vendedor.getDados().filial().getCnpj());
        assertEquals("São Paulo", vendedor.getDados().filial().getCidade());
        assertEquals("SP", vendedor.getDados().filial().getUf());
        assertEquals("Rua", vendedor.getDados().filial().getTipo());
        assertTrue(vendedor.getDados().filial().getAtivo());
        assertEquals(LocalDateTime.of(2020,06,10, 10,20), vendedor.getDados().filial().getDataCadastro());
        assertEquals(LocalDateTime.of(2023,01,13, 12,53), vendedor.getDados().filial().getUltimaAtualizacao());

    }
}
