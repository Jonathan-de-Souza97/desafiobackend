package br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces;

import br.com.grupocasasbahia.desafiobackend.vendedor.application.response.Resposta;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface IProdutor {
    CompletableFuture<Resposta<String>> enviarMensagem(String topico, String requisicaoId, Object mensagem);
}
