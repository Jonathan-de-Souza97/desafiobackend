package br.com.grupocasasbahia.desafiobackend.vendedor.application.usecases;

import br.com.grupocasasbahia.desafiobackend.vendedor.application.inputs.InputEditarVendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces.IEditarVendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces.IFilialApi;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces.IVendedorRepository;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.response.Resposta;
import br.com.grupocasasbahia.desafiobackend.vendedor.core.entities.Filial;
import br.com.grupocasasbahia.desafiobackend.vendedor.core.entities.Vendedor;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.CompletableFuture.completedFuture;

@Service
public class EditarVendedorUseCase implements IEditarVendedor {

    private final IVendedorRepository _repository;
    private final IFilialApi _filialApi;

    public EditarVendedorUseCase(IVendedorRepository repository, IFilialApi filialApi) {
        _repository = repository;
        _filialApi = filialApi;
    }

    @Override
    public CompletableFuture<Resposta<String>> executeAsync(InputEditarVendedor input) {
        Vendedor vendedor = input.toEntity();

        if(!vendedor.valido())
            return CompletableFuture.completedFuture(Resposta.erro(vendedor.getError()));

        int numeroMatricula = Integer.parseInt(input.matricula().substring(0,8));

        vendedor.setMatricula(numeroMatricula, vendedor.getTipoDeContratacao());

        Resposta<String> verificarSeVendedorExiste = _repository.buscarPorMatricula(input.matricula()).join();

        if(!verificarSeVendedorExiste.Sucedido())
            return CompletableFuture.completedFuture(Resposta.erro("Falha interna tente novamente"));

        if(verificarSeVendedorExiste.getDados() == null)
            return CompletableFuture.completedFuture(Resposta.erro("Vendedor não cadastrado"));

        Resposta<String> verificarSeDocumentoJaEstaCadastrado = _repository.buscarPorDocumentoETipoDeContratacao(vendedor.getDocumento(), vendedor.getTipoDeContratacao()).join();

        if(!verificarSeDocumentoJaEstaCadastrado.Sucedido())
            return CompletableFuture.completedFuture(Resposta.erro("Falha interna tente novamente"));

        if(verificarSeDocumentoJaEstaCadastrado.getDados() != null)
            return CompletableFuture.completedFuture(Resposta.erro("Documento já cadastrado para esse tipo de contratação"));

        Resposta<Filial> buscarFilial = _filialApi.buscarPorID(vendedor.getnumeroFilial()).join();

        if(!buscarFilial.Sucedido())
            return completedFuture(Resposta.erro("Falha interna, tente novamente"));

        if(buscarFilial.getDados() == null)
            return completedFuture(Resposta.erro("Filial não cadastrada"));


        Resposta<String> resultado = _repository.editar(input.matricula(),vendedor).join();

        if(!resultado.Sucedido())
            return CompletableFuture.completedFuture(Resposta.erro("Falha interna tente novamente"));

        return CompletableFuture.completedFuture(resultado);
    }
}
