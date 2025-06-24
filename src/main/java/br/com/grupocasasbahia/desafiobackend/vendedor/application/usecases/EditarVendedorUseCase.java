package br.com.grupocasasbahia.desafiobackend.vendedor.application.usecases;

import br.com.grupocasasbahia.desafiobackend.vendedor.application.inputs.InputEditarVendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces.IEditarVendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces.IVendedorRepository;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.response.Resposta;
import br.com.grupocasasbahia.desafiobackend.vendedor.core.entities.Vendedor;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class EditarVendedorUseCase implements IEditarVendedor {

    private IVendedorRepository _repository;

    public EditarVendedorUseCase(IVendedorRepository _repository) {
        this._repository = _repository;
    }

    @Override
    public CompletableFuture<Resposta<String>> executeAsync(InputEditarVendedor input) {
        Vendedor vendedor = input.toEntity();

        if(!vendedor.valido())
            return CompletableFuture.completedFuture(Resposta.erro(vendedor.getError()));

        Resposta<Vendedor> verificarSeVendedorExiste = _repository.buscarPorMatricula(input.matricula()).join();

        if(!verificarSeVendedorExiste.Sucedido())
            return CompletableFuture.completedFuture(Resposta.erro("Falha interna tente novamente"));

        if(verificarSeVendedorExiste.getDados() == null)
            return CompletableFuture.completedFuture(Resposta.erro("Vendedor não cadastrado"));

        Resposta<Vendedor> verificarSeDocumentoJaEstaCadastrado = _repository.buscarPorDocumento(vendedor.getDocumento()).join();

        if(!verificarSeDocumentoJaEstaCadastrado.Sucedido())
            return CompletableFuture.completedFuture(Resposta.erro("Falha interna tente novamente"));

        if(verificarSeVendedorExiste.getDados() != null)
            return CompletableFuture.completedFuture(Resposta.erro("Documento já cadastrado"));

        Resposta<String> resultado = _repository.editar(vendedor).join();

        if(!resultado.Sucedido())
            return CompletableFuture.completedFuture(Resposta.erro("Falha interna tente novamente"));

        return CompletableFuture.completedFuture(resultado);
    }
}
