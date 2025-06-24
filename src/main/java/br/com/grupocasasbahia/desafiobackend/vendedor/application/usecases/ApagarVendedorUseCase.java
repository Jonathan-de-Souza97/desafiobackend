package br.com.grupocasasbahia.desafiobackend.vendedor.application.usecases;

import br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces.IApagarVendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces.IVendedorRepository;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.response.Resposta;
import br.com.grupocasasbahia.desafiobackend.vendedor.core.entities.Vendedor;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class ApagarVendedorUseCase implements IApagarVendedor {

    private final IVendedorRepository _repository;
    public ApagarVendedorUseCase(IVendedorRepository repository) {
        _repository = repository;
    }

    @Override
    public CompletableFuture<Resposta<String>> executeAsync(String matricula) {
        Resposta<Vendedor> verificarSeVendedorExiste = _repository.buscarPorMatricula(matricula).join();

        if(!verificarSeVendedorExiste.Sucedido())
            return CompletableFuture.completedFuture(Resposta.erro("Falha interna tente novamente"));

        if(verificarSeVendedorExiste.getDados() == null)
            return CompletableFuture.completedFuture(Resposta.erro("Vendedor não cadastrado"));

        Resposta<String> resultado = _repository.apagar(matricula).join();

        if(!resultado.Sucedido())
            return CompletableFuture.completedFuture(Resposta.erro("Falha interna tente novamente"));

        return CompletableFuture.completedFuture(resultado);
    }
}
