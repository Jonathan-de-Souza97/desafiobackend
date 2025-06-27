package br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces;

import br.com.grupocasasbahia.desafiobackend.vendedor.application.outputs.OutputProcessamento;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.response.Resposta;

import java.util.concurrent.CompletableFuture;

public interface IProcessarQueryServices {
    CompletableFuture<Resposta<OutputProcessamento>> buscarPorRequisicaoId(String requisicaoId);
}
