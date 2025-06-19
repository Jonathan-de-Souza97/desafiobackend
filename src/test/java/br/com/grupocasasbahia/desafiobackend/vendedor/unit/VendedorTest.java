package br.com.grupocasasbahia.desafiobackend.vendedor.unit;

import br.com.grupocasasbahia.desafiobackend.vendedor.core.entities.Vendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.core.enums.TipoDeContratacao;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

public class VendedorTest {

    @Test
    public void DeveCriarUmVendedoValido(){

        //arrange
        String matricula ="2345211-CLT";
        String nome = "Jonathan de Souza";
        LocalDate dataNascimento = LocalDate.parse("1997-01-24");
        String cpf = "42184073806";
        String email = "john@gmail.com";

        Vendedor vendedor = new Vendedor(matricula,nome,dataNascimento,cpf,email, TipoDeContratacao.CLT,1);

        assertNotNull(vendedor.getId());
        assertEquals(matricula, vendedor.getMatricula());
        assertEquals(nome, vendedor.getNome());
        assertEquals(dataNascimento, vendedor.getDataDeNascimento());
        assertEquals(cpf, vendedor.getDocumento());
        assertEquals(email, vendedor.getEmail());
    }
}
