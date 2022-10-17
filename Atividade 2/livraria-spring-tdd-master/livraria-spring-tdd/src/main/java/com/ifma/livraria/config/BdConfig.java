package com.ifma.livraria.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class BdConfig {

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSourceConfig = new BasicDataSource();
        dataSourceConfig.setDriverClassName("org.postgresql.Driver");

        dataSourceConfig.setUrl("jdbc:postgresql://127.0.0.1:5432/livrariabd");
        dataSourceConfig.setUsername("postgres");
        dataSourceConfig.setValidationQuery("SELECT 1");
        dataSourceConfig.setPassword("erl");

        return dataSourceConfig;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
    	
        return new JdbcTemplate(dataSource);
    }
    
    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }
}
