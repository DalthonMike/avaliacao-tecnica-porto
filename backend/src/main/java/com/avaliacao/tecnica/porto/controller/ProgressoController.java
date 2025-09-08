package com.avaliacao.tecnica.porto.controller;

import com.avaliacao.tecnica.porto.controller.IController.IProgressoController;
import com.avaliacao.tecnica.porto.dto.request.ProgressoRequest;
import com.avaliacao.tecnica.porto.dto.response.ProgressoLeituraResponse;
import com.avaliacao.tecnica.porto.mapper.progresso.ProgressoMapper;
import com.avaliacao.tecnica.porto.model.ProgressoLeitura;
import com.avaliacao.tecnica.porto.service.ProgressoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/progresso-leitura")
public class ProgressoController implements IProgressoController {

    private final ProgressoService progressoService;
    private final ProgressoMapper progressoMapper;

    public ProgressoController(ProgressoService progressoService, ProgressoMapper progressoMapper) {
        this.progressoService = progressoService;
        this.progressoMapper = progressoMapper;
    }

    @Override
    public ResponseEntity<ProgressoLeituraResponse> atualizar(@Valid @RequestBody ProgressoRequest request) {
        ProgressoLeitura atualizado = progressoService.salvarOuAtualizar(request);
        return ResponseEntity.ok(progressoMapper.toResponse(atualizado));
    }

}
