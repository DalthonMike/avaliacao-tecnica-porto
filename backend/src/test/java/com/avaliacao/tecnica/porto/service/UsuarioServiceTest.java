package com.avaliacao.tecnica.porto.service;

import com.avaliacao.tecnica.porto.dto.request.UsuarioRequest;
import com.avaliacao.tecnica.porto.enums.Status;
import com.avaliacao.tecnica.porto.exception.NegocioException;
import com.avaliacao.tecnica.porto.model.Usuario;
import com.avaliacao.tecnica.porto.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBuscarTodosAtivos() {
        Usuario u1 = new Usuario(); u1.setId(1L);
        Usuario u2 = new Usuario(); u2.setId(2L);

        when(usuarioRepository.findAllByStatus(Status.ATIVO)).thenReturn(List.of(u1, u2));

        List<Usuario> resultado = usuarioService.buscarTodosAtivos();

        assertEquals(2, resultado.size());
        verify(usuarioRepository, times(1)).findAllByStatus(Status.ATIVO);
    }

    @Test
    void testBuscarPorIdSucesso() {
        Usuario u = new Usuario(); u.setId(1L);

        when(usuarioRepository.findByIdAndStatus(1L, Status.ATIVO)).thenReturn(u);

        Usuario resultado = usuarioService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    void testBuscarPorIdNaoEncontrado() {
        when(usuarioRepository.findByIdAndStatus(1L, Status.ATIVO)).thenReturn(null);

        NegocioException exception = assertThrows(NegocioException.class,
                () -> usuarioService.buscarPorId(1L));

        assertEquals("Não foi possível encontrar um usuario com o identificador 1", exception.getMessage());
    }

    @Test
    void testCadastrarSucesso() {
        Usuario u = new Usuario();
        u.setEmail("teste@email.com");

        when(usuarioRepository.findByEmail("teste@email.com")).thenReturn(null);
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(i -> i.getArgument(0));

        Usuario resultado = usuarioService.cadastrar(u);

        assertEquals(Status.ATIVO, resultado.getStatus());
        verify(usuarioRepository, times(1)).save(u);
    }

    @Test
    void testCadastrarEmailDuplicado() {
        Usuario u = new Usuario();
        u.setEmail("teste@email.com");

        when(usuarioRepository.findByEmail("teste@email.com")).thenReturn(new Usuario());

        NegocioException exception = assertThrows(NegocioException.class,
                () -> usuarioService.cadastrar(u));

        assertEquals("Já existe um usuário com o e-mail informado: teste@email.com", exception.getMessage());
    }

    @Test
    void testAtualizarSucesso() {
        Usuario existente = new Usuario();
        existente.setId(1L);
        existente.setEmail("old@email.com");

        UsuarioRequest request = UsuarioRequest.builder()
                .nome("Novo Nome")
                .email("novo@email.com")
                .verSpoilers(true)
                .build();

        when(usuarioRepository.findByIdAndStatus(1L, Status.ATIVO)).thenReturn(existente);
        when(usuarioRepository.findByEmail("novo@email.com")).thenReturn(null);
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(i -> i.getArgument(0));

        Usuario resultado = usuarioService.atualizar(request, 1L);

        assertEquals("Novo Nome", resultado.getNome());
        assertEquals("novo@email.com", resultado.getEmail());
        assertTrue(resultado.isVerSpoilers());
    }

    @Test
    void testAtualizarEmailDuplicado() {
        Usuario existente = new Usuario();
        existente.setId(1L);
        existente.setEmail("old@email.com");

        UsuarioRequest request = UsuarioRequest.builder()
                .nome("Nome")
                .email("duplicado@email.com")
                .verSpoilers(true)
                .build();

        when(usuarioRepository.findByIdAndStatus(1L, Status.ATIVO)).thenReturn(existente);
        when(usuarioRepository.findByEmail("duplicado@email.com")).thenReturn(new Usuario());

        NegocioException exception = assertThrows(NegocioException.class,
                () -> usuarioService.atualizar(request, 1L));

        assertEquals("Já existe um usuário com o e-mail informado: duplicado@email.com", exception.getMessage());
    }

    @Test
    void testExcluirLogicamente() {
        Usuario existente = new Usuario();
        existente.setId(1L);
        existente.setStatus(Status.ATIVO);

        when(usuarioRepository.findByIdAndStatus(1L, Status.ATIVO)).thenReturn(existente);
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(i -> i.getArgument(0));

        usuarioService.excluirLogicamente(1L);

        assertEquals(Status.INATIVO, existente.getStatus());
        verify(usuarioRepository, times(1)).save(existente);
    }
}
