package com.example.imobiliaria.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class ImovelIdInput {

    @NotNull
    private Long id;
}
