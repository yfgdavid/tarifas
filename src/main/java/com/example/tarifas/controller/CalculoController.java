package com.example.tarifas.controller;
import com.example.tarifas.dto.CalculoRequestDTO;
import com.example.tarifas.dto.CalculoResponseDTO;
import com.example.tarifas.service.CalculoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/calculos")
public class CalculoController {

    private final CalculoService calculoService;

    public CalculoController(CalculoService calculoService) {
        this.calculoService = calculoService;
    }

    @PostMapping
    public ResponseEntity<CalculoResponseDTO> calcular(
            @Valid @RequestBody CalculoRequestDTO request
    ) {
        CalculoResponseDTO response = calculoService.calcular(request);
        return ResponseEntity.ok(response);
    }
}
