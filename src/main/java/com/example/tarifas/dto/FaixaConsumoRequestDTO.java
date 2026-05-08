package com.example.tarifas.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
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
    @Min(value = 0, message = "O valor inicial não pode ser negativo")
    private Integer inicio;

    @NotNull(message = "O valor final é obrigatório")
    @Min(value = 0, message = "O valor final não pode ser negativo")
    private Integer fim;

    @NotNull(message = "O valor por metro é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "O valor por metro deve ser maior que zero")
    private BigDecimal valorUnitario;
}
