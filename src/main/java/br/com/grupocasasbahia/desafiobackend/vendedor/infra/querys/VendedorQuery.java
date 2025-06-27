package br.com.grupocasasbahia.desafiobackend.vendedor.infra.querys;

import br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces.IVendedorQuery;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.outputs.OutputVendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.response.Resposta;
import br.com.grupocasasbahia.desafiobackend.vendedor.core.entities.Vendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.core.enums.TipoDeContratacao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component

public class VendedorQuery implements IVendedorQuery {

private JdbcTemplate _conexao;

    public VendedorQuery(JdbcTemplate conexao) {
        this._conexao = conexao;
    }

    @Override
    public CompletableFuture<Resposta<Vendedor>> buscarPorMatricula(String matricula) {
        String query = "SELECT matricula, nome, datadenascimento, documento, tipodecontratacao, email, numeroFilial FROM vendedor WHERE matricula = ?";

        try {
            List<Vendedor> vendedor = _conexao.query(
                    query,
                    new Object[]{matricula},
                    (rs, rowNum) -> new Vendedor(
                            rs.getString("matricula"),
                            rs.getString("nome"),
                            rs.getDate("datadenascimento") != null ? rs.getDate("datadenascimento").toLocalDate() : null,
                            rs.getString("documento"),
                            rs.getString("email"),
                            TipoDeContratacao.valueOf(rs.getString("tipodecontratacao")),
                            rs.getInt("numerofilial")
            ));

            if(vendedor.isEmpty())
                return CompletableFuture.completedFuture(Resposta.successo(null));

            return CompletableFuture.completedFuture(Resposta.successo(vendedor.get(0)));

        } catch (Exception e) {
            return CompletableFuture.completedFuture(Resposta.erro("Erro ao buscar vendedor: " + e.getMessage()));
        }
    }

    @Override
    public CompletableFuture<Resposta<List<Vendedor>>> buscarPorDocumento(String documento) {
        String query = "SELECT matricula, nome, datadenascimento, documento, tipodecontratacao, email, numeroFilial FROM vendedor WHERE documento = ?";

        try {
            List<Vendedor> vendedores = _conexao.query(
                    query,
                    new Object[]{documento},
                    (rs, rowNum) -> new Vendedor(
                            rs.getString("matricula"),
                            rs.getString("nome"),
                            rs.getDate("datadenascimento") != null ? rs.getDate("datadenascimento").toLocalDate() : null,
                            rs.getString("documento"),
                            rs.getString("email"),
                            TipoDeContratacao.valueOf(rs.getString("tipodecontratacao")),
                            rs.getInt("numerofilial")
                    ));

            if(vendedores.isEmpty())
                return CompletableFuture.completedFuture(Resposta.successo(null));

            return CompletableFuture.completedFuture(Resposta.successo(vendedores));

        } catch (Exception e) {
            return CompletableFuture.completedFuture(Resposta.erro("Erro ao buscar vendedor: " + e.getMessage()));
        }
    }

    @Override
    public CompletableFuture<Resposta<List<Vendedor>>> buscarTodos() {
        String query = "SELECT matricula, nome, datadenascimento, documento, tipodecontratacao, email, numeroFilial FROM vendedor";

        try {

            List<Vendedor> vendedores = _conexao.query(query, new Object[]{}, (rs, rowNum) ->
                    new Vendedor(
                            rs.getString("matricula"),
                            rs.getString("nome"),
                            rs.getDate("datadenascimento") != null ? rs.getDate("datadenascimento").toLocalDate() : null,
                            rs.getString("documento"),
                            rs.getString("email"),
                            TipoDeContratacao.valueOf(rs.getString("tipodecontratacao")),
                            rs.getInt("numeroFilial")
                    ));


            return CompletableFuture.completedFuture(Resposta.successo(vendedores));
        }
        catch (Exception ex){
            return CompletableFuture.completedFuture(Resposta.erro("Falha ao buscar vendedores: " + ex.getMessage()));
        }
    }
}
