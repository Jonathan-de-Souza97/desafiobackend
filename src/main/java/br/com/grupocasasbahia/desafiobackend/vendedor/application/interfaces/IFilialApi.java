package br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces;

import br.com.grupocasasbahia.desafiobackend.vendedor.application.response.Resposta;
import br.com.grupocasasbahia.desafiobackend.vendedor.core.entities.Filial;

import java.util.concurrent.CompletableFuture;

public interface IFilialApi {
    CompletableFuture<Resposta<Filial>> buscarPorID(int id);
}
