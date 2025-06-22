package br.com.grupocasasbahia.desafiobackend.vendedor.infra.interfaces;

import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public interface ConexaoBancoDeDados {
    <T> List<T> query(String sql, Object[] params, RowMapper<T> rowMapper);
    int comando(String sql, Object[] params);
}
