package com.example.tarifas.dto;
import com.example.tarifas.model.FaixaConsumo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class FaixaConsumoResponseDTO {
    private Long id;
    private Integer inicio;
    private Integer fim;
    private BigDecimal valorUnitario;

    public static FaixaConsumoResponseDTO fromModel(FaixaConsumo faixaConsumo) {
        return new FaixaConsumoResponseDTO(
                faixaConsumo.getId(),
                faixaConsumo.getInicio(),
                faixaConsumo.getFim(),
                faixaConsumo.getValorUnitario()
        );
    }
}