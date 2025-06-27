package br.com.grupocasasbahia.desafiobackend.vendedor.core.entities;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

public class Filial {
    private Integer id;
    private String nome;
    private String cnpj;
    private String cidade;
    private String uf;
    private String tipo;
    private Boolean ativo;
    private LocalDateTime dataCadastro;
    private LocalDateTime ultimaAtualizacao;

    public Filial(Integer id, String nome, String cnpj, String cidade, String uf, String tipo, Boolean ativo, LocalDateTime dataCadastro, LocalDateTime ultimaAtualizacao) {
        this.id = id;
        this.nome = nome;
        this.cnpj = cnpj;
        this.cidade = cidade;
        this.uf = uf;
        this.tipo = tipo;
        this.ativo = ativo;
        this.dataCadastro = dataCadastro;
        this.ultimaAtualizacao = ultimaAtualizacao;
    }


    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getCidade() {
        return cidade;
    }

    public String getUf() {
        return uf;
    }

    public String getTipo() {
        return tipo;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public LocalDateTime getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }
}
