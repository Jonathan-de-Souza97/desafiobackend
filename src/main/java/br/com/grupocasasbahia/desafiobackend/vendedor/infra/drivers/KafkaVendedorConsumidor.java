package br.com.grupocasasbahia.desafiobackend.vendedor.infra.drivers;

import br.com.grupocasasbahia.desafiobackend.vendedor.application.inputs.InputCriarVendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces.IFilialApi;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces.IProcessarRepository;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces.IVendedorConsumidor;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces.IVendedorRepository;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.response.Resposta;
import br.com.grupocasasbahia.desafiobackend.vendedor.core.entities.Filial;
import br.com.grupocasasbahia.desafiobackend.vendedor.core.entities.Vendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.core.enums.StatusProcessamento;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.kafka.support.KafkaHeaders;

@Component
public class KafkaVendedorConsumidor implements IVendedorConsumidor {

    private final IVendedorRepository _repositoryVendedor;
    private final IFilialApi _filialApi;
    private final IProcessarRepository _repositoryProcessamento;

    public KafkaVendedorConsumidor(IVendedorRepository repository, IFilialApi filialApi, IProcessarRepository repositoryProcessamento) {
        _repositoryVendedor = repository;
        _filialApi = filialApi;
        _repositoryProcessamento = repositoryProcessamento;
    }

    @KafkaListener(topics = "criar-vendedor",groupId = "grupo-vendedor")
    @Override
    public Resposta<String> processar(@Header("kafka_receivedMessageKey") String requisicaoID, @Payload InputCriarVendedor input) {

        Vendedor vendedor = input.toEntity();
        if(!vendedor.valido())
            return _repositoryProcessamento.salvarStatus(requisicaoID, StatusProcessamento.Erro, vendedor.getError()).join();

        Resposta<Integer> buscaNovaMatricula = _repositoryVendedor.buscarNovaMatricula().join();

        if(!buscaNovaMatricula.Sucedido())
            return Resposta.erro("Falha interna, tente novamente");

        vendedor.setMatricula(buscaNovaMatricula.getDados(), vendedor.getTipoDeContratacao());

        Resposta<String> verificarSeDocumentoJaExiste = _repositoryVendedor.buscarPorDocumentoETipoDeContratacao(vendedor.getDocumento(), vendedor.getTipoDeContratacao()).join();

        if(!verificarSeDocumentoJaExiste.Sucedido())
            return Resposta.erro("Falha interna, tente novamente");

        if(verificarSeDocumentoJaExiste.getDados() != null)
            return Resposta.erro("Documento já cadastrado para esse tipo de contratação");

        Resposta<Filial> buscarFilial = _filialApi.buscarPorID(vendedor.getnumeroFilial()).join();

        if(!buscarFilial.Sucedido())
            return Resposta.erro("Falha interna, tente novamente");

        if(buscarFilial.getDados() == null)
            return Resposta.erro("Filial não cadastrada");

        Resposta<String> salvarVendedor = _repositoryVendedor.salvar(vendedor).join();

        if(!salvarVendedor.Sucedido())
            return Resposta.erro("Falha interna, tente novamente");

       return  _repositoryProcessamento.salvarStatus(requisicaoID,StatusProcessamento.Sucesso,"Matricula: " + salvarVendedor.getDados()).join();
    }
}
