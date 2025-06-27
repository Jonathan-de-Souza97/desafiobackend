package br.com.grupocasasbahia.desafiobackend.vendedor.application.inputs;

import br.com.grupocasasbahia.desafiobackend.vendedor.core.entities.Vendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.core.enums.TipoDeContratacao;

import java.time.LocalDate;

public class InputCriarVendedor {
    private String nome;
    private LocalDate dataDeNascimento;
    private String documento;
    private String email;
    private TipoDeContratacao tipoDeContratacao;
    private Integer numeroFilial;

    public InputCriarVendedor() {
    }

    public InputCriarVendedor(String nome, LocalDate dataDeNascimento, String documento, String email, TipoDeContratacao tipoDeContratacao, Integer numeroFilial) {
        this.nome = nome;
        this.dataDeNascimento = dataDeNascimento;
        this.documento = documento;
        this.email = email;
        this.tipoDeContratacao = tipoDeContratacao;
        this.numeroFilial = numeroFilial;
    }

    public Vendedor toEntity(){
        return new Vendedor(
                nome,
                dataDeNascimento,
                documento,
                email,
                tipoDeContratacao,
                numeroFilial
        );
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getDataDeNascimento() {
        return dataDeNascimento;
    }

    public String getDocumento() {
        return documento;
    }

    public String getEmail() {
        return email;
    }

    public TipoDeContratacao getTipoDeContratacao() {
        return tipoDeContratacao;
    }

    public Integer getNumeroFilial() {
        return numeroFilial;
    }
}
