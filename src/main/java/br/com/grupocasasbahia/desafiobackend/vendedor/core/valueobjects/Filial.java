package br.com.grupocasasbahia.desafiobackend.vendedor.core.valueobjects;

import java.sql.Timestamp;
import java.util.UUID;

public class Filial {
    private UUID id;
    private String nome;
    private String cnpj;
    private String cidade;
    private String uf;
    private String tipo;
    private Boolean ativo;
    private Timestamp dataCadastro;
    private Timestamp ultimaAtualizacao;
}
