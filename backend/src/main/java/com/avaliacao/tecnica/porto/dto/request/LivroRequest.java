package com.avaliacao.tecnica.porto.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LivroRequest {

    private Long id;

    @NotBlank(message = "O título é obrigatório")
    @Size(max = 20, message = "O título pode ter no máximo 20 caracteres")
    private String titulo;

    @NotBlank(message = "O autor é obrigatório")
    @Size(max = 80, message = "O autor pode ter no máximo 80 caracteres")
    private String autor;

    @Size(max = 2000, message = "O resumo pode ter no máximo 2000 caracteres")
    private String resumo;

    @NotNull(message = "O ano de publicação é obrigatório")
    private Integer anoPublicacao;

    @NotBlank(message = "O ISBN é obrigatório")
    @Size(max = 20, message = "O ISBN pode ter no máximo 20 caracteres")
    private String isbn;
}
