package com.ifma.livraria.repository;

import com.ifma.livraria.dto.Pageable;
import com.ifma.livraria.exceptions.NaoEncontradoException;
import com.ifma.livraria.utils.MessageProperties;
import com.ifma.livraria.service.QueryService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.util.Assert;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static com.ifma.livraria.utils.MessageProperties.getMensagemPadrao;


@Slf4j
public abstract class AbstractRepository {

    private static final String TAMANHO_PAGINA = "tamanhoPagina";

    private static final String OFF_SET = "offSet";

    private static final String ERRO = "Erro: {}";

    private static final String ERRO_CONEXAO_BANCO_DADOS =
            getMensagemPadrao("erro.conexao.banco.dados");

    private static final String PAGEABLE_NAO_PODE_SER_NULO =
            getMensagemPadrao("erro.pageable.nao.pode.ser.nulo");

    private static final String O_ROW_MAPPER_NAO_PODE_SER_NULO =
            getMensagemPadrao("erro.rowmapper.nao.pode.ser.nulo");

    private static final String O_MAP_DE_PARAMETROS_NAO_PODE_SER_NULO =
            getMensagemPadrao("erro.map.parameters.nao.pode.ser.nulo");

    private static final String A_CHAVE_DA_QUERY_NAO_PODE_SER_ESTAR_VAZIA =
            getMensagemPadrao("erro.chave.query.nao.pode.estar.vazia");

    private static final String A_CHAVE_DA_QUERY_NAO_PODE_SER_NULA =
            getMensagemPadrao("erro.chave.query.nao.pode.ser.nula");

    private static final String ORDEM_NAO_PODE_SER_NULO =
            getMensagemPadrao("erro.ordem.nao.pode.ser.nula");

