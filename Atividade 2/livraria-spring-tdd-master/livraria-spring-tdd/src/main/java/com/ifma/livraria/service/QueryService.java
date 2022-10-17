package com.ifma.livraria.service;

import com.ifma.livraria.dto.Pageable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Arrays;

@Service
@PropertySource("classpath:queries.xml")
public class QueryService {

    private Environment environment;

    public QueryService(Environment environment) {
        this.environment = environment;
    }

    /**
     * Recupera a query com a chave informada.
     * 
     * @param key (chave) para busca da query.
     * @return uma {@link String} contendo a query.
     */
    public String get(final String key) {
        Assert.notNull(key, "A chave (key) não pode ser nula.");
        Assert.hasText(key, "A chave (key) não pode ser vazia.");
        return StringUtils.stripToEmpty(this.environment.getProperty(key));
    }

    /**
     * Método para adicionar order by e paginação em uma query.
     * 
     * @param query param
     * @param pageable param
     * @param ordem param
     * @return
     */
    public String addOrdemEPaginacao(String query, Pageable pageable, String[] ordem) {

        String queryComOrdem = adicionarOrdem(query, ordem);

        return adicionarPaginacao(queryComOrdem, pageable);
    }

    private String adicionarOrdem(String query, String... ordem) {
        return query.concat(" ORDER BY ").concat(Arrays.toString(ordem));
    }

    private String adicionarPaginacao(String queryComOrdem, Pageable pageable) {
        return queryComOrdem.concat(" OFFSET ")
            .concat(pageable.getOffset().toString())
            .concat(" ROWS FETCH NEXT ")
            .concat(pageable.getTamanhoPagina().toString())
            .concat(" ROWS ONLY ");
    }
}
