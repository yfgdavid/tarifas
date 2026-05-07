package com.example.tarifas.dto;
import com.example.tarifas.model.TabelaTarifaria;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class TabelaTarifariaResponseDTO {

    private Long id;
    private String nome;
    private LocalDate dataVigencia;
    private boolean ativo;
    private List<CategoriaConsumoResponseDTO> categorias;

    public static TabelaTarifariaResponseDTO fromModel(TabelaTarifaria tabelaTarifaria) {
        return new TabelaTarifariaResponseDTO(
                tabelaTarifaria.getId(),
                tabelaTarifaria.getNome(),
                tabelaTarifaria.getDataVigencia(),
                tabelaTarifaria.isAtivo(),
                tabelaTarifaria.getCategorias().stream()
                        .map(CategoriaConsumoResponseDTO::fromModel)
                        .toList()

        );


    }
}
