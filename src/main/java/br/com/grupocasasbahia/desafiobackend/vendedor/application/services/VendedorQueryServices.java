package br.com.grupocasasbahia.desafiobackend.vendedor.application.services;

import br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces.IFilialApi;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces.IVendedorQuery;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces.IVendedorQueryServices;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.outputs.OutputVendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.response.Resposta;
import br.com.grupocasasbahia.desafiobackend.vendedor.core.entities.Filial;
import br.com.grupocasasbahia.desafiobackend.vendedor.core.entities.Vendedor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class VendedorQueryServices implements IVendedorQueryServices {

    private final IVendedorQuery _query;
    private final IFilialApi _filialApi;

    public VendedorQueryServices(IVendedorQuery query, IFilialApi filialApi) {
        _query = query;
        _filialApi = filialApi;
    }


    @Override
    public CompletableFuture<Resposta<List<OutputVendedor>>> buscarTodosVendedoresAsync() {

        Resposta<List<Vendedor>> vendedores = _query.buscarTodos().join();

        List<OutputVendedor> outputVendedores = new ArrayList<>();

        if(!vendedores.Sucedido())
            CompletableFuture.completedFuture(Resposta.erro("Falha interna tente novamente"));

        if(vendedores.getDados().isEmpty())
            CompletableFuture.completedFuture(Resposta.successo(null));

        for(Vendedor vendedor : vendedores.getDados()){
            Resposta<Filial> buscarFilial = _filialApi.buscarPorID(vendedor.getnumeroFilial()).join();

            if(!buscarFilial.Sucedido())
                return CompletableFuture.completedFuture(Resposta.erro("Falha interna tente novamente"));

            OutputVendedor outputVendedor = new OutputVendedor(
                    vendedor.getMatricula(),
                    vendedor.getNome(),
                    vendedor.getDataDeNascimento(),
                    vendedor.getDocumento(),
                    vendedor.getTipoDeContratacao(),
                    vendedor.getEmail(),
                    buscarFilial.getDados());

            outputVendedores.add(outputVendedor);

        };

        return CompletableFuture.completedFuture(Resposta.successo(outputVendedores));
    }

    @Override
    public CompletableFuture<Resposta<OutputVendedor>> buscarVendedorPorMatriculaAsync(String matricula) {

        Resposta<Vendedor> buscaPorVendedor = _query.buscarPorMatricula(matricula).join();

        if(!buscaPorVendedor.Sucedido())
            return CompletableFuture.completedFuture(Resposta.erro("Falha interna tente novamente"));

        if(buscaPorVendedor.getDados() == null)
            return CompletableFuture.completedFuture(Resposta.successo(null));

            Resposta<Filial> buscarFilial = _filialApi.buscarPorID(buscaPorVendedor.getDados().getnumeroFilial()).join();

            if(!buscarFilial.Sucedido())
                return CompletableFuture.completedFuture(Resposta.erro("Falha interna tente novamente"));

            OutputVendedor outputVendedor = new OutputVendedor(
                    buscaPorVendedor.getDados().getMatricula(),
                    buscaPorVendedor.getDados().getNome(),
                    buscaPorVendedor.getDados().getDataDeNascimento(),
                    buscaPorVendedor.getDados().getDocumento(),
                    buscaPorVendedor.getDados().getTipoDeContratacao(),
                    buscaPorVendedor.getDados().getEmail(),
                    buscarFilial.getDados());

        return CompletableFuture.completedFuture(Resposta.successo(outputVendedor));

    }

    @Override
    public CompletableFuture<Resposta<List<OutputVendedor>>> buscarVendedorPorDocumentoAsync(String doc) {

        String documento = limparDocumento(doc);

        Resposta<List<Vendedor>> vendedores = _query.buscarPorDocumento(documento).join();
        List<OutputVendedor> outputVendedores = new ArrayList<>();

        if(!vendedores.Sucedido())
            CompletableFuture.completedFuture(Resposta.erro("Falha interna tente novamente"));

        if(vendedores.getDados().isEmpty())
            CompletableFuture.completedFuture(Resposta.successo(null));

        for(Vendedor vendedor : vendedores.getDados()){
            Resposta<Filial> buscarFilial = _filialApi.buscarPorID(vendedor.getnumeroFilial()).join();

            if(!buscarFilial.Sucedido())
                return CompletableFuture.completedFuture(Resposta.erro("Falha interna tente novamente"));

            OutputVendedor outputVendedor = new OutputVendedor(
                    vendedor.getMatricula(),
                    vendedor.getNome(),
                    vendedor.getDataDeNascimento(),
                    vendedor.getDocumento(),
                    vendedor.getTipoDeContratacao(),
                    vendedor.getEmail(),
                    buscarFilial.getDados());

            outputVendedores.add(outputVendedor);

        };

        return CompletableFuture.completedFuture(Resposta.successo(outputVendedores));
    }

    private String limparDocumento(String documento){
        return documento.replaceAll("\\D", "");
    }
}
