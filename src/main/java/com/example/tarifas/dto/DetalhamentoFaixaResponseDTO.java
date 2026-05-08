package com.example.tarifas.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetalhamentoFaixaResponseDTO {

    @Valid
    @NotNull(message = "A faixa é obrigatória")
    private FaixaConsumoResponseDTO faixa;

    @NotNull(message = "A quantidade de m3 cobrados é obrigatória")
    @Min(value = 0, message = "A quantidade de m3 cobrados não pode ser negativa")
    private Integer m3Cobrados;

    @NotNull(message = "O valor unitário é obrigatório")
    private BigDecimal valorUnitario;

    @NotNull(message = "O subtotal é obrigatório")
    private BigDecimal subtotal;
}