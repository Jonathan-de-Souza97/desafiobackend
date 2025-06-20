package br.com.grupocasasbahia.desafiobackend.vendedor.unit;

import br.com.grupocasasbahia.desafiobackend.vendedor.core.entities.Vendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.core.enums.TipoDeContratacao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

public class VendedorTest {

    @Test
    public void DeveCriarUmVendedorValidoComCPF(){

        //arrange
        String matricula ="2345211-CLT";
        String nome = "Jonathan de Souza";
        LocalDate dataNascimento = LocalDate.parse("1997-01-24");
        String cpf = "42184073806";
        String email = "john@gmail.com";

        Vendedor vendedor = new Vendedor(matricula,nome,dataNascimento,cpf,email, TipoDeContratacao.CLT,1);

        assertTrue(vendedor.vendedorEhValido());
        assertNotNull(vendedor.getId());
        assertEquals(matricula, vendedor.getMatricula());
        assertEquals(nome, vendedor.getNome());
        assertEquals(dataNascimento, vendedor.getDataDeNascimento());
        assertEquals(cpf, vendedor.getDocumento());
        assertEquals(email, vendedor.getEmail());
    }

    @Test
    public void DeveCriarUmVendedorValidoComCNPJ(){

        //arrange
        String matricula ="2345211-CLT";
        String nome = "Jonathan de Souza";
        LocalDate dataNascimento = LocalDate.parse("1997-01-24");
        String cnpj = "33041260094711";
        String email = "john@gmail.com";

        Vendedor vendedor = new Vendedor(matricula,nome,dataNascimento,cnpj,email, TipoDeContratacao.CLT,1);

        assertTrue(vendedor.vendedorEhValido());
        assertNotNull(vendedor.getId());
        assertEquals(matricula, vendedor.getMatricula());
        assertEquals(nome, vendedor.getNome());
        assertEquals(dataNascimento, vendedor.getDataDeNascimento());
        assertEquals(cnpj, vendedor.getDocumento());
        assertEquals(email, vendedor.getEmail());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "Jonathan", "Jg22", "as@"})
    public void NaoDeveCriarUmVendedorNomeInvalido(String nome){

        //arrange
        String matricula ="2345211-CLT";
        LocalDate dataNascimento = LocalDate.parse("1997-01-24");
        String cpf = "42184073806";
        String email = "john@gmail.com";

        Vendedor vendedor = new Vendedor(matricula,nome,dataNascimento,cpf,email, TipoDeContratacao.CLT,1);

        assertFalse(vendedor.vendedorEhValido());
        assertEquals("Nome inválido", vendedor.getError());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "Jonathan.com", ""})
    public void NaoDeveCriarUmVendedorEmailInvalido(String email){
        //arrange
        String matricula ="2345211-CLT";
        String nome = "Jonathan de Souza";
        LocalDate dataNascimento = LocalDate.parse("1997-01-24");
        String cpf = "42184073806";

        Vendedor vendedor = new Vendedor(matricula,nome,dataNascimento,cpf,email, TipoDeContratacao.CLT,1);

        assertFalse(vendedor.vendedorEhValido());
        assertEquals("Email inválido", vendedor.getError());
    }

    @Test
    public void NaoDeveCriarUmVendedorDataNascimentoIdadeMenorQue16Anos(){
        //arrange
        String matricula ="2345211-CLT";
        String nome = "Jonathan de Souza";
        LocalDate dataNascimento = LocalDate.now().plusDays(-5);
        String cpf = "42184073806";
        String email = "john@gmail.com";

        Vendedor vendedor = new Vendedor(matricula,nome,dataNascimento,cpf,email, TipoDeContratacao.CLT,1);

        assertFalse(vendedor.vendedorEhValido());
        assertEquals("Idade inválida, vendedor precisa ter 16 anos completos", vendedor.getError());
    }
}
