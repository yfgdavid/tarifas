package com.example.tarifas.service;

import com.example.tarifas.dto.CategoriaConsumoRequestDTO;
import com.example.tarifas.dto.FaixaConsumoRequestDTO;
import com.example.tarifas.dto.TabelaTarifariaRequestDTO;
import com.example.tarifas.dto.TabelaTarifariaResponseDTO;
import com.example.tarifas.model.CategoriaConsumo;
import com.example.tarifas.model.FaixaConsumo;
import com.example.tarifas.model.TabelaTarifaria;
import com.example.tarifas.repository.TabelaTarifariaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
public class TabelaTarifariaService {

    private final TabelaTarifariaRepository tabelaTarifariaRepository;

    public TabelaTarifariaService(TabelaTarifariaRepository tabelaTarifariaRepository) {
        this.tabelaTarifariaRepository = tabelaTarifariaRepository;
    }

    public TabelaTarifariaResponseDTO criarTabela(TabelaTarifariaRequestDTO tabelaTarifariaDTO) {
        TabelaTarifaria tabelaTarifaria = new TabelaTarifaria();
        tabelaTarifaria.setNome(tabelaTarifariaDTO.getNome());
        tabelaTarifaria.setDataVigencia(tabelaTarifariaDTO.getDataVigencia());
        tabelaTarifaria.setAtivo(true);

        List<CategoriaConsumo> categorias = tabelaTarifariaDTO.getCategorias()
                .stream()
                .map(categoriaDTO -> criarCategoria(categoriaDTO, tabelaTarifaria))
                .toList();

        tabelaTarifaria.setCategorias(categorias);

        validarCategorias(categorias);

        TabelaTarifaria salva = tabelaTarifariaRepository.save(tabelaTarifaria);
        return TabelaTarifariaResponseDTO.fromModel(salva);
    }

    @Transactional(readOnly = true)
    public List<TabelaTarifariaResponseDTO> buscarTabelas() {
        return tabelaTarifariaRepository.findAll()
                .stream()
                .map(TabelaTarifariaResponseDTO::fromModel)
                .toList();
    }

    public void excluirTabela(Long id) {
        TabelaTarifaria tabela = tabelaTarifariaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tabela tarifária não encontrada"));

        tabela.setAtivo(false);
        tabelaTarifariaRepository.save(tabela);
    }

    private CategoriaConsumo criarCategoria(CategoriaConsumoRequestDTO categoriaDTO, TabelaTarifaria tabelaTarifaria) {
        CategoriaConsumo categoria = new CategoriaConsumo();
        categoria.setCategoria(categoriaDTO.getCategoria());
        categoria.setTabelaTarifaria(tabelaTarifaria);

        List<FaixaConsumo> faixas = categoriaDTO.getFaixas()
                .stream()
                .map(faixaDTO -> criarFaixa(faixaDTO, categoria))
                .toList();

        categoria.setFaixas(faixas);
        return categoria;
    }

    private FaixaConsumo criarFaixa(FaixaConsumoRequestDTO faixaDTO, CategoriaConsumo categoria) {
        FaixaConsumo faixa = new FaixaConsumo();
        faixa.setInicio(faixaDTO.getInicio());
        faixa.setFim(faixaDTO.getFim());
        faixa.setValorUnitario(faixaDTO.getValorUnitario());
        faixa.setCategoriaConsumo(categoria);
        return faixa;
    }

    private void validarCategorias(List<CategoriaConsumo> categorias) {
        if (categorias == null || categorias.isEmpty()) {
            throw new RuntimeException("A tabela tarifária deve possuir ao menos uma categoria");
        }

        for (CategoriaConsumo categoria : categorias) {
            validarFaixas(categoria.getFaixas());
        }
    }

    private void validarFaixas(List<FaixaConsumo> faixas) {
        if (faixas == null || faixas.isEmpty()) {
            throw new RuntimeException("A categoria deve possuir ao menos uma faixa");
        }

        List<FaixaConsumo> faixasOrdenadas = faixas.stream()
                .sorted(Comparator.comparingInt(FaixaConsumo::getInicio))
                .toList();

        if (faixasOrdenadas.get(0).getInicio() != 0) {
            throw new RuntimeException("As faixas devem iniciar em 0");
        }

        for (int i = 0; i < faixasOrdenadas.size(); i++) {
            FaixaConsumo atual = faixasOrdenadas.get(i);

            if (atual.getInicio() >= atual.getFim()) {
                throw new RuntimeException("O início da faixa deve ser menor que o fim");
            }

            if (i > 0) {
                FaixaConsumo anterior = faixasOrdenadas.get(i - 1);

                if (atual.getInicio() <= anterior.getFim()) {
                    throw new RuntimeException("Existem faixas sobrepostas");
                }

                if (atual.getInicio() != anterior.getFim() + 1) {
                    throw new RuntimeException("Existem buracos entre as faixas");
                }
            }
        }
    }
}