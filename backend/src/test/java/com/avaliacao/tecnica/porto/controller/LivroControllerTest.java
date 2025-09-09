package com.avaliacao.tecnica.porto.controller;

import com.avaliacao.tecnica.porto.dto.request.LivroRequest;
import com.avaliacao.tecnica.porto.dto.response.ComentarioResponse;
import com.avaliacao.tecnica.porto.dto.response.LivroResponse;
import com.avaliacao.tecnica.porto.mapper.comentario.ComentarioMapper;
import com.avaliacao.tecnica.porto.mapper.livro.LivroMapper;
import com.avaliacao.tecnica.porto.model.Comentario;
import com.avaliacao.tecnica.porto.model.Livro;
import com.avaliacao.tecnica.porto.service.ComentarioService;
import com.avaliacao.tecnica.porto.service.LivroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LivroControllerTest {

    @Mock
    private LivroService livroService;

    @Mock
    private LivroMapper livroMapper;

    @Mock
    private ComentarioService comentarioService;

    @Mock
    private ComentarioMapper comentarioMapper;

    @InjectMocks
    private LivroController livroController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBuscarTodos() {
        Livro livro1 = new Livro(); livro1.setId(1L);
        Livro livro2 = new Livro(); livro2.setId(2L);

        LivroResponse r1 = LivroResponse.builder().id(1L).build();
        LivroResponse r2 = LivroResponse.builder().id(2L).build();

        when(livroService.buscarTodosAtivos()).thenReturn(List.of(livro1, livro2));
        when(livroMapper.toResponse(livro1)).thenReturn(r1);
        when(livroMapper.toResponse(livro2)).thenReturn(r2);

        var response = livroController.buscarTodos();

        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(livroService, times(1)).buscarTodosAtivos();
    }

    @Test
    void testBuscarPorId() {
        Livro livro = new Livro(); livro.setId(1L);
        LivroResponse responseObj = LivroResponse.builder().id(1L).build();

        when(livroService.buscarPorId(1L)).thenReturn(livro);
        when(livroMapper.toResponse(livro)).thenReturn(responseObj);

        var response = livroController.buscarPorId(1L);

        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void testCadastrar() {
        LivroRequest request = LivroRequest.builder().titulo("Livro X").build();
        Livro entidade = new Livro();
        Livro salvo = new Livro();
        LivroResponse responseObj = LivroResponse.builder().titulo("Livro X").build();

        when(livroMapper.toEntity(request)).thenReturn(entidade);
        when(livroService.cadastrar(entidade)).thenReturn(salvo);
        when(livroMapper.toResponse(salvo)).thenReturn(responseObj);

        var response = livroController.cadastrar(request);

        assertNotNull(response.getBody());
        assertEquals("Livro X", response.getBody().getTitulo());
    }

    @Test
    void testAtualizar() {
        LivroRequest request = LivroRequest.builder().titulo("Novo Livro").build();
        Livro atualizado = new Livro();
        LivroResponse responseObj = LivroResponse.builder().titulo("Novo Livro").build();

        when(livroService.atualizar(request, 1L)).thenReturn(atualizado);
        when(livroMapper.toResponse(atualizado)).thenReturn(responseObj);

        var response = livroController.atualizar(request, 1L);

        assertNotNull(response.getBody());
        assertEquals("Novo Livro", response.getBody().getTitulo());
    }

    @Test
    void testExcluir() {
        doNothing().when(livroService).excluirLogicamente(1L);

        var response = livroController.excluir(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(livroService, times(1)).excluirLogicamente(1L);
    }

    @Test
    void testListarComentariosPorLivro() {
        ComentarioResponse c1 = ComentarioResponse.builder().id(1L).build();
        ComentarioResponse c2 = ComentarioResponse.builder().id(2L).build();

        when(comentarioService.listarComentariosPorLivro(1L, 1L)).thenReturn(List.of(c1, c2));

        var response = livroController.listarComentariosPorLivro(1L, 1L);

        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testListarTodosPorLivro() {
        Comentario c1 = new Comentario(); c1.setId(1L);
        Comentario c2 = new Comentario(); c2.setId(2L);

        ComentarioResponse r1 = ComentarioResponse.builder().id(1L).build();
        ComentarioResponse r2 = ComentarioResponse.builder().id(2L).build();

        when(comentarioService.listarTodosPorLivro(1L)).thenReturn(List.of(c1, c2));
        when(comentarioMapper.toResponse(c1)).thenReturn(r1);
        when(comentarioMapper.toResponse(c2)).thenReturn(r2);

        var response = livroController.listarTodosPorLivro(1L);

        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }
}