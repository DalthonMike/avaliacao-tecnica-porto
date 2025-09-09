package com.avaliacao.tecnica.porto.controller.IController;

import com.avaliacao.tecnica.porto.dto.request.ComentarioRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface IComentarioController {

    @PostMapping()
    ResponseEntity<?> cadastrar(@Valid @RequestBody ComentarioRequest request);
    
}
