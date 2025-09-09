package com.avaliacao.tecnica.porto.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioRequest {

    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 80, message = "O nome pode ter no máximo 80 caracteres")
    private String nome;

    @NotBlank(message = "O email é obrigatório")
    @Size(max = 100, message = "O email pode ter no máximo 100 caracteres")
    private String email;

    @NotNull(message = "O verSpoilers é obrigatório")
    private boolean verSpoilers;

}
