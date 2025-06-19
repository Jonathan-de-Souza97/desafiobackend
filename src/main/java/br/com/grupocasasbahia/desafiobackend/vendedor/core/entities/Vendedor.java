package br.com.grupocasasbahia.desafiobackend.vendedor.core.entities;

import br.com.grupocasasbahia.desafiobackend.vendedor.core.enums.TipoDeContratacao;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class Vendedor {
    private UUID id;
    private String matricula;
    private String nome;
    private LocalDate dataDeNascimento;
    private String documento;
    private String email;
    private TipoDeContratacao tipoDeContratacao;
    private Integer idFilial;

    public Vendedor(String matricula, String nome, LocalDate dataDeNascimento, String documento, String email, TipoDeContratacao tipoDeContratacao, Integer idFilial) {
        this.id = UUID.randomUUID();
        this.matricula = matricula;
        this.nome = nome;
        this.dataDeNascimento = dataDeNascimento;
        this.documento = documento;
        this.email = email;
        this.tipoDeContratacao = tipoDeContratacao;
        this.idFilial = idFilial;
    }

    public UUID getId() {
        return id;
    }

    public String getMatricula() {
        return matricula;
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

    public Integer getIdFilial() {
        return idFilial;
    }
}
