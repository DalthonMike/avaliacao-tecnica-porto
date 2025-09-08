package com.avaliacao.tecnica.porto.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProgressoLeituraResponse {

    private Long id;

    private String usuario;

    private String livro;

    private Integer paginaAtual;

    private LocalDateTime dataRegistro;
}
