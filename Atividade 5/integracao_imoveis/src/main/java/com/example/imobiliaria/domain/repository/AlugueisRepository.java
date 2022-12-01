package com.example.imobiliaria.domain.repository;

import com.example.imobiliaria.domain.model.Alugueis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlugueisRepository extends JpaRepository<Alugueis, Long> {


    List<Alugueis> findAllByLocacaoCliente_Nome(String nome);

    List<Alugueis> findAllByLocacaoClienteNome(String nome);

    Alugueis findByLocacao_Imovel_Id(Long id);
}
