package br.com.grupocasasbahia.desafiobackend.vendedor.application.outputs;

import br.com.grupocasasbahia.desafiobackend.vendedor.core.entities.Filial;
import br.com.grupocasasbahia.desafiobackend.vendedor.core.enums.TipoDeContratacao;

import java.time.LocalDate;

public record OutputVendedor (String matricula, String nome, LocalDate dataDeNascimento, String documento, TipoDeContratacao tipoDeContratacao, String email, Filial filial){
}
