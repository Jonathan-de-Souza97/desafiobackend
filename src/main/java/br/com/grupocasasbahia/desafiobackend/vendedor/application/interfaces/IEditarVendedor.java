package br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces;

import br.com.grupocasasbahia.desafiobackend.vendedor.application.inputs.InputEditarVendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.response.Resposta;

import java.util.concurrent.CompletableFuture;

public interface IEditarVendedor {
    CompletableFuture<Resposta<String>>executeAsync(InputEditarVendedor input);
}
