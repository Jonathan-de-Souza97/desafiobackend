package br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces;

import br.com.grupocasasbahia.desafiobackend.vendedor.application.inputs.InputCriarVendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.response.Resposta;

public interface IVendedorConsumidor {

    Resposta<String> processar(String requisicaoID, InputCriarVendedor input);
}
