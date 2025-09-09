package com.avaliacao.tecnica.porto.controller;

import com.avaliacao.tecnica.porto.dto.request.UsuarioRequest;
import com.avaliacao.tecnica.porto.dto.response.UsuarioResponse;
import com.avaliacao.tecnica.porto.mapper.usuario.UsuarioMapper;
import com.avaliacao.tecnica.porto.model.Usuario;
import com.avaliacao.tecnica.porto.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private UsuarioMapper usuarioMapper;

    @InjectMocks
    private UsuarioController usuarioController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBuscarTodos() {
        Usuario u1 = new Usuario(); u1.setId(1L);
        Usuario u2 = new Usuario(); u2.setId(2L);

        UsuarioResponse r1 = UsuarioResponse.builder().id(1L).build();
        UsuarioResponse r2 = UsuarioResponse.builder().id(2L).build();

        when(usuarioService.buscarTodosAtivos()).thenReturn(List.of(u1, u2));
        when(usuarioMapper.toResponse(u1)).thenReturn(r1);
        when(usuarioMapper.toResponse(u2)).thenReturn(r2);

        var response = usuarioController.buscarTodos();

        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(usuarioService, times(1)).buscarTodosAtivos();
    }

    @Test
    void testBuscarPorId() {
        Usuario u = new Usuario(); u.setId(1L);
        UsuarioResponse r = UsuarioResponse.builder().id(1L).build();

        when(usuarioService.buscarPorId(1L)).thenReturn(u);
        when(usuarioMapper.toResponse(u)).thenReturn(r);

        var response = usuarioController.buscarPorId(1L);

        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void testCadastrar() {
        UsuarioRequest request = UsuarioRequest.builder().nome("João").email("joao@email.com").verSpoilers(true).build();
        Usuario entidade = new Usuario();
        Usuario salvo = new Usuario();
        UsuarioResponse r = UsuarioResponse.builder().nome("João").build();

        when(usuarioMapper.toEntity(request)).thenReturn(entidade);
        when(usuarioService.cadastrar(entidade)).thenReturn(salvo);
        when(usuarioMapper.toResponse(salvo)).thenReturn(r);

        var response = usuarioController.cadastrar(request);

        assertNotNull(response.getBody());
        assertEquals("João", response.getBody().getNome());
    }

    @Test
    void testAtualizar() {
        UsuarioRequest request = UsuarioRequest.builder().nome("Maria").email("maria@email.com").verSpoilers(false).build();
        Usuario atualizado = new Usuario();
        UsuarioResponse r = UsuarioResponse.builder().nome("Maria").build();

        when(usuarioService.atualizar(request, 1L)).thenReturn(atualizado);
        when(usuarioMapper.toResponse(atualizado)).thenReturn(r);

        var response = usuarioController.atualizar(request, 1L);

        assertNotNull(response.getBody());
        assertEquals("Maria", response.getBody().getNome());
    }

    @Test
    void testExcluir() {
        doNothing().when(usuarioService).excluirLogicamente(1L);

        var response = usuarioController.excluir(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(usuarioService, times(1)).excluirLogicamente(1L);
    }
}
