package br.com.grupocasasbahia.desafiobackend.vendedor.application.usecases;

import br.com.grupocasasbahia.desafiobackend.vendedor.application.inputs.InputCriarVendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces.ICriarVendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces.IFilialApi;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces.IProdutor;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces.IVendedorRepository;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.response.Resposta;
import br.com.grupocasasbahia.desafiobackend.vendedor.core.entities.Filial;
import br.com.grupocasasbahia.desafiobackend.vendedor.core.entities.Vendedor;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.RecursiveTask;

import static br.com.grupocasasbahia.desafiobackend.vendedor.application.response.Resposta.erro;
import static java.util.concurrent.CompletableFuture.completedFuture;

@Service
public class CriarVendedorUseCase implements ICriarVendedor {

    private final IProdutor _produtor;

    public CriarVendedorUseCase(IProdutor produtor) {

        _produtor = produtor;
    }

    @Override
    public CompletableFuture<Resposta<String>> executeAsync(InputCriarVendedor input) {
        String requisicaoID = UUID.randomUUID().toString();

        _produtor.enviarMensagem("criar-vendedor",requisicaoID, input);

        Resposta<String> resposta = Resposta.successo("Solicitação em processamento", requisicaoID);

        return CompletableFuture.completedFuture(resposta);
    }
}
