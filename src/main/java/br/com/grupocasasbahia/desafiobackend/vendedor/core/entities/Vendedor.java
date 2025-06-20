package br.com.grupocasasbahia.desafiobackend.vendedor.core.entities;

import br.com.grupocasasbahia.desafiobackend.vendedor.core.enums.TipoDeContratacao;

import java.time.LocalDate;
import java.time.Period;
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

        if(!validaDocumentoEmBrancoOuVazio()){
            error = "Documento é obrigatório";
            return false;
        }

        if(!validaDocumentoTodosDigitosIguais(limparDocumento(documento))){
            error = "Documento inválido, todos dígitos são iguais";
            return false;
        }

        if(tipoDeContratacao != TipoDeContratacao.PJ && !validaCpf()){
            error = "CPF inválido";
            return false;
        }

        if(tipoDeContratacao == TipoDeContratacao.PJ && !validaCnpj()){
            error = "CNPJ inválido";
            return false;
        }

        if(!validaIdFilial()){
            error = "Id filial deve ser maior que 0";
            return false;
        }

        return true;
    }

    private Boolean validaNome(){
        if(nome == null || nome.trim().isEmpty())
            return false;

        return Pattern.matches("^([A-Za-zÀ-ÿ]+\\s)+[A-Za-zÀ-ÿ]+$", nome);
    }

    private Boolean validaDataNascimento(){

        LocalDate dataFim = LocalDate.now();
        Integer idade = Period.between(dataDeNascimento, dataFim).getYears();

        if(idade < 16)
            return  false;

        return true;
    }

    private Boolean validaDocumentoEmBrancoOuVazio(){
        Integer TamanhoValido = 11;

        if(documento == null || documento.trim().isEmpty())
            return false;

        return true;
    }

    private Boolean validaDocumentoTodosDigitosIguais(String documento){
        Character primeiroDigito = documento.charAt(0);
        Boolean todosOsDigitosSaoIguais = true;

        for(Character digito : documento.toCharArray()){
            if(digito != primeiroDigito)
                todosOsDigitosSaoIguais = false;
        }

        if(todosOsDigitosSaoIguais)
            return false;

        return true;
    }

    private static String limparDocumento(String documento){
        return documento.replaceAll("\\D", "");
    }

    private Boolean validaCpf(){

        Integer tamanhoCpfValido = 11;
        String cpf = limparDocumento(documento);
        Integer tamanhoCpf = cpf.length();

        if(tipoDeContratacao != TipoDeContratacao.PJ && tamanhoCpf != tamanhoCpfValido)
            return false;

        return aplicarRegrasDeDigitosCPF(cpf);
    }

    private Boolean aplicarRegrasDeDigitosCPF(String cpf){
        // Referencia https://www.macoratti.net/alg_cpf.htm#:~:text=O%20algoritmo%20de%20valida%C3%A7%C3%A3o%20do,%3A%20111.444.777%2D05.

        Integer primeiroDigito = calculaDigitoCPF(cpf,10);
        Integer segundoDigito = calculaDigitoCPF(cpf,11);
        String digitosCalculados = primeiroDigito.toString() + segundoDigito.toString();
        String digitosOriginais = cpf.substring(cpf.length() -2);

        if(!digitosCalculados.equals(digitosOriginais))
            return false;

        return true;
    }

    private Integer calculaDigitoCPF(String cpf, Integer fator){
        Integer total = 0;

        for (Character digito : cpf.toCharArray()){
            if (fator > 1)
                total += Integer.parseInt(digito.toString()) * fator --;
        }

        Integer resto = total % 11;

        return resto < 2 ? 0 : 11 - resto;
    }

    private Boolean validaCnpj(){

        Integer tamanhoCnpjValido = 14;
        String cnpj = limparDocumento(documento);
        Integer tamanhoCnpj = cnpj.length();

        if(tipoDeContratacao != TipoDeContratacao.PJ && tamanhoCnpj != tamanhoCnpjValido)
            return false;

        return aplicarRegrasDeDigitosCnpj(cnpj);
    }

    private Boolean aplicarRegrasDeDigitosCnpj(String cnpj){
        // https://www.macoratti.net/alg_cnpj.htm#:~:text=O%20n%C3%BAmero%20que%20comp%C3%B5e%20o,que%20s%C3%A3o%20os%20d%C3%ADgitos%20verificadores.

        Integer primeiroDigito = calculaDigitoCNPJ(cnpj,12);
        Integer segundoDigito = calculaDigitoCNPJ(cnpj,13);
        String digitosCalculados  = primeiroDigito.toString() + segundoDigito.toString();
        String digitosOriginais  = cnpj.substring(cnpj.length() -2);

        if(!digitosCalculados.equals(digitosOriginais))
            return false;

        return true;
    }

    private Integer calculaDigitoCNPJ(String cnpj, Integer posicao){
        int[] pesos;

        if(posicao == 12){
            pesos = new int[]{5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        }
        else if (posicao == 13){
            pesos = new int[]{6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        }
        else{
            throw new IllegalArgumentException("Posição inválida para cálculo do dígito do CNPJ");
        }

        int total = 0;
        for (int index = 0; index < pesos.length; index ++){
            total += Character.getNumericValue(cnpj.charAt(index)) * pesos[index];
        }

        int resto = total % 11;

        return resto < 2 ? 0 : 11 - resto;
    }

    private Boolean validaEmail(){
        if(email == null || email.trim().isEmpty())
            return false;

        return Pattern.matches("^(.+)\\@(.+)$", email);
    }

    private Boolean validaIdFilial(){

        return idFilial > 0;
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
