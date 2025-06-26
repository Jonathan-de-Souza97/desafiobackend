package br.com.grupocasasbahia.desafiobackend.vendedor.infra.drivers;

import br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces.IFilialApi;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.response.Resposta;
import br.com.grupocasasbahia.desafiobackend.vendedor.core.entities.Filial;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Component
public class FilialApiMock implements IFilialApi {


    private List<Filial> filiais = Arrays.asList(
            new Filial(1, "Berrini", "66.235.624/0001-04", "São Paulo", "SP", "Rua", true, LocalDateTime.of(2020,06,10, 10,20), LocalDateTime.of(2023,01,13, 12,53)),
            new Filial(2, "Mega Loja", "30.126.163/0001-86","São Paulo", "SP", "Rua", true, LocalDateTime.of(2017,07,13, 04,15), LocalDateTime.of(2024,03,23, 14,41)),
            new Filial(3, "Jundiaí Shopping", "76.469.258/0001-01", "Jundiaí", "SP", "Shopping", true, LocalDateTime.of(2010,01,14, 14, 34), LocalDateTime.of(2025,06,07, 18, 27))
    );

    @Override
    public CompletableFuture<Resposta<Filial>> buscarPorID(int id) {
        Optional<Filial> filial = filiais.stream()
                .filter(f -> f.getId().equals(id))
                .findFirst();

        try{
            if (filial.isPresent()) {
                return CompletableFuture.completedFuture(Resposta.successo(filial.get()));
            } else {
                return CompletableFuture.completedFuture(Resposta.successo(null));
            }
        }
        catch (Exception ex){
            return CompletableFuture.completedFuture(Resposta.erro("Falha ao buscar filial: " + ex));
        }

    }
}
