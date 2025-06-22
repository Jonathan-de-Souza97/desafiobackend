package br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces;

import br.com.grupocasasbahia.desafiobackend.vendedor.application.inputs.InputCriarVendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.outputs.OutputCriarUsuario;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.response.Resposta;

import java.util.concurrent.CompletableFuture;

public interface ICriarVendedor {
    CompletableFuture<Resposta<OutputCriarUsuario>> executeAsync(InputCriarVendedor input);
}
