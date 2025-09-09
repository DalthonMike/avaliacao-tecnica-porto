package com.avaliacao.tecnica.porto.service;

import com.avaliacao.tecnica.porto.dto.request.LivroRequest;
import com.avaliacao.tecnica.porto.enums.Status;
import com.avaliacao.tecnica.porto.exception.NegocioException;
import com.avaliacao.tecnica.porto.model.Livro;
import com.avaliacao.tecnica.porto.repository.LivroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LivroServiceTest {

    @Mock
    private LivroRepository livroRepository;

    @InjectMocks
    private LivroService livroService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBuscarTodosAtivos() {
        Livro livro1 = new Livro();
        livro1.setStatus(Status.ATIVO);
        Livro livro2 = new Livro();
        livro2.setStatus(Status.ATIVO);

        when(livroRepository.findAllByStatus(Status.ATIVO)).thenReturn(List.of(livro1, livro2));

        List<Livro> livros = livroService.buscarTodosAtivos();

        assertEquals(2, livros.size());
        verify(livroRepository, times(1)).findAllByStatus(Status.ATIVO);
    }

    @Test
    void testBuscarPorIdSucesso() {
        Livro livro = new Livro();
        livro.setId(1L);
        livro.setStatus(Status.ATIVO);

        when(livroRepository.findByIdAndStatus(1L, Status.ATIVO)).thenReturn(livro);

        Livro resultado = livroService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    void testBuscarPorIdNaoEncontrado() {
        when(livroRepository.findByIdAndStatus(1L, Status.ATIVO)).thenReturn(null);

        NegocioException exception = assertThrows(NegocioException.class,
                () -> livroService.buscarPorId(1L));

        assertEquals("Não foi possível encontrar um livro com o identificador 1", exception.getMessage());
    }

    @Test
    void testCadastrarLivro() {
        Livro livro = new Livro();
        livro.setTitulo("Livro Teste");

        when(livroRepository.save(any(Livro.class))).thenAnswer(i -> i.getArgument(0));

        Livro resultado = livroService.cadastrar(livro);

        assertEquals(Status.ATIVO, resultado.getStatus());
        assertEquals("Livro Teste", resultado.getTitulo());
        verify(livroRepository, times(1)).save(livro);
    }

    @Test
    void testAtualizarLivro() {
        Livro livroExistente = new Livro();
        livroExistente.setId(1L);
        livroExistente.setTitulo("Titulo Antigo");
        livroExistente.setStatus(Status.ATIVO);

        LivroRequest request = LivroRequest.builder()
                .titulo("Titulo Novo")
                .autor("Autor Novo")
                .resumo("Resumo")
                .anoPublicacao(2025)
                .isbn("12345")
                .build();

        when(livroRepository.findByIdAndStatus(1L, Status.ATIVO)).thenReturn(livroExistente);
        when(livroRepository.save(any(Livro.class))).thenAnswer(i -> i.getArgument(0));

        Livro resultado = livroService.atualizar(request, 1L);

        assertEquals("Titulo Novo", resultado.getTitulo());
        assertEquals("Autor Novo", resultado.getAutor());
        assertEquals(2025, resultado.getAnoPublicacao());
        verify(livroRepository, times(1)).save(livroExistente);
    }

    @Test
    void testExcluirLogicamente() {
        Livro livro = new Livro();
        livro.setId(1L);
        livro.setStatus(Status.ATIVO);

        when(livroRepository.findByIdAndStatus(1L, Status.ATIVO)).thenReturn(livro);
        when(livroRepository.save(any(Livro.class))).thenAnswer(i -> i.getArgument(0));

        livroService.excluirLogicamente(1L);

        assertEquals(Status.INATIVO, livro.getStatus());
        verify(livroRepository, times(1)).save(livro);
    }
}
