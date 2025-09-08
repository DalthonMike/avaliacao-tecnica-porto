package com.avaliacao.tecnica.porto.controller.IController;

import com.avaliacao.tecnica.porto.dto.request.ProgressoRequest;
import com.avaliacao.tecnica.porto.dto.response.ProgressoLeituraResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface IProgressoController {

    @PostMapping()
    ResponseEntity<ProgressoLeituraResponse> atualizar(@Valid @RequestBody ProgressoRequest request);

}
