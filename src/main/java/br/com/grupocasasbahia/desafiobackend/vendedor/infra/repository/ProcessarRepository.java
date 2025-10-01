package br.com.grupocasasbahia.desafiobackend.vendedor.infra.repository;

import br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces.IProcessarRepository;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.response.Resposta;
import br.com.grupocasasbahia.desafiobackend.vendedor.core.enums.StatusProcessamento;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.CompletableFuture;

@Repository
public class ProcessarRepository implements IProcessarRepository {
    private final JdbcTemplate _conexao;

    public ProcessarRepository(JdbcTemplate _conexao) {
        this._conexao = _conexao;
    }

    @Override
    public CompletableFuture<Resposta<String>> salvarStatus(String requisicaoID, StatusProcessamento status, String mensagem) {
        String query = "INSERT INTO statusprocessamento " +
                       "(requisicaoid, status, mensagem) " +
                       "VALUES " +
                       "(?, ?, ?)";

        try {
            _conexao.update(query, new Object[]{requisicaoID, status.name(), mensagem});

            return CompletableFuture.completedFuture(Resposta.successo(requisicaoID));
        }
        catch (Exception ex){
            return CompletableFuture.completedFuture(Resposta.erro("Erro ao salvar processamento: " + ex.getMessage()));
        }
    }
}
