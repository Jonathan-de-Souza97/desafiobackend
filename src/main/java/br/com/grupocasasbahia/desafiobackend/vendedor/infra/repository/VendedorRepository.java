package br.com.grupocasasbahia.desafiobackend.vendedor.infra.repository;

import br.com.grupocasasbahia.desafiobackend.vendedor.application.interfaces.IVendedorRepository;
import br.com.grupocasasbahia.desafiobackend.vendedor.application.response.Resposta;
import br.com.grupocasasbahia.desafiobackend.vendedor.core.entities.Vendedor;
import br.com.grupocasasbahia.desafiobackend.vendedor.core.enums.TipoDeContratacao;
import br.com.grupocasasbahia.desafiobackend.vendedor.infra.interfaces.ConexaoBancoDeDados;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Repository
public class VendedorRepository implements IVendedorRepository {

    private final ConexaoBancoDeDados _conexao;

    public VendedorRepository(ConexaoBancoDeDados conexao) {
        _conexao = conexao;
    }

    @Override
    public CompletableFuture<Resposta<Vendedor>> buscarPorMatricula(String matricula) {
        String query = "SELECT * FROM vendedor WHERE matricula = ?";

        try {
            List<Vendedor> vendedores = _conexao.query(
                    query,
                    new Object[]{matricula},
                    (rs, rowNum) -> new Vendedor(
                            rs.getString("matricula"),
                            rs.getString("nome"),
                            rs.getDate("dataDeNascimento").toLocalDate(),
                            rs.getString("documento"),
                            rs.getString("email"),
                            TipoDeContratacao.converteAPartirDoCodigo(rs.getInt("tipoDeContratacao")),
                            rs.getInt("numero_filial")                   )
            );

            if (vendedores.isEmpty())
                return CompletableFuture.completedFuture(Resposta.erro("Vendedor não encontrado"));

            return CompletableFuture.completedFuture(Resposta.successo(vendedores.get(0)));

        } catch (Exception e) {
            return CompletableFuture.completedFuture(Resposta.erro("Erro ao buscar vendedor: " + e.getMessage()));
        }
    }

    @Override
    public CompletableFuture<Resposta<Vendedor>> buscarPorDocumento(String documento) {
        return null;
    }

    @Override
    public CompletableFuture<Resposta<List<Vendedor>>> buscarTodos() {
        return null;
    }

    @Override
    public CompletableFuture<Resposta<Integer>> buscarNovaMatricula() {
        return null;
    }

    @Override
    public CompletableFuture<Resposta<String>> salvar(Vendedor vendedor) {
        return null;
    }

    @Override
    public CompletableFuture<Resposta<String>> editar(Vendedor vendedor) {
        return null;
    }

    @Override
    public CompletableFuture<Resposta<String>> apagar(String matricula) {
        return null;
    }
}