    private JdbcTemplate jdbcTemplate;
    private QueryService queryService;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Contrutor padrão.
     * 
     * @param jdbcTemplate Params
     * @param queryService Params
     */
    protected AbstractRepository(JdbcTemplate jdbcTemplate, QueryService queryService) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryService = queryService;
    }

    /**
     * Contrutor padrão.
     * 
     * @param jdbcTemplate Params
     * @param queryService Params
     * @param namedParameterJdbcTemplate Params
     */
    protected AbstractRepository(JdbcTemplate jdbcTemplate, QueryService queryService,
                                 NamedParameterJdbcTemplate namedParameterJdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
        this.queryService = queryService;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public QueryService getQueryService() {
        return queryService;
    }

    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
        return namedParameterJdbcTemplate;
    }

    protected Integer count(String query) {
        return this.count(query, new MapSqlParameterSource());
    }

    protected Double sum(String query, MapSqlParameterSource mapSqlParameterSource) {
        return this.somar(query, mapSqlParameterSource);
    }

    protected Integer count(String query, MapSqlParameterSource params) {

        Assert.notNull(query, A_CHAVE_DA_QUERY_NAO_PODE_SER_NULA);
        Assert.hasText(query, A_CHAVE_DA_QUERY_NAO_PODE_SER_ESTAR_VAZIA);
        Assert.notNull(params, O_MAP_DE_PARAMETROS_NAO_PODE_SER_NULO);

        try {
            Integer quantidadeRegistros = getNamedParameterJdbcTemplate()
                    .query(getQueryService().get(query), params, (rs, rowNum) -> rs.getInt(1))
                    .get(0);

            if (quantidadeRegistros == 0) {
                throw new NaoEncontradoException();
            }

            return quantidadeRegistros;
        } catch (DataAccessException e) {
            log.info(e.getMessage());
            throw new ServiceException(
                    MessageProperties.getMensagemPadrao(ERRO_CONEXAO_BANCO_DADOS));
        }
    }

    protected Double somar(String query, MapSqlParameterSource params) {

        Assert.notNull(query, A_CHAVE_DA_QUERY_NAO_PODE_SER_NULA);
        Assert.hasText(query, A_CHAVE_DA_QUERY_NAO_PODE_SER_ESTAR_VAZIA);
        Assert.notNull(params, O_MAP_DE_PARAMETROS_NAO_PODE_SER_NULO);

        try {
            Double quantidadeRegistros = getNamedParameterJdbcTemplate()
                    .query(getQueryService().get(query), params, (rs, rowNum) -> rs.getDouble(1))
                    .get(0);

            if (quantidadeRegistros == 0) {
                throw new NaoEncontradoException();
            }

            return quantidadeRegistros;
        } catch (DataAccessException e) {
            log.info(e.getMessage());
            throw new ServiceException(
                    MessageProperties.getMensagemPadrao(ERRO_CONEXAO_BANCO_DADOS));
        }
    }

    protected <T> List<T> query(String sql, MapSqlParameterSource params, RowMapper<T> rowMapper) {

        Assert.notNull(sql, A_CHAVE_DA_QUERY_NAO_PODE_SER_NULA);
        Assert.hasText(sql, A_CHAVE_DA_QUERY_NAO_PODE_SER_ESTAR_VAZIA);
        Assert.notNull(params, O_MAP_DE_PARAMETROS_NAO_PODE_SER_NULO);
        Assert.notNull(rowMapper, O_ROW_MAPPER_NAO_PODE_SER_NULO);

        try {
            return this.getNamedParameterJdbcTemplate().query(getQueryService().get(sql), params,
                    rowMapper);

        } catch (DataAccessException e) {
            log.info(ERRO, e.getMessage());
            throw new ServiceException(
                    MessageProperties.getMensagemPadrao(ERRO_CONEXAO_BANCO_DADOS));
        }
    }

    protected <T> List<T> query(String sql, RowMapper<T> rowMapper) {

        Assert.notNull(sql, A_CHAVE_DA_QUERY_NAO_PODE_SER_NULA);
        Assert.hasText(sql, A_CHAVE_DA_QUERY_NAO_PODE_SER_ESTAR_VAZIA);
        Assert.notNull(rowMapper, O_ROW_MAPPER_NAO_PODE_SER_NULO);

        try {
            return this.getNamedParameterJdbcTemplate().query(getQueryService().get(sql),
                    rowMapper);

        } catch (DataAccessException e) {
            log.info(ERRO, e.getMessage());
            throw new ServiceException(
                    MessageProperties.getMensagemPadrao(ERRO_CONEXAO_BANCO_DADOS));
        }
    }

    protected <T> T query(String sql, MapSqlParameterSource params,
            ResultSetExtractor<T> resultSetExtrator) {

        Assert.notNull(sql, A_CHAVE_DA_QUERY_NAO_PODE_SER_NULA);
        Assert.hasText(sql, A_CHAVE_DA_QUERY_NAO_PODE_SER_ESTAR_VAZIA);
        Assert.notNull(params, O_MAP_DE_PARAMETROS_NAO_PODE_SER_NULO);
        Assert.notNull(resultSetExtrator, O_ROW_MAPPER_NAO_PODE_SER_NULO);

        try {
            return this.getNamedParameterJdbcTemplate().query(getQueryService().get(sql), params,
                    resultSetExtrator);

        } catch (DataAccessException e) {
            log.info(ERRO, e.getMessage());
            throw new ServiceException(
                    MessageProperties.getMensagemPadrao(ERRO_CONEXAO_BANCO_DADOS));
        }


    }

    protected <T> T query(String sql, ResultSetExtractor<T> resultSetExtrator) {

        Assert.notNull(sql, A_CHAVE_DA_QUERY_NAO_PODE_SER_NULA);
        Assert.hasText(sql, A_CHAVE_DA_QUERY_NAO_PODE_SER_ESTAR_VAZIA);
        Assert.notNull(resultSetExtrator, O_ROW_MAPPER_NAO_PODE_SER_NULO);

        try {
            return this.getNamedParameterJdbcTemplate().query(getQueryService().get(sql),
                    resultSetExtrator);

        } catch (DataAccessException e) {
            throw new ServiceException(
                    MessageProperties.getMensagemPadrao(ERRO_CONEXAO_BANCO_DADOS));
        }
    }

    protected <T> T queryForObject(String sql, MapSqlParameterSource params,
            RowMapper<T> rowMapper) {

        Assert.notNull(sql, A_CHAVE_DA_QUERY_NAO_PODE_SER_NULA);
        Assert.hasText(sql, A_CHAVE_DA_QUERY_NAO_PODE_SER_ESTAR_VAZIA);
        Assert.notNull(params, O_MAP_DE_PARAMETROS_NAO_PODE_SER_NULO);
        Assert.notNull(rowMapper, O_ROW_MAPPER_NAO_PODE_SER_NULO);

        try {
            return this.getNamedParameterJdbcTemplate().queryForObject(getQueryService().get(sql),
                    params, rowMapper);

        } catch (EmptyResultDataAccessException de) {
            throw new NaoEncontradoException(
                    MessageProperties.getMensagemPadrao("response.code204"));
        } catch (DataAccessException e) {
            log.info(ERRO, e.getMessage());
            throw new ServiceException(
                    MessageProperties.getMensagemPadrao(ERRO_CONEXAO_BANCO_DADOS));
        }
    }

    protected <T> List<T> obterPaginado(String sql, MapSqlParameterSource params,
            RowMapper<T> rowMapper, Pageable pageable) {

        Assert.notNull(pageable, PAGEABLE_NAO_PODE_SER_NULO);

        params.addValue(OFF_SET, pageable.getOffset());
        params.addValue(TAMANHO_PAGINA, pageable.getTamanhoPagina());

        try {
            return this.query(sql, params, rowMapper);
        } catch (DataAccessException e) {
            log.info(ERRO, e.getMessage());
            throw new ServiceException(
                    MessageProperties.getMensagemPadrao(ERRO_CONEXAO_BANCO_DADOS));
        }
    }

    protected <T> T obterPaginado(String sql, MapSqlParameterSource params,
            ResultSetExtractor<T> resultSetExtrator, Pageable pageable) {

        Assert.notNull(sql, A_CHAVE_DA_QUERY_NAO_PODE_SER_NULA);
        Assert.hasText(sql, A_CHAVE_DA_QUERY_NAO_PODE_SER_ESTAR_VAZIA);
        Assert.notNull(params, O_MAP_DE_PARAMETROS_NAO_PODE_SER_NULO);
        Assert.notNull(resultSetExtrator, O_ROW_MAPPER_NAO_PODE_SER_NULO);
        Assert.notNull(pageable, PAGEABLE_NAO_PODE_SER_NULO);

        params.addValue(OFF_SET, pageable.getOffset());
        params.addValue(TAMANHO_PAGINA, pageable.getTamanhoPagina());

        try {
            return this.getNamedParameterJdbcTemplate().query(getQueryService().get(sql), params,
                    resultSetExtrator);
        } catch (DataAccessException e) {
            log.info(ERRO, e.getMessage());
            throw new ServiceException(
                    MessageProperties.getMensagemPadrao(ERRO_CONEXAO_BANCO_DADOS));
        }
    }

    protected <T> List<T> obterPaginado(String sql, RowMapper<T> rowMapper, Pageable pageable) {
        return this.obterPaginado(sql, new MapSqlParameterSource(), rowMapper, pageable);
    }

    protected <T> T obterPaginado(String sql, ResultSetExtractor<T> resultSetExtrator,
            Pageable pageable) {
        return this.obterPaginado(sql, new MapSqlParameterSource(), resultSetExtrator, pageable);
    }

    protected Integer contar(String query, MapSqlParameterSource params) {

        try {
            return getNamedParameterJdbcTemplate()
                    .query(getQueryService().get(query), params, (rs, rowNum) -> rs.getInt(1))
                    .get(0);
        } catch (DataAccessException e) {
            throw new ServiceException(
                    MessageProperties.getMensagemPadrao(ERRO_CONEXAO_BANCO_DADOS));
        }
    }

    protected Timestamp getTimeStamp(LocalDateTime ldt) {
        return ldt == null ? null : Timestamp.valueOf(ldt);
    }

    protected <T> List<T> obterPaginadoOrdenado(String sql, MapSqlParameterSource params,
            RowMapper<T> rowMapper, Pageable pageable, String... ordem) {

        Assert.notNull(sql, A_CHAVE_DA_QUERY_NAO_PODE_SER_NULA);
        Assert.hasText(sql, A_CHAVE_DA_QUERY_NAO_PODE_SER_ESTAR_VAZIA);
        Assert.notNull(params, O_MAP_DE_PARAMETROS_NAO_PODE_SER_NULO);
        Assert.notNull(rowMapper, O_ROW_MAPPER_NAO_PODE_SER_NULO);
        Assert.notNull(pageable, PAGEABLE_NAO_PODE_SER_NULO);
        Assert.notNull(ordem, ORDEM_NAO_PODE_SER_NULO);


        params.addValue(OFF_SET, pageable.getOffset());
        params.addValue(TAMANHO_PAGINA, pageable.getTamanhoPagina());

        String addOrdemEPaginacao =
                getQueryService().addOrdemEPaginacao(getQueryService().get(sql), pageable, ordem);

        try {
            return this.getNamedParameterJdbcTemplate().query(addOrdemEPaginacao, params,
                    rowMapper);
        } catch (DataAccessException e) {
            log.info(ERRO, e.getMessage());
            throw new ServiceException(
                    MessageProperties.getMensagemPadrao(ERRO_CONEXAO_BANCO_DADOS));
        }
    }
}
