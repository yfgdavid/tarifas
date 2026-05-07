package com.example.tarifas.dto;

import com.example.tarifas.model.Categoria;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaConsumoRequestDTO {

    @NotNull(message = "A categoria é obrigatória")
    private Categoria categoria;

    @Valid
    @NotNull(message = "As faixas são obrigatórias")
    private List<FaixaConsumoRequestDTO> faixas;
}