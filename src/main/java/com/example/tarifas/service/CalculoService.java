package com.example.tarifas.service;

import com.example.tarifas.dto.CalculoRequestDTO;
import com.example.tarifas.dto.CalculoResponseDTO;
import com.example.tarifas.dto.DetalhamentoFaixaResponseDTO;
import com.example.tarifas.dto.FaixaConsumoResponseDTO;
import com.example.tarifas.model.Categoria;
import com.example.tarifas.model.CategoriaConsumo;
import com.example.tarifas.model.FaixaConsumo;
import com.example.tarifas.model.TabelaTarifaria;
import com.example.tarifas.repository.TabelaTarifariaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class CalculoService {

    private final TabelaTarifariaRepository tabelaTarifariaRepository;

    public CalculoService(TabelaTarifariaRepository tabelaTarifariaRepository) {
        this.tabelaTarifariaRepository = tabelaTarifariaRepository;
    }

    @Transactional(readOnly = true)
    public CalculoResponseDTO calcular(CalculoRequestDTO request) {
        Categoria categoriaInformada = request.getCategoria();

        List<TabelaTarifaria> tabelasAtivas = tabelaTarifariaRepository.findAll()
                .stream()
                .filter(TabelaTarifaria::isAtivo)
                .toList();

        if (tabelasAtivas.isEmpty()) {
            throw new RuntimeException("Nenhuma tabela tarifária ativa encontrada");
        }

        TabelaTarifaria tabelaAtiva = tabelasAtivas.stream()
                .max(Comparator.comparing(TabelaTarifaria::getDataVigencia))
                .orElseThrow(() -> new RuntimeException("Nenhuma tabela tarifária ativa encontrada"));

        CategoriaConsumo categoria = tabelaAtiva.getCategorias()
                .stream()
                .filter(c -> c.getCategoria().equals(categoriaInformada))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada na tabela tarifária ativa"));

        List<FaixaConsumo> faixasOrdenadas = categoria.getFaixas()
                .stream()
                .sorted(Comparator.comparingInt(FaixaConsumo::getInicio))
                .toList();

        List<DetalhamentoFaixaResponseDTO> detalhamento = new ArrayList<>();
        BigDecimal valorTotal = BigDecimal.ZERO;
        int consumoRestante = request.getConsumo();

        for (FaixaConsumo faixa : faixasOrdenadas) {
            if (consumoRestante <= 0) {
                break;
            }

            int capacidadeFaixa = faixa.getFim() - faixa.getInicio();
            int m3Cobrados = Math.min(consumoRestante, capacidadeFaixa);

            BigDecimal subtotal = faixa.getValorUnitario()
                    .multiply(BigDecimal.valueOf(m3Cobrados));

            detalhamento.add(new DetalhamentoFaixaResponseDTO(
                    FaixaConsumoResponseDTO.fromModel(faixa),
                    m3Cobrados,
                    faixa.getValorUnitario(),
                    subtotal.setScale(2, RoundingMode.HALF_UP)
            ));

            valorTotal = valorTotal.add(subtotal);
            consumoRestante -= m3Cobrados;
        }

        if (consumoRestante > 0) {
            throw new RuntimeException("As faixas cadastradas não cobrem o consumo informado");
        }

        return new CalculoResponseDTO(
                categoriaInformada.name(),
                request.getConsumo(),
                valorTotal.setScale(2, RoundingMode.HALF_UP),
                detalhamento
        );
    }
}