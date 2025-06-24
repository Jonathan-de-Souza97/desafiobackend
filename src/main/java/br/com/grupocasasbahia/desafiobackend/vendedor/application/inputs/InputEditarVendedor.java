package br.com.grupocasasbahia.desafiobackend.vendedor.application.inputs;

import br.com.grupocasasbahia.desafiobackend.vendedor.core.entities.Vendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.core.enums.TipoDeContratacao;

import java.time.LocalDate;

public record InputEditarVendedor(String matricula, String nome, LocalDate dataDeNascimento, String documento, String email, TipoDeContratacao tipoDeContratacao, int numeroFilial) {
    public Vendedor toEntity(){
        return new Vendedor(matricula, nome, dataDeNascimento, documento, email,tipoDeContratacao, numeroFilial);
    }
}
