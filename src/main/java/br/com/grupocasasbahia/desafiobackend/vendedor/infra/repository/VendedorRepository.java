package br.com.grupocasasbahia.desafiobackend.vendedor.infra.repository;

import br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces.IVendedorRepository;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.response.Resposta;
import br.com.grupocasasbahia.desafiobackend.vendedor.core.entities.Vendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.core.enums.TipoDeContratacao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Repository
public class VendedorRepository implements IVendedorRepository {

    private final JdbcTemplate _conexao;

    public VendedorRepository(JdbcTemplate conexao) {
        _conexao = conexao;
    }

    @Override
    public CompletableFuture<Resposta<Vendedor>> buscarPorMatricula(String matricula) {
        String query = "SELECT * FROM vendedor WHERE matricula = ?";

        try {
            List<Vendedor> vendedor = _conexao.query(
                    query,
                    new Object[]{matricula},
                    (rs, rowNum) -> new Vendedor(
                            rs.getString("matricula"),
                            rs.getString("nome"),
                            rs.getDate("datadenascimento").toLocalDate(),
                            rs.getString("documento"),
                            rs.getString("email"),
                            TipoDeContratacao.valueOf(rs.getString("tipodecontratacao")),
                            rs.getInt("numerofilial"))
            );

            if(vendedor.isEmpty())
                return CompletableFuture.completedFuture(Resposta.successo(null));

            return CompletableFuture.completedFuture(Resposta.successo(vendedor.get(0)));

        } catch (Exception e) {
            return CompletableFuture.completedFuture(Resposta.erro("Erro ao buscar vendedor: " + e.getMessage()));
        }
    }

    @Override
    public CompletableFuture<Resposta<Vendedor>> buscarPorDocumento(String documento) {
        String query = "SELECT * FROM vendedor WHERE documento = ?";
        try {
            List<Vendedor> vendedor = _conexao.query(query, new Object[]{documento}, (rs,rowNum) ->
                    new Vendedor(rs.getString("matricula"),
                            rs.getString("nome"),
                            rs.getDate("datadenascimento").toLocalDate(),
                            rs.getString("documento"),
                            rs.getString("email"),
                            TipoDeContratacao.valueOf(rs.getString("tipodecontratacao")),
                            rs.getInt("numerofilial"))
            );

            if(vendedor.isEmpty())
                return CompletableFuture.completedFuture(Resposta.successo(null));

            return CompletableFuture.completedFuture(Resposta.successo(vendedor.get(0)));
        }
        catch (Exception ex){
            return CompletableFuture.completedFuture(Resposta.erro("Erro ao buscar vendedor:" + ex.getMessage()));
        }
    }

    @Override
    public CompletableFuture<Resposta<List<Vendedor>>> buscarTodos() {
        return null;
    }

    @Override
    public CompletableFuture<Resposta<Integer>> buscarNovaMatricula() {
        String querySelect = "SELECT ultimocodigo FROM controlesequenciamentomatricula FOR UPDATE";
        String queryUpdate = "UPDATE controlesequenciamentomatricula SET ultimocodigo = ?";

        try {
            Connection connection = _conexao.getDataSource().getConnection();
            connection.setAutoCommit(false);

            JdbcTemplate jdbcComTransacao = new JdbcTemplate(new SingleConnectionDataSource(connection, true));

            List<Integer> resultado = jdbcComTransacao.query(
                    querySelect,
                    new Object[]{},
                    (rs, rowNum) -> rs.getInt("ultimocodigo")
            );

            int novoCodigo = resultado.get(0) + 1;

            jdbcComTransacao.update(queryUpdate, novoCodigo);

            connection.commit();
            connection.close();

            return CompletableFuture.completedFuture(Resposta.successo(novoCodigo));

        } catch (Exception e) {
            return CompletableFuture.completedFuture(Resposta.erro("Erro ao buscar nova matrícula: " + e.getMessage()));
        }
    }

    @Override
    public CompletableFuture<Resposta<String>> salvar(Vendedor vendedor) {
        String query = "INSERT INTO vendedor (id, matricula, nome, datadenascimento, documento, email, tipodecontratacao,numerofilial)" +
                       " VALUES " +
                       "(?,?,?,?,?,?,?,?)";
        try{
            _conexao.update(query, new Object[]{vendedor.getId(), vendedor.getMatricula(), vendedor.getNome(),vendedor.getDataDeNascimento(),
                    vendedor.getDocumento(), vendedor.getEmail(), vendedor.getTipoDeContratacao().name(), vendedor.getnumeroFilial()});

            return CompletableFuture.completedFuture(Resposta.successo((vendedor.getMatricula())));
        }
        catch (Exception ex){
            return CompletableFuture.completedFuture(Resposta.erro("Falha ao salvar vendedor: " + ex.getMessage()));
        }
    }

    @Override
    public CompletableFuture<Resposta<String>> editar(Vendedor vendedor) {
        return null;
    }

    @Override
    public CompletableFuture<Resposta<String>> apagar(String matricula) {
        String query = "DELETE FROM vendedor WHERE matricula = ?";

        try{
            _conexao.update(query,new Object[]{matricula});

            return CompletableFuture.completedFuture(Resposta.successo(matricula));
        }
        catch (Exception ex){
            return CompletableFuture.completedFuture(Resposta.erro("Falha ao apagar vendedor"));
        }
    }
}
