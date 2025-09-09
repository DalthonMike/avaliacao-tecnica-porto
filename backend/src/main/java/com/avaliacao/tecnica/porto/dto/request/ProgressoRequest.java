package com.avaliacao.tecnica.porto.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProgressoRequest {

    private Long id;

    @NotNull(message = "O identificador do usuário obrigatório")
    private Long usuarioId;

    @NotNull(message = "O identificador do livro obrigatório")
    private Long livroId;

    @NotNull(message = "A página atual é obrigatória")
    private Integer paginaAtual;

}
