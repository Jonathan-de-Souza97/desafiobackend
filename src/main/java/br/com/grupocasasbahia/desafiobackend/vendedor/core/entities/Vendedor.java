package br.com.grupocasasbahia.desafiobackend.vendedor.core.entities;

import br.com.grupocasasbahia.desafiobackend.vendedor.core.enums.TipoDeContratacao;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Pattern;

public class Vendedor {
    private String error;
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

    public Boolean vendedorEhValido(){
        if(!validaNome()){
            error = "Nome inválido";
            return false;
        }

        if(!validaEmail()){
            error = "Email inválido";
            return false;
        }

        if(!validaDataNascimento()){
            error = "Idade inválida, vendedor precisa ter 16 anos completos";
            return false;
        }

        return true;
    }

    private Boolean validaNome(){
        if(nome == null || nome.trim().isEmpty())
            return false;

        return Pattern.matches("^([A-Za-zÀ-ÿ]+\\s)+[A-Za-zÀ-ÿ]+$", nome);
    }

    private Boolean validaEmail(){
        if(email == null || email.trim().isEmpty())
            return false;

        return Pattern.matches("^(.+)\\@(.+)$", email);
    }

    private Boolean validaDataNascimento(){

        LocalDate dataFim = LocalDate.now();
        Integer idade = Period.between(dataDeNascimento, dataFim).getYears();

        if(idade < 16)
            return  false;

        return true;
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

    public String getError() {
        return error;
    }
}
