package com.example.tarifas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CalculoResponseDTO {

    private String categoria;
    private Integer consumoTotal;
    private BigDecimal valorTotal;
    private List<DetalhamentoFaixaResponseDTO> detalhamento;


}