package br.com.grupocasasbahia.desafiobackend.vendedor.application.services;

import br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces.IProcessarQuery;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces.IProcessarQueryServices;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.outputs.OutputProcessamento;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.response.Resposta;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class ProcessarQueryServices implements IProcessarQueryServices {

    private final IProcessarQuery _query;

    public ProcessarQueryServices(IProcessarQuery query) {
        _query = query;
    }

    @Override
    public CompletableFuture<Resposta<OutputProcessamento>> buscarPorRequisicaoId(String requisicaoId) {

        Resposta<OutputProcessamento> buscar = _query.buscarStatusProcessamento(requisicaoId).join();

        if(!buscar.Sucedido())
            return CompletableFuture.completedFuture(Resposta.erro("Falha interna tente novamente"));

        if(buscar.getDados() == null)
            CompletableFuture.completedFuture(Resposta.successo(null));

        return CompletableFuture.completedFuture(buscar);
    }
}
