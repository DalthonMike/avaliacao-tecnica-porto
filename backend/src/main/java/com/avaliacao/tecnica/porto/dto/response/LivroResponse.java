package com.avaliacao.tecnica.porto.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LivroResponse {

    private Long id;

    private String titulo;

    private String autor;

    private String resumo;

    private Integer anoPublicacao;

    private String isbn;

    private List<ComentarioResponse> comentarios;

    private List<ProgressoLeituraResponse> progressos;
}
