package com.example.tarifas.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class FaixaConsumoRequestDTO {

    @NotNull(message = "O valor inicial é obrigatório")
    private Integer inicio;

    @NotNull(message = "O valor final é obrigatório")
    private Integer fim;

    @NotNull(message = "O valor por metro é obrigatório")
    private BigDecimal valorUnitario;
}
