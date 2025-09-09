package com.avaliacao.tecnica.porto.mapper;

import com.avaliacao.tecnica.porto.dto.request.ProgressoRequest;
import com.avaliacao.tecnica.porto.dto.response.ProgressoLeituraResponse;
import com.avaliacao.tecnica.porto.mapper.progresso.ProgressoMapper;
import com.avaliacao.tecnica.porto.model.Livro;
import com.avaliacao.tecnica.porto.model.ProgressoLeitura;
import com.avaliacao.tecnica.porto.model.Usuario;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ProgressoMapperTest {

    private final ProgressoMapper mapper = Mappers.getMapper(ProgressoMapper.class);

    @Test
    void toEntity_ShouldMapFieldsCorrectly() {
        ProgressoRequest request = ProgressoRequest.builder()
                .id(1L)
                .paginaAtual(50)
                .build();

        ProgressoLeitura entity = mapper.toEntity(request);

        assertNotNull(entity);
        assertEquals(request.getId(), entity.getId());
        assertEquals(request.getPaginaAtual(), entity.getPaginaAtual());
        // Usuario e Livro não são mapeados
        assertNull(entity.getUsuario());
        assertNull(entity.getLivro());
        assertNull(entity.getDataRegistro());
    }

    @Test
    void toEntity_ShouldReturnNull_WhenRequestIsNull() {
        assertNull(mapper.toEntity(null));
    }

    @Test
    void toResponse_ShouldMapFieldsCorrectly() {
        Usuario usuario = Usuario.builder().id(1L).nome("João").build();
        Livro livro = Livro.builder().id(10L).titulo("Livro Teste").build();
        LocalDateTime data = LocalDateTime.of(2025, 1, 1, 12, 0);

        ProgressoLeitura progresso = ProgressoLeitura.builder()
                .id(100L)
                .usuario(usuario)
                .livro(livro)
                .paginaAtual(75)
                .dataRegistro(data)
                .build();

        ProgressoLeituraResponse response = mapper.toResponse(progresso);

        assertNotNull(response);
        assertEquals(progresso.getId(), response.getId());
        assertEquals(progresso.getPaginaAtual(), response.getPaginaAtual());
        assertEquals(usuario.getNome(), response.getUsuario());
        assertEquals(livro.getTitulo(), response.getLivro());
        assertEquals(data, response.getDataRegistro());
    }

    @Test
    void toResponse_ShouldHandleNullProgresso() {
        assertNull(mapper.toResponse(null));
    }

    @Test
    void toResponse_ShouldHandleNullUsuarioAndLivro() {
        ProgressoLeitura progresso = ProgressoLeitura.builder()
                .id(101L)
                .usuario(null)
                .livro(null)
                .paginaAtual(10)
                .dataRegistro(LocalDateTime.now())
                .build();

        ProgressoLeituraResponse response = mapper.toResponse(progresso);

        assertNotNull(response);
        assertNull(response.getUsuario());
        assertNull(response.getLivro());
        assertEquals(progresso.getId(), response.getId());
        assertEquals(progresso.getPaginaAtual(), response.getPaginaAtual());
    }

    @Test
    void toResponse_ShouldHandleNullNomeAndTitulo() {
        Usuario usuario = Usuario.builder().id(2L).nome(null).build();
        Livro livro = Livro.builder().id(20L).titulo(null).build();

        ProgressoLeitura progresso = ProgressoLeitura.builder()
                .id(102L)
                .usuario(usuario)
                .livro(livro)
                .paginaAtual(30)
                .dataRegistro(LocalDateTime.now())
                .build();

        ProgressoLeituraResponse response = mapper.toResponse(progresso);

        assertNotNull(response);
        assertNull(response.getUsuario());
        assertNull(response.getLivro());
        assertEquals(progresso.getId(), response.getId());
        assertEquals(progresso.getPaginaAtual(), response.getPaginaAtual());
    }
}
