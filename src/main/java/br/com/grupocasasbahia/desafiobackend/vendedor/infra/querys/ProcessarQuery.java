package br.com.grupocasasbahia.desafiobackend.vendedor.infra.querys;

import br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces.IProcessarQuery;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.outputs.OutputProcessamento;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.response.Resposta;
import br.com.grupocasasbahia.desafiobackend.vendedor.core.enums.StatusProcessamento;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class ProcessarQuery implements IProcessarQuery {
    private final JdbcTemplate _conexao;

    public ProcessarQuery(JdbcTemplate conexao) {
        _conexao = conexao;
    }

    @Override
    public CompletableFuture<Resposta<OutputProcessamento>> buscarStatusProcessamento(String requisicaoId) {
        String query = "SELECT requisicaoid, status, mensagem FROM statusprocessamento WHERE requisicaoid = ?";

        try{
            List<OutputProcessamento> output = _conexao.query(query,
                    new Object[]{requisicaoId}, (rs,rowNum) ->
                    new OutputProcessamento(
                            rs.getString("requisicaoid"),
                            StatusProcessamento.valueOf(rs.getString("status")),
                            rs.getString("mensagem"
                            )
                    )
            );

            if(output.isEmpty())
                return CompletableFuture.completedFuture(Resposta.successo(null));


            return CompletableFuture.completedFuture(Resposta.successo(output.get(0)));
        }
        catch (Exception ex){
            return CompletableFuture.completedFuture(Resposta.erro("Falha ao buscar status de processamento: " + ex.getMessage()));
        }

    }
}
