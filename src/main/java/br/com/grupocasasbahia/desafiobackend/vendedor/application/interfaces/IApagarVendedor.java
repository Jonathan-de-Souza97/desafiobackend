package br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces;

import br.com.grupocasasbahia.desafiobackend.vendedor.application.response.Resposta;

import java.util.concurrent.CompletableFuture;

public interface IApagarVendedor {
    CompletableFuture<Resposta<String>>executeAsync(String matricula);
}
