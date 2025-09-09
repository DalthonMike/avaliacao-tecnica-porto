package com.avaliacao.tecnica.porto.mapper;

import com.avaliacao.tecnica.porto.dto.request.LivroRequest;
import com.avaliacao.tecnica.porto.dto.response.ComentarioResponse;
import com.avaliacao.tecnica.porto.dto.response.LivroResponse;
import com.avaliacao.tecnica.porto.dto.response.ProgressoLeituraResponse;
import com.avaliacao.tecnica.porto.mapper.livro.LivroMapper;
import com.avaliacao.tecnica.porto.model.Comentario;
import com.avaliacao.tecnica.porto.model.Livro;
import com.avaliacao.tecnica.porto.model.ProgressoLeitura;
import com.avaliacao.tecnica.porto.model.Usuario;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LivroMapperTest {

    private final LivroMapper mapper = Mappers.getMapper(LivroMapper.class);

    @Test
    void toEntity_ShouldMapFieldsCorrectly() {
        LivroRequest request = LivroRequest.builder()
                .id(1L)
                .titulo("Livro Teste")
                .autor("Autor X")
                .resumo("Resumo do livro")
                .anoPublicacao(2025)
                .isbn("1234567890")
                .build();

        Livro livro = mapper.toEntity(request);

        assertNotNull(livro);
        assertEquals(request.getId(), livro.getId());
        assertEquals(request.getTitulo(), livro.getTitulo());
        assertEquals(request.getAutor(), livro.getAutor());
        assertEquals(request.getResumo(), livro.getResumo());
        assertEquals(request.getAnoPublicacao(), livro.getAnoPublicacao());
        assertEquals(request.getIsbn(), livro.getIsbn());

        assertNull(livro.getComentarios());
        assertNull(livro.getProgressos());
        assertNull(livro.getStatus());
    }

    @Test
    void mapComentarios_ShouldHandleNullAndValues() {
        assertNull(mapper.mapComentarios(null));

        Usuario usuario = Usuario.builder().id(1L).nome("João").build();
        Comentario comentario = Comentario.builder()
                .id(100L)
                .usuario(usuario)
                .mensagem("Mensagem teste")
                .dataComentario(LocalDateTime.of(2025,1,1,12,0))
                .build();
        List<ComentarioResponse> responses = mapper.mapComentarios(List.of(comentario));

        assertNotNull(responses);
        assertEquals(1, responses.size());
        ComentarioResponse response = responses.get(0);
        assertEquals(comentario.getId(), response.getId());
        assertEquals(comentario.getMensagem(), response.getMensagem());
        assertEquals(comentario.getDataComentario(), response.getDataComentario());
        assertEquals(usuario.getNome(), response.getUsuario());
    }

    @Test
    void mapProgressos_ShouldHandleNullAndValues() {
        assertNull(mapper.mapProgressos(null));

        Usuario usuario = Usuario.builder().id(2L).nome("Maria").build();
        Livro livro = Livro.builder().id(10L).titulo("Livro Teste").build();
        ProgressoLeitura progresso = ProgressoLeitura.builder()
                .id(200L)
                .usuario(usuario)
                .livro(livro)
                .paginaAtual(50)
                .dataRegistro(LocalDateTime.of(2025,1,2,10,0))
                .build();

        List<ProgressoLeituraResponse> responses = mapper.mapProgressos(List.of(progresso));

        assertNotNull(responses);
        assertEquals(1, responses.size());
        ProgressoLeituraResponse response = responses.get(0);
        assertEquals(progresso.getId(), response.getId());
        assertEquals(usuario.getNome(), response.getUsuario());
        assertEquals(livro.getTitulo(), response.getLivro());
        assertEquals(progresso.getPaginaAtual(), response.getPaginaAtual());
        assertEquals(progresso.getDataRegistro(), response.getDataRegistro());
    }

    @Test
    void toResponse_ShouldMapEntityToResponseIncludingLists() {
        Usuario usuario = Usuario.builder().id(1L).nome("João").build();
        Comentario comentario = Comentario.builder()
                .id(101L)
                .usuario(usuario)
                .mensagem("Comentário legal")
                .dataComentario(LocalDateTime.of(2025,1,1,12,0))
                .build();

        Livro livro = Livro.builder()
                .id(1L)
                .titulo("Livro Teste")
                .autor("Autor X")
                .resumo("Resumo")
                .anoPublicacao(2025)
                .isbn("1234567890")
                .comentarios(List.of(comentario))
                .progressos(Collections.emptyList())
                .build();

        LivroResponse response = mapper.toResponse(livro);

        assertNotNull(response);
        assertEquals(livro.getId(), response.getId());
        assertEquals(livro.getTitulo(), response.getTitulo());
        assertEquals(1, response.getComentarios().size());
        assertEquals("João", response.getComentarios().get(0).getUsuario());
        assertNotNull(response.getProgressos());
        assertTrue(response.getProgressos().isEmpty());
    }
}
