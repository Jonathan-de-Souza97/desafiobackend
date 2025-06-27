package br.com.grupocasasbahia.desafiobackend.vendedor.controller;

import br.com.grupocasasbahia.desafiobackend.vendedor.application.outputs.OutputProcessamento;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.outputs.OutputVendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.response.Resposta;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.services.ProcessarQueryServices;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/statusdoprocesso")
public class ProcessoStatusController {

    private final ProcessarQueryServices _queryService;

    public ProcessoStatusController(ProcessarQueryServices queryService) {
        _queryService = queryService;
    }

    @GetMapping("/{requisicaoId}")
    public CompletableFuture<ResponseEntity<Resposta<OutputProcessamento>>> buscarPorRequisicaoId(@PathVariable String requisicaoId){

        return _queryService.buscarPorRequisicaoId(requisicaoId)
                .thenApply(resposta -> {
                    if(!resposta.Sucedido() && resposta.getMensagem().equals("Falha interna tente novamente"))
                        return ResponseEntity.status(500).body(resposta);

                    return ResponseEntity.status(200).body(resposta);
                });
    }
}
