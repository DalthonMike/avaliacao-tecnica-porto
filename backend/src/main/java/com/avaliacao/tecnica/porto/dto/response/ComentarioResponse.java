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
public class ComentarioResponse {

    private Long id;

    private String livro;

    private String usuario;

    private String mensagem;

    private LocalDateTime dataComentario;

    private boolean spoiler;
}
