package com.example.tarifas.model;

import jakarta.persistence.*;
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
@Entity

@Table(name ="tabela_tarifaria")
public class TabelaTarifaria {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", length = 50, nullable = false)
    @NotBlank(message = "O nome é obrigatório.")
    private String nome;

    @Column(name = "data_vigencia", nullable = false)
    @NotNull(message = "A data de vigência é obrigatória.")
    private LocalDate dataVigencia;

    @Column(name = "status", nullable = false)
    private boolean ativo = true;

    @OneToMany(mappedBy = "tabelaTarifaria", cascade = CascadeType.ALL)
    private List<CategoriaConsumo> categorias;

}