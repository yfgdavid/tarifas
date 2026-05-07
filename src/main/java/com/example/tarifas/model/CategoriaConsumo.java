package com.example.tarifas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity

@Table(name = "categoria_consumo")
public class CategoriaConsumo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn (name = "tabela_tarifaria_id", nullable = false)
    private TabelaTarifaria tabelaTarifaria;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria", nullable = false)
    private Categoria categoria;

    @OneToMany(mappedBy = "categoriaConsumo", cascade = CascadeType.ALL)
    private List<FaixaConsumo> faixas;
}
