package br.com.grupocasasbahia.desafiobackend.vendedor.integration;

import br.com.grupocasasbahia.desafiobackend.vendedor.application.inputs.InputCriarVendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces.ICriarVendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces.IVendedorQueryServices;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces.IVendedorRepository;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.outputs.OutputVendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.response.Resposta;
import br.com.grupocasasbahia.desafiobackend.vendedor.core.entities.Vendedor;
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
    private IVendedorRepository _repository;
    @Test
    public void DeveRetornarTodosVendedoresCadastrados(){
        //Arrange
        Vendedor input1 = new Vendedor("Jonathan de Souza", LocalDate.parse("1964-01-24"), "22.921.015/0001-25", "john1@gmail.com",TipoDeContratacao.PJ, 1);
        Vendedor input2 = new Vendedor("Jonathan de Santos", LocalDate.parse("1978-01-24"), "418.291.230-68", "john2@gmail.com",TipoDeContratacao.CLT, 2);
        Vendedor input3 = new Vendedor("Jonathan de Silva", LocalDate.parse("2000-01-24"), "398.772.510-94", "john3@gmail.com",TipoDeContratacao.Outsourcing, 3);

        int numeroMatricula1 = 15454;
        int numeroMatricula2 = 15498;
        int numeroMatricula3 = 1543654;

        input1.setMatricula(numeroMatricula1, input1.getTipoDeContratacao());
        input2.setMatricula(numeroMatricula2, input2.getTipoDeContratacao());
        input3.setMatricula(numeroMatricula3, input3.getTipoDeContratacao());


        //Act
        var act1 = _repository.salvar(input1).join();
        var act2 =_repository.salvar(input2).join();
        var act3 =_repository.salvar(input3).join();

        Resposta<List<OutputVendedor>> vendedores = _queryServices.buscarTodosVendedoresAsync().join();

        //Assert
        assertTrue(vendedores.Sucedido());
        assertEquals("Operação realizada", vendedores.getMensagem());
        assertTrue(vendedores.getDados().size() > 0);

    }
}
