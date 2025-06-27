package br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces;

import java.util.UUID;

public interface IProdutor {
    void enviarMensagem(String topico, String requisicaoId, Object mensagem);
}
