package com.example.tarifas.dto;

import com.example.tarifas.model.Categoria;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CalculoRequestDTO {

    @NotNull(message = "A categoria é obrigatória")
    private Categoria categoria;

    @NotNull(message = "A informação sobre o consumo é obrigatória.")
    @Min(value = 0, message = "O consumo não pode ser negativo.")
    private Integer consumo;

}


