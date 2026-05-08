package com.example.tarifas.controller;

import com.example.tarifas.dto.FaixaConsumoResponseDTO;
import com.example.tarifas.dto.TabelaTarifariaRequestDTO;
import com.example.tarifas.dto.TabelaTarifariaResponseDTO;
import com.example.tarifas.model.Categoria;
import com.example.tarifas.service.TabelaTarifariaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tabelas-tarifarias")
public class TabelaTarifariaController {

    private final TabelaTarifariaService tabelaTarifariaService;

    public TabelaTarifariaController(TabelaTarifariaService tabelaTarifariaService) {
        this.tabelaTarifariaService = tabelaTarifariaService;
    }

    @PostMapping
    public ResponseEntity<TabelaTarifariaResponseDTO> criarTabela(
            @Valid @RequestBody TabelaTarifariaRequestDTO request
    ) {
        TabelaTarifariaResponseDTO response = tabelaTarifariaService.criarTabela(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<TabelaTarifariaResponseDTO>> listarTabelas() {
        List<TabelaTarifariaResponseDTO> tabelas = tabelaTarifariaService.buscarTabelas();
        return ResponseEntity.ok(tabelas);
    }

    @GetMapping("/ativas")
    public ResponseEntity<List<TabelaTarifariaResponseDTO>> listarTabelasAtivas() {
        List<TabelaTarifariaResponseDTO> tabelasAtivas = tabelaTarifariaService.buscarTabelasAtivas();
        return ResponseEntity.ok(tabelasAtivas);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirTabela(@PathVariable Long id) {
        tabelaTarifariaService.excluirTabela(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<FaixaConsumoResponseDTO>> listarFaixasPorCategoria(
            @PathVariable Categoria categoria
    ) {
        return ResponseEntity.ok(tabelaTarifariaService.listarFaixasPorCategoria(categoria));
    }
}

