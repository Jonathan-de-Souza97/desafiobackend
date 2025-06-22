package br.com.grupocasasbahia.desafiobackend.vendedor.infra.databases;

import br.com.grupocasasbahia.desafiobackend.vendedor.infra.interfaces.ConexaoBancoDeDados;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;


public class JdbcTemplateAdapter implements ConexaoBancoDeDados {
    private final JdbcTemplate _jdbcTemplate;

    public JdbcTemplateAdapter(JdbcTemplate jdbcTemplate) {
        _jdbcTemplate = jdbcTemplate;
    }

    @Override
    public <T> List<T> query(String sql, Object[] params, RowMapper<T> rowMapper) {
        return _jdbcTemplate.query(sql, params, rowMapper);
    }

    @Override
    public int comando(String sql, Object[] params) {
        return _jdbcTemplate.update(sql, params);
    }
}
