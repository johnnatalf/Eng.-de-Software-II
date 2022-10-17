package com.ifma.livraria.repository.impl;

import com.ifma.livraria.entity.Emprestimo;
import com.ifma.livraria.exceptions.LivrariaException;
import com.ifma.livraria.repository.AbstractRepository;
import com.ifma.livraria.repository.EmprestimoRepository;
import com.ifma.livraria.service.QueryService;
import com.ifma.livraria.utils.MessageProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

@Slf4j
@Repository
public class EmprestimoRepositoryImpl extends AbstractRepository implements EmprestimoRepository {

    public EmprestimoRepositoryImpl(JdbcTemplate jdbcTemplate, QueryService queryService, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, queryService, namedParameterJdbcTemplate);
    }


    @Override
    public Emprestimo salvarNovoEmprestimo(Emprestimo emprestimo) {
        int[] quantidadeSalva = this.getJdbcTemplate().batchUpdate(
                this.getQueryService().get("Emprestimo.salvar"),
                new BatchPreparedStatementSetter() {

                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setLong(1, emprestimo.getIdEmprestimo());
                        ps.setLong(2, emprestimo.getIdUser());
                        ps.setTimestamp(3, Timestamp.valueOf(emprestimo.getDataInicioEmprestimo()));
                        ps.setTimestamp(4, Timestamp.valueOf(emprestimo.getDataPrevistaDevolucaoEmprestimo()));
                        ps.setTimestamp(5, Timestamp.valueOf(emprestimo.getDataDevolucaoEmprestimo()));

                    }
                    @Override
                    public int getBatchSize() {
                        return 1;
                    }
                });

        if(quantidadeSalva.length > 0){
            int[] quantidadeSalvaLivro = this.getJdbcTemplate().batchUpdate(
                    this.getQueryService().get("Emprestimo.salvarLivrosEmprestimo"),
                    new BatchPreparedStatementSetter() {

                        @Override
                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            ps.setLong(1, emprestimo.getIdEmprestimo());
                            ps.setLong(2, emprestimo.getLivros().get(i).getId());
                        }
                        @Override
                        public int getBatchSize() {
                            return 1;
                        }
                    });

            return quantidadeSalvaLivro.length == emprestimo.getLivros().size() ? emprestimo : null;
        }
        return quantidadeSalva.length > 0 ? emprestimo : null;

    }

    @Override
    public boolean devolucaoDeEmprestimo(Emprestimo emprestimo) {
        int[] quantidadeDevolucao = this.getJdbcTemplate().batchUpdate(
                this.getQueryService().get("Emprestimo.devolucao"),
                new BatchPreparedStatementSetter() {

                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setTimestamp(1, Timestamp.valueOf(emprestimo.getDataPrevistaDevolucaoEmprestimo()));
                        ps.setLong(2, emprestimo.getIdEmprestimo());
                    }
                    @Override
                    public int getBatchSize() {
                        return 1;
                    }
                });
        if(quantidadeDevolucao.length > 0){
            int[] quantidadeLivroAtualizada = this.getJdbcTemplate().batchUpdate(
                    this.getQueryService().get("Livro.DevolverEmprestado"),
                    new BatchPreparedStatementSetter() {

                        @Override
                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            ps.setLong(1, emprestimo.getLivros().get(i).getId());
                        }
                        @Override
                        public int getBatchSize() {
                            return 1;
                        }
                    });

            if (quantidadeLivroAtualizada.length < emprestimo.getLivros().size()){
                throw new LivrariaException(
                        MessageProperties.getMensagemPadrao("emprestimo.devolucao.erro.livros"));
            }
        }
        return quantidadeDevolucao.length > 0;
    }
}
