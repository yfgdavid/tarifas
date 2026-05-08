package com.example.tarifas.dto;
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

    private FaixaConsumoResponseDTO faixa;
    private Integer m3Cobrados;
    private BigDecimal valorUnitario;
    private BigDecimal subtotal;
}