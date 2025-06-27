package br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces;

import br.com.grupocasasbahia.desafiobackend.vendedor.application.response.Resposta;
import br.com.grupocasasbahia.desafiobackend.vendedor.core.entities.Vendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.core.enums.TipoDeContratacao;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IVendedorRepository {

    CompletableFuture<Resposta<String>>buscarPorMatricula(String matricula);
    CompletableFuture<Resposta<String>> buscarPorDocumentoETipoDeContratacao(String documento, TipoDeContratacao tipoDeContratacao);
    CompletableFuture<Resposta<Integer>>buscarNovaMatricula();
    CompletableFuture<Resposta<String>>salvar(Vendedor vendedor);
    CompletableFuture<Resposta<String>>editar(String matriculaAEditar,Vendedor vendedor);
    CompletableFuture<Resposta<String>>apagar(String matricula);


}
