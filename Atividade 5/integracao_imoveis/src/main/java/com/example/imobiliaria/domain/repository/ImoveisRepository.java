package com.example.imobiliaria.domain.repository;

import com.example.imobiliaria.domain.model.Imoveis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ImoveisRepository extends JpaRepository<Imoveis, Long> {
    List<Imoveis> findAllByBairroAndTipo_ImovelAndAtivo(String bairro, String tipo);

}
