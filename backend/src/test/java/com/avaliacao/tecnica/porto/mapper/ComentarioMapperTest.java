package com.avaliacao.tecnica.porto.mapper;

import com.avaliacao.tecnica.porto.dto.response.ComentarioResponse;
import com.avaliacao.tecnica.porto.mapper.comentario.ComentarioMapper;
import com.avaliacao.tecnica.porto.model.Comentario;
import com.avaliacao.tecnica.porto.model.Livro;
import com.avaliacao.tecnica.porto.model.Usuario;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ComentarioMapperTest {

    private final ComentarioMapper mapper = Mappers.getMapper(ComentarioMapper.class);

    @Test
    void toResponse_ShouldMapFieldsCorrectly() {
        Usuario usuario = Usuario.builder()
                .id(1L)
                .nome("João")
                .email("joao@email.com")
                .build();

        Livro livro = Livro.builder()
                .id(10L)
                .titulo("Livro Teste")
                .autor("Autor X")
                .build();

        Comentario comentario = Comentario.builder()
                .id(100L)
                .usuario(usuario)
                .livro(livro)
                .mensagem("Gostei bastante")
                .dataComentario(LocalDateTime.of(2025, 1, 1, 12, 0))
                .spoiler(false)
                .build();

        ComentarioResponse response = mapper.toResponse(comentario);

        assertNotNull(response);
        assertEquals(comentario.getId(), response.getId());
        assertEquals(comentario.getMensagem(), response.getMensagem());
        assertEquals(comentario.getDataComentario(), response.getDataComentario());
        assertEquals(comentario.isSpoiler(), response.isSpoiler());
        assertEquals(usuario.getNome(), response.getUsuario());
        assertEquals(livro.getTitulo(), response.getLivro());
    }

    @Test
    void toResponse_ShouldReturnNull_WhenComentarioIsNull() {
        ComentarioResponse response = mapper.toResponse(null);

        assertNull(response);
    }

    @Test
    void toResponse_ShouldHandleNullUsuarioAndLivro() {
        Comentario comentario = Comentario.builder()
                .id(101L)
                .usuario(null)
                .livro(null)
                .mensagem("Mensagem teste")
                .dataComentario(LocalDateTime.now())
                .spoiler(false)
                .build();

        ComentarioResponse response = mapper.toResponse(comentario);

        assertNotNull(response);
        assertEquals(comentario.getId(), response.getId());
        assertEquals("Mensagem teste", response.getMensagem());
        assertNull(response.getUsuario());
        assertNull(response.getLivro());
    }

    @Test
    void toResponse_ShouldHandleNullNomeAndTitulo() {
        Usuario usuario = Usuario.builder().id(2L).nome(null).build();
        Livro livro = Livro.builder().id(20L).titulo(null).build();

        Comentario comentario = Comentario.builder()
                .id(102L)
                .usuario(usuario)
                .livro(livro)
                .mensagem("Mensagem com null")
                .dataComentario(LocalDateTime.now())
                .spoiler(false)
                .build();

        ComentarioResponse response = mapper.toResponse(comentario);

        assertNotNull(response);
        assertNull(response.getUsuario());
        assertNull(response.getLivro());
    }
}
