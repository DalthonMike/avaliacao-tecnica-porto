package com.avaliacao.tecnica.porto.controller;

import com.avaliacao.tecnica.porto.controller.IController.IComentarioController;
import com.avaliacao.tecnica.porto.dto.request.ComentarioRequest;
import com.avaliacao.tecnica.porto.mapper.comentario.ComentarioMapper;
import com.avaliacao.tecnica.porto.service.ComentarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comentario-leitura")
public class ComentarioController implements IComentarioController {

    private final ComentarioService comentarioService;
    private final ComentarioMapper comentarioMapper;

    public ComentarioController(ComentarioService comentarioService, ComentarioMapper comentarioMapper) {
        this.comentarioService = comentarioService;
        this.comentarioMapper = comentarioMapper;
    }

    @Override
    public ResponseEntity<ComentarioRequest> cadastrar(@Valid @RequestBody ComentarioRequest request) {
        comentarioService.cadastrar(request);
        return ResponseEntity.ok().build();
    }

}
