package com.avaliacao.tecnica.porto.controller.IController;

import com.avaliacao.tecnica.porto.dto.request.UsuarioRequest;
import com.avaliacao.tecnica.porto.dto.response.UsuarioResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface IUsuarioController {

    @GetMapping("/todos")
    ResponseEntity<List<UsuarioResponse>> buscarTodos();

    @GetMapping("/{id}")
    ResponseEntity<UsuarioResponse> buscarPorId(@PathVariable Long id);

    @PostMapping
    ResponseEntity<UsuarioResponse> cadastrar(@Valid @RequestBody UsuarioRequest request);

    @PutMapping("/{id}")
    ResponseEntity<UsuarioResponse> atualizar(@Valid @RequestBody UsuarioRequest request, @NotNull @PathVariable Long id);

    @DeleteMapping("/{id}")
    ResponseEntity<?> excluir(@PathVariable Long id);
}
