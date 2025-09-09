package com.avaliacao.tecnica.porto.service;

import com.avaliacao.tecnica.porto.dto.request.ComentarioRequest;
import com.avaliacao.tecnica.porto.dto.response.ComentarioResponse;
import com.avaliacao.tecnica.porto.exception.NegocioException;
import com.avaliacao.tecnica.porto.model.Comentario;
import com.avaliacao.tecnica.porto.model.Livro;
import com.avaliacao.tecnica.porto.model.Usuario;
import com.avaliacao.tecnica.porto.repository.ComentarioRepository;
import com.avaliacao.tecnica.porto.repository.LivroRepository;
import com.avaliacao.tecnica.porto.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ComentarioServiceTest {

    @Mock
    private ComentarioRepository comentarioRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private LivroRepository livroRepository;

    @InjectMocks
    private ComentarioService comentarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCadastrarComentarioSucesso() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("João");

        Livro livro = new Livro();
        livro.setId(1L);
        livro.setTitulo("Livro Teste");

        ComentarioRequest request = ComentarioRequest.builder()
                .usuarioId(1L)
                .livroId(1L)
                .mensagem("Ótimo livro!")
                .spoiler(false)
                .build();

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));
        when(comentarioRepository.save(any(Comentario.class))).thenAnswer(i -> i.getArgument(0));

        Comentario resultado = comentarioService.cadastrar(request);

        assertNotNull(resultado);
        assertEquals("Ótimo livro!", resultado.getMensagem());
        assertEquals(usuario, resultado.getUsuario());
        assertEquals(livro, resultado.getLivro());
        verify(comentarioRepository, times(1)).save(any(Comentario.class));
    }

    @Test
    void testCadastrarComentarioUsuarioNaoEncontrado() {
        ComentarioRequest request = ComentarioRequest.builder()
                .usuarioId(1L)
                .livroId(1L)
                .mensagem("Mensagem")
                .spoiler(false)
                .build();

        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        NegocioException exception = assertThrows(NegocioException.class,
                () -> comentarioService.cadastrar(request));

        assertEquals("Usuário não encontrado com id 1", exception.getMessage());
        verify(comentarioRepository, never()).save(any());
    }

    @Test
    void testListarComentariosPorLivroFiltraSpoilers() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Maria");
        usuario.setVerSpoilers(false);

        Livro livro = new Livro();
        livro.setId(1L);
        livro.setTitulo("Livro Teste");

        Comentario c1 = Comentario.builder().id(1L).usuario(usuario).livro(livro)
                .mensagem("Comentário 1").spoiler(false).dataComentario(LocalDateTime.now()).build();
        Comentario c2 = Comentario.builder().id(2L).usuario(usuario).livro(livro)
                .mensagem("Comentário 2").spoiler(true).dataComentario(LocalDateTime.now()).build();

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(comentarioRepository.findByLivroId(1L)).thenReturn(List.of(c1, c2));

        List<ComentarioResponse> respostas = comentarioService.listarComentariosPorLivro(1L, 1L);

        assertEquals(1, respostas.size());
        assertEquals("Comentário 1", respostas.get(0).getMensagem());
    }

    @Test
    void testListarTodosPorLivro() {
        Livro livro = new Livro();
        livro.setId(1L);

        Comentario c1 = Comentario.builder().id(1L).mensagem("Comentário 1").build();
        Comentario c2 = Comentario.builder().id(2L).mensagem("Comentário 2").build();

        when(comentarioRepository.findByLivroId(1L)).thenReturn(List.of(c1, c2));

        List<Comentario> resultado = comentarioService.listarTodosPorLivro(1L);

        assertEquals(2, resultado.size());
        verify(comentarioRepository, times(1)).findByLivroId(1L);
    }
}
