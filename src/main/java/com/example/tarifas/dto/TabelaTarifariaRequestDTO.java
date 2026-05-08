package com.example.tarifas.dto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class TabelaTarifariaRequestDTO {
    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotNull(message = "A data de vigência é obrigatória")
    private LocalDate dataVigencia;

    @Valid
    @NotNull(message = "As categorias são obrigatórias")
    private List<CategoriaConsumoRequestDTO> categorias;
}
