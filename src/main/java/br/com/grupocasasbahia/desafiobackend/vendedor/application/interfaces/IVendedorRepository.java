package br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces;

import br.com.grupocasasbahia.desafiobackend.vendedor.application.response.Resposta;
import br.com.grupocasasbahia.desafiobackend.vendedor.core.entities.Vendedor;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IVendedorRepository {

    CompletableFuture<Resposta<Vendedor>>buscarPorMatricula(String matricula);
    CompletableFuture<Resposta<Vendedor>>buscarPorDocumento(String documento);
    CompletableFuture<Resposta<List<Vendedor>>>buscarTodos();
    CompletableFuture<Resposta<Integer>>buscarNovaMatricula();
    CompletableFuture<Resposta<String>>salvar(Vendedor vendedor);
    CompletableFuture<Resposta<String>>editar(Vendedor vendedor);
    CompletableFuture<Resposta<String>>apagar(String matricula);


}
