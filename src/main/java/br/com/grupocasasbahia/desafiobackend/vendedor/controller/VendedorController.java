package br.com.grupocasasbahia.desafiobackend.vendedor.controller;

import br.com.grupocasasbahia.desafiobackend.vendedor.application.inputs.InputCriarVendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.inputs.InputEditarVendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces.IApagarVendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces.ICriarVendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces.IEditarVendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces.IVendedorQueryServices;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.outputs.OutputVendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.response.Resposta;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/vendedor")

public class VendedorController {
    private final ICriarVendedor _criarVendedorUseCase;
    private final IEditarVendedor _editarVendedorUseCase;
    private final IApagarVendedor _apagarVendedorUseCase;
    private final IVendedorQueryServices _queryServices;

    public VendedorController(ICriarVendedor useCaseCriarVendedor, IEditarVendedor editarVendedorUseCase, IApagarVendedor apagarVendedorUseCase, IVendedorQueryServices queryServices) {
        _criarVendedorUseCase = useCaseCriarVendedor;
        _editarVendedorUseCase = editarVendedorUseCase;
        _apagarVendedorUseCase = apagarVendedorUseCase;
        _queryServices = queryServices;
    }

    @PostMapping()
    public CompletableFuture<ResponseEntity<Resposta<String>>> criar(@RequestBody InputCriarVendedor input){
        return _criarVendedorUseCase.executeAsync(input)
                .thenApply(resposta -> {
                    if(!resposta.Sucedido() && resposta.getMensagem().equals("Falha interna tente novamente")){
                        return ResponseEntity.status(500).body(resposta);
                    }
                    else if(!resposta.Sucedido() && !resposta.getMensagem().equals("Falha interna tente novamente")){
                        return ResponseEntity.status(400).body(resposta);
                    }
                    else{
                        return ResponseEntity.status(200).body(resposta);
                    }
                });
    }

    @PutMapping()
    public CompletableFuture<ResponseEntity<Resposta<String>>> editar(@RequestBody InputEditarVendedor input){
        return _editarVendedorUseCase.executeAsync(input)
                .thenApply(resposta -> {
                    if(!resposta.Sucedido() && resposta.getMensagem().equals("Falha interna tente novamente")){
                        return ResponseEntity.status(500).body(resposta);
                    }
                    else if(!resposta.Sucedido() && !resposta.getMensagem().equals("Falha interna tente novamente")){
                        return ResponseEntity.status(400).body(resposta);
                    }
                    else{
                        return ResponseEntity.status(200).body(resposta);
                    }
                });
    }

    @DeleteMapping("/{matricula}")
    public CompletableFuture<ResponseEntity<Resposta<String>>> apagar(@PathVariable String matricula){
        return _apagarVendedorUseCase.executeAsync(matricula)
                .thenApply(resposta -> {
                    if(!resposta.Sucedido() && resposta.getMensagem().equals("Falha interna tente novamente")){
                        return ResponseEntity.status(500).body(resposta);
                    }
                    else if(!resposta.Sucedido() && !resposta.getMensagem().equals("Falha interna tente novamente")){
                        return ResponseEntity.status(400).body(resposta);
                    }
                    else{
                        return ResponseEntity.status(200).body(resposta);
                    }
                });
    }

    @GetMapping()
    public CompletableFuture<ResponseEntity<Resposta<List<OutputVendedor>>>> buscarTodos(){
        return _queryServices.buscarTodosVendedoresAsync()
                .thenApply(resposta -> {
                    if(!resposta.Sucedido() && resposta.getMensagem().equals("Falha interna tente novamente")){
                        return ResponseEntity.status(500).body(resposta);
                    }
                    else if(resposta.Sucedido() && resposta.getDados() == null){
                        return ResponseEntity.status(200).body(resposta);
                    }
                    else{
                        return ResponseEntity.status(200).body(resposta);
                    }
                });
    }

    @GetMapping("/matricula/{matricula}")
    public CompletableFuture<ResponseEntity<Resposta<OutputVendedor>>> buscarPorMatricula(@PathVariable String matricula){
        return _queryServices.buscarVendedorPorMatriculaAsync(matricula)
                .thenApply(resposta -> {
                    if(!resposta.Sucedido() && resposta.getMensagem().equals("Falha interna tente novamente"))
                        return ResponseEntity.status(500).body(resposta);

                    return ResponseEntity.status(200).body(resposta);
                });
    }

    @GetMapping("/documento/{documento}")
    public CompletableFuture<ResponseEntity<Resposta<List<OutputVendedor>>>> buscarPorDocumento(@PathVariable String documento){
        return _queryServices.buscarVendedorPorDocumentoAsync(documento)
                .thenApply(resposta -> {
                    if(!resposta.Sucedido() && resposta.getMensagem().equals("Falha interna tente novamente")){
                        return ResponseEntity.status(500).body(resposta);
                    }
                    else if(resposta.Sucedido() && resposta.getDados() == null){
                        return ResponseEntity.status(200).body(resposta);
                    }

                    return ResponseEntity.status(200).body(resposta);
                });
    }
}
