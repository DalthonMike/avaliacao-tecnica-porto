package com.avaliacao.tecnica.porto.controller;

import com.avaliacao.tecnica.porto.dto.request.ComentarioRequest;
import com.avaliacao.tecnica.porto.mapper.comentario.ComentarioMapper;
import com.avaliacao.tecnica.porto.model.Comentario;
import com.avaliacao.tecnica.porto.service.ComentarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class ComentarioControllerTest {

    @Mock
    private ComentarioService comentarioService;

    @Mock
    private ComentarioMapper comentarioMapper;

    @InjectMocks
    private ComentarioController comentarioController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCadastrar() {
        ComentarioRequest request = ComentarioRequest.builder()
                .usuarioId(1L)
                .livroId(2L)
                .mensagem("Ótimo livro!")
                .spoiler(false)
                .build();

        Comentario comentarioMock = Comentario.builder().build();
        when(comentarioService.cadastrar(request)).thenReturn(comentarioMock);

        var response = comentarioController.cadastrar(request);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        verify(comentarioService, times(1)).cadastrar(request);
    }
}
