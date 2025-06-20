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

        Vendedor vendedor = new Vendedor(matricula,nome,dataNascimento,cnpj,email, TipoDeContratacao.PJ,1);

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

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    public void NaoDeveCriarUmVendedorDocumentoVazio(String cpf){
        //arrange
        String matricula ="2345211-CLT";
        String nome = "Jonathan de Souza";
        String email = "john@gmail.com";
        LocalDate dataNascimento = LocalDate.parse("1997-01-24");

        Vendedor vendedor = new Vendedor(matricula,nome,dataNascimento,cpf,email, TipoDeContratacao.CLT,1);

        assertFalse(vendedor.vendedorEhValido());
        assertEquals("Documento é obrigatório", vendedor.getError());
    }

    @Test
    public void NaoDeveCriarUmVendedorDocumentoComTodosDigitosIguais(){
        //arrange
        String matricula ="2345211-CLT";
        String nome = "Jonathan de Souza";
        String email = "john@gmail.com";
        String cpf = "11111111111";
        LocalDate dataNascimento = LocalDate.parse("1997-01-24");

        Vendedor vendedor = new Vendedor(matricula,nome,dataNascimento,cpf,email, TipoDeContratacao.CLT,1);

        assertFalse(vendedor.vendedorEhValido());
        assertEquals("Documento inválido, todos dígitos são iguais", vendedor.getError());
    }

    @ParameterizedTest
    @ValueSource(strings = { "123", "71428793806", "87748248810", "877.482.488-70", "877.482.48850", "877.48248888"})
    public void NaoDeveCriarUmVendedorCPFInvalido(String cpf){
        //arrange
        String matricula ="2345211-CLT";
        String nome = "Jonathan de Souza";
        String email = "john@gmail.com";
        LocalDate dataNascimento = LocalDate.parse("1997-01-24");

        Vendedor vendedor = new Vendedor(matricula,nome,dataNascimento,cpf,email, TipoDeContratacao.CLT,1);

        assertFalse(vendedor.vendedorEhValido());
        assertEquals("CPF inválido", vendedor.getError());
    }

    @ParameterizedTest
    @ValueSource(strings = { "45.723.174/0001-15", "03918103000153", "1232", "05.587.148/0001-05", "14.300.716/0001-78", "60742648000171"})
    public void NaoDeveCriarUmVendedorCNPJInvalido(String cnpj){
        //arrange
        String matricula ="2345211-CLT";
        String nome = "Jonathan de Souza";
        String email = "john@gmail.com";
        LocalDate dataNascimento = LocalDate.parse("1997-01-24");

        Vendedor vendedor = new Vendedor(matricula,nome,dataNascimento,cnpj,email, TipoDeContratacao.CLT,1);

        assertFalse(vendedor.vendedorEhValido());
        assertEquals("CPF inválido", vendedor.getError());
    }

    @Test
    public void NaoDeveCriarUmVendedorIDFilialIgualAZero(){

        //arrange
        String matricula ="2345211-CLT";
        String nome = "Jonathan de Souza";
        LocalDate dataNascimento = LocalDate.parse("1997-01-24");
        String cpf = "42184073806";
        String email = "john@gmail.com";

        Vendedor vendedor = new Vendedor(matricula,nome,dataNascimento,cpf,email, TipoDeContratacao.CLT,0);

        assertFalse(vendedor.vendedorEhValido());
        assertEquals("Id filial deve ser maior que 0", vendedor.getError());
    }
}
