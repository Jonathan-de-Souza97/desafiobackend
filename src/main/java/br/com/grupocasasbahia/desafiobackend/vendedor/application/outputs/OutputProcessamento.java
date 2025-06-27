package br.com.grupocasasbahia.desafiobackend.vendedor.application.outputs;

import br.com.grupocasasbahia.desafiobackend.vendedor.core.enums.StatusProcessamento;

public record OutputProcessamento (String requisicaoId, StatusProcessamento statusProcessamento, String mensagem) {
}
