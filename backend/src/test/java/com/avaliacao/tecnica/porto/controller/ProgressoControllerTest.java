package com.avaliacao.tecnica.porto.controller;

import com.avaliacao.tecnica.porto.dto.request.ProgressoRequest;
import com.avaliacao.tecnica.porto.dto.response.ProgressoLeituraResponse;
import com.avaliacao.tecnica.porto.mapper.progresso.ProgressoMapper;
import com.avaliacao.tecnica.porto.model.ProgressoLeitura;
import com.avaliacao.tecnica.porto.service.ProgressoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProgressoControllerTest {

    @Mock
    private ProgressoService progressoService;

    @Mock
    private ProgressoMapper progressoMapper;

    @InjectMocks
    private ProgressoController progressoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAtualizar() {
        ProgressoRequest request = ProgressoRequest.builder()
                .usuarioId(1L)
                .livroId(2L)
                .paginaAtual(50)
                .build();

        ProgressoLeitura progressoMock = ProgressoLeitura.builder()
                .id(1L)
                .paginaAtual(50)
                .build();

        when(progressoService.salvarOuAtualizar(request)).thenReturn(progressoMock);

        ProgressoLeituraResponse responseMock = ProgressoLeituraResponse.builder()
                .id(1L)
                .paginaAtual(50)
                .build();

        when(progressoMapper.toResponse(progressoMock)).thenReturn(responseMock);

        ResponseEntity<ProgressoLeituraResponse> response = progressoController.atualizar(request);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(50, response.getBody().getPaginaAtual());

        verify(progressoService, times(1)).salvarOuAtualizar(request);
        verify(progressoMapper, times(1)).toResponse(progressoMock);
    }
}
