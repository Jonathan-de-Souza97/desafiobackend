package br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces;

import br.com.grupocasasbahia.desafiobackend.vendedor.application.response.Resposta;
import br.com.grupocasasbahia.desafiobackend.vendedor.core.enums.StatusProcessamento;

import java.util.concurrent.CompletableFuture;

public interface IProcessarRepository {
    CompletableFuture<Resposta<String>> salvarStatus(String requisicaoID, StatusProcessamento status, String mensagem);
}
