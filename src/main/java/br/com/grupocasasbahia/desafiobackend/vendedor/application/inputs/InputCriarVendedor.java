package br.com.grupocasasbahia.desafiobackend.vendedor.application.inputs;

import br.com.grupocasasbahia.desafiobackend.vendedor.core.entities.Vendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.core.enums.TipoDeContratacao;

import java.time.LocalDate;

public record InputCriarVendedor (
        String nome,
        LocalDate dataDeNascimento,
        String documento,
        String email,
        TipoDeContratacao tipoDeContratacao,
        Integer numeroFilial){

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
}
