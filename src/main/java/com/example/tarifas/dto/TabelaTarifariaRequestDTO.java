package com.example.tarifas.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TabelaTarifariaRequestDTO {
    private String nome;
    private LocalDate dataVigencia;
}