package br.com.grupocasasbahia.desafiobackend.vendedor.application.usecases;

import br.com.grupocasasbahia.desafiobackend.vendedor.application.inputs.InputCriarVendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces.ICriarVendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces.IFilialApi;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces.IVendedorRepository;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.response.Resposta;
import br.com.grupocasasbahia.desafiobackend.vendedor.core.entities.Filial;
import br.com.grupocasasbahia.desafiobackend.vendedor.core.entities.Vendedor;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

import static br.com.grupocasasbahia.desafiobackend.vendedor.application.response.Resposta.erro;
import static java.util.concurrent.CompletableFuture.completedFuture;

@Service
public class CriarVendedorUseCase implements ICriarVendedor {

    private final IVendedorRepository _repository;
    private final IFilialApi _filialApi;

    public CriarVendedorUseCase(IVendedorRepository repository, IFilialApi filialApi) {
        _repository = repository;
        _filialApi = filialApi;
    }

    @Override
    public CompletableFuture<Resposta<String>> executeAsync(InputCriarVendedor input) {
        Vendedor vendedor = input.toEntity();
        if(!vendedor.valido())
            return completedFuture(erro(vendedor.getError()));

        Resposta<Integer> buscaNovaMatricula = _repository.buscarNovaMatricula().join();

        if(!buscaNovaMatricula.Sucedido())
            return completedFuture(Resposta.erro("Falha interna, tente novamente"));

        vendedor.setMatricula(buscaNovaMatricula.getDados(), vendedor.getTipoDeContratacao());

        Resposta<String> verificarSeDocumentoJaExiste = _repository.buscarPorDocumentoETipoDeContratacao(vendedor.getDocumento(), vendedor.getTipoDeContratacao()).join();

        if(!verificarSeDocumentoJaExiste.Sucedido())
            return completedFuture(Resposta.erro("Falha interna, tente novamente"));

        if(verificarSeDocumentoJaExiste.getDados() != null)
            return completedFuture(Resposta.erro("Documento já cadastrado para esse tipo de contratação"));

        Resposta<Filial> buscarFilial = _filialApi.buscarPorID(vendedor.getnumeroFilial()).join();

        if(!buscarFilial.Sucedido())
            return completedFuture(Resposta.erro("Falha interna, tente novamente"));

        if(buscarFilial.getDados() == null)
            return completedFuture(Resposta.erro("Filial não cadastrada"));


        Resposta<String> salvarVendedor = _repository.salvar(vendedor).join();

        if(!salvarVendedor.Sucedido())
            return completedFuture(Resposta.erro("Falha interna tente novamente"));



        return completedFuture(Resposta.successo(salvarVendedor.getDados()));
    }
}
