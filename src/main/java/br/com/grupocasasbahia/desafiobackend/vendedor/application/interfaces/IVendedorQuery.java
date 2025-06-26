package br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces;

import br.com.grupocasasbahia.desafiobackend.vendedor.application.response.Resposta;
import br.com.grupocasasbahia.desafiobackend.vendedor.core.entities.Vendedor;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IVendedorQuery {
    CompletableFuture<Resposta<Vendedor>> buscarPorMatricula(String matricula);
    CompletableFuture<Resposta<List<Vendedor>>> buscarPorDocumento(String documento);
    CompletableFuture<Resposta<List<Vendedor>>> buscarTodos();
}
