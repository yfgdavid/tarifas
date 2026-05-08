package com.example.tarifas.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ErrorResponse {

    private LocalDateTime timestamp;
    private Integer status;
    private String erro;
    private String mensagem;
    private Map<String, String> campos;


}