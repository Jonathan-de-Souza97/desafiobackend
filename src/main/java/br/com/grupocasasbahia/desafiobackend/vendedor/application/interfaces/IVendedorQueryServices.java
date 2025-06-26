package br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces;

import br.com.grupocasasbahia.desafiobackend.vendedor.application.outputs.OutputVendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.response.Resposta;
import br.com.grupocasasbahia.desafiobackend.vendedor.core.entities.Vendedor;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IVendedorQueryServices {
    CompletableFuture<Resposta<List<OutputVendedor>>> buscarTodosVendedoresAsync();

    CompletableFuture<Resposta<OutputVendedor>> buscarVendedorPorMatriculaAsync(String matricula);

    CompletableFuture<Resposta<List<OutputVendedor>>> buscarVendedorPorDocumentoAsync(String documento);
}
