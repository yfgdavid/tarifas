package com.example.tarifas.Model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity

@Table(name = "faixa_consumo")

public class FaixaConsumo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "inicio", nullable = false)
    @NotNull(message = "O valor inicial é obrigatório")
    private Integer inicio;

    @Column(name = "fim", nullable = false)
    @NotNull(message = "O valor inicial é obrigatório")
    private Integer fim;

    @Column(name = "valor_unitario", nullable = false)
    @NotNull(message = "O valor por metro é obrigatório")
    private BigDecimal valorUnitario;

    @ManyToOne
    @JoinColumn(name = "categoria_consumo_id", nullable = false)
    private CategoriaConsumo categoriaConsumo;
}
