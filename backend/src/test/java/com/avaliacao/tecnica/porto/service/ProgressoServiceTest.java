package com.avaliacao.tecnica.porto.service;

import com.avaliacao.tecnica.porto.dto.request.ProgressoRequest;
import com.avaliacao.tecnica.porto.exception.NegocioException;
import com.avaliacao.tecnica.porto.model.Livro;
import com.avaliacao.tecnica.porto.model.ProgressoLeitura;
import com.avaliacao.tecnica.porto.model.Usuario;
import com.avaliacao.tecnica.porto.repository.LivroRepository;
import com.avaliacao.tecnica.porto.repository.ProgressoRepository;
import com.avaliacao.tecnica.porto.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProgressoServiceTest {

    @Mock
    private ProgressoRepository progressoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private LivroRepository livroRepository;

    @InjectMocks
    private ProgressoService progressoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBuscarPorIdSucesso() {
        ProgressoLeitura progresso = new ProgressoLeitura();
        progresso.setId(1L);

        when(progressoRepository.findById(1L)).thenReturn(Optional.of(progresso));

        ProgressoLeitura resultado = progressoService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    void testBuscarPorIdNaoEncontrado() {
        when(progressoRepository.findById(1L)).thenReturn(Optional.empty());

        NegocioException exception = assertThrows(NegocioException.class,
                () -> progressoService.buscarPorId(1L));

        assertEquals("Não existe um progresso com o id: 1informado", exception.getMessage());
    }

    @Test
    void testSalvarOuAtualizarAtualizaProgressoExistente() {
        ProgressoRequest request = ProgressoRequest.builder()
                .usuarioId(1L)
                .livroId(1L)
                .paginaAtual(50)
                .build();

        Usuario usuario = new Usuario();
        usuario.setId(1L);

        Livro livro = new Livro();
        livro.setId(1L);

        ProgressoLeitura progressoExistente = ProgressoLeitura.builder()
                .id(1L)
                .usuario(usuario)
                .livro(livro)
                .paginaAtual(30)
                .dataRegistro(LocalDateTime.now().minusDays(1))
                .build();

        when(progressoRepository.findByUsuarioIdAndLivroId(1L, 1L))
                .thenReturn(progressoExistente);
        when(progressoRepository.save(any(ProgressoLeitura.class))).thenAnswer(i -> i.getArgument(0));

        ProgressoLeitura resultado = progressoService.salvarOuAtualizar(request);

        assertEquals(50, resultado.getPaginaAtual());
        assertNotNull(resultado.getDataRegistro());
        verify(progressoRepository, times(1)).save(progressoExistente);
    }

    @Test
    void testSalvarOuAtualizarCriaNovoProgresso() {
        ProgressoRequest request = ProgressoRequest.builder()
                .usuarioId(1L)
                .livroId(1L)
                .paginaAtual(10)
                .build();

        Usuario usuario = new Usuario();
        usuario.setId(1L);

        Livro livro = new Livro();
        livro.setId(1L);

        when(progressoRepository.findByUsuarioIdAndLivroId(1L, 1L)).thenReturn(null);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));
        when(progressoRepository.save(any(ProgressoLeitura.class))).thenAnswer(i -> i.getArgument(0));

        ProgressoLeitura resultado = progressoService.salvarOuAtualizar(request);

        assertEquals(10, resultado.getPaginaAtual());
        assertEquals(usuario, resultado.getUsuario());
        assertEquals(livro, resultado.getLivro());
        assertNotNull(resultado.getDataRegistro());
        verify(progressoRepository, times(1)).save(any(ProgressoLeitura.class));
    }

    @Test
    void testSalvarOuAtualizarUsuarioNaoEncontrado() {
        ProgressoRequest request = ProgressoRequest.builder()
                .usuarioId(1L)
                .livroId(1L)
                .paginaAtual(10)
                .build();

        when(progressoRepository.findByUsuarioIdAndLivroId(1L, 1L)).thenReturn(null);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        NegocioException exception = assertThrows(NegocioException.class,
                () -> progressoService.salvarOuAtualizar(request));

        assertEquals("Usuário não encontrado", exception.getMessage());
    }

    @Test
    void testSalvarOuAtualizarLivroNaoEncontrado() {
        ProgressoRequest request = ProgressoRequest.builder()
                .usuarioId(1L)
                .livroId(1L)
                .paginaAtual(10)
                .build();

        Usuario usuario = new Usuario();
        usuario.setId(1L);

        when(progressoRepository.findByUsuarioIdAndLivroId(1L, 1L)).thenReturn(null);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(livroRepository.findById(1L)).thenReturn(Optional.empty());

        NegocioException exception = assertThrows(NegocioException.class,
                () -> progressoService.salvarOuAtualizar(request));

        assertEquals("Livro não encontrado", exception.getMessage());
    }
}
