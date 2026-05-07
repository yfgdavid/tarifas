package com.example.tarifas.dto;

import com.example.tarifas.model.Categoria;
import com.example.tarifas.model.CategoriaConsumo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CategoriaConsumoResponseDTO {
    private Long id;
    private Long idTabelaTarifaria;
    private Categoria categoria;
    private List<FaixaConsumoResponseDTO> faixas;


    public static CategoriaConsumoResponseDTO fromModel(CategoriaConsumo categoriaConsumo){
        return new CategoriaConsumoResponseDTO(
                categoriaConsumo.getId(),
                categoriaConsumo.getTabelaTarifaria().getId(),
                categoriaConsumo.getCategoria(),
                categoriaConsumo.getFaixas().stream()
                        .map(FaixaConsumoResponseDTO::fromModel)
                        .toList()
        );
    }
}
