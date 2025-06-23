package br.com.grupocasasbahia.desafiobackend.vendedor.application.usecases;

import br.com.grupocasasbahia.desafiobackend.vendedor.application.inputs.InputCriarVendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces.ICriarVendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces.IVendedorRepository;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.outputs.OutputCriarUsuario;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.response.Resposta;
import br.com.grupocasasbahia.desafiobackend.vendedor.core.entities.Vendedor;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

import static br.com.grupocasasbahia.desafiobackend.vendedor.application.response.Resposta.erro;
import static java.util.concurrent.CompletableFuture.completedFuture;

@Service
public class CriarVendedorUseCase implements ICriarVendedor {

    private final IVendedorRepository _repository;

    public CriarVendedorUseCase(IVendedorRepository repository) {
        _repository = repository;
    }

    @Override
    public CompletableFuture<Resposta<OutputCriarUsuario>> executeAsync(InputCriarVendedor input) {
        Vendedor vendedor = input.toEntity();
        if(!vendedor.ehValido())
            return completedFuture(erro(vendedor.getError()));

        Resposta<Integer> buscaNovaMatricula = _repository.buscarNovaMatricula().join();

        if(!buscaNovaMatricula.Sucedido())
            return completedFuture(Resposta.erro("Falha interna, tente novamente"));

        vendedor.setMatricula(buscaNovaMatricula.getDados(), vendedor.getTipoDeContratacao());

        Resposta<Vendedor> verificarSeDocumentoJaExiste = _repository.buscarPorDocumento(input.documento()).join();

        if(!verificarSeDocumentoJaExiste.Sucedido())
            return completedFuture(Resposta.erro("Falha interna, tente novamente"));

        if(verificarSeDocumentoJaExiste.getDados() != null)
            return completedFuture(Resposta.erro("Documento já cadastrado"));

        Resposta<String> salvarVendedor = _repository.salvar(vendedor).join();

        if(!salvarVendedor.Sucedido())
            return completedFuture(Resposta.erro("Falha interna tente novamente"));

        OutputCriarUsuario output = new OutputCriarUsuario(salvarVendedor.getDados());
        return completedFuture(Resposta.successo(output));
    }
}
