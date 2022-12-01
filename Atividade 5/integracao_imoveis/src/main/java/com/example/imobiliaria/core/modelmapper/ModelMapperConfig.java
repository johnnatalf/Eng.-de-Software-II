package com.example.imobiliaria.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
//        metodo utilizado para injetar um modelMapper, Lib responsavel por evitar os codigos Boilerplate
        return new ModelMapper();
    }
}
