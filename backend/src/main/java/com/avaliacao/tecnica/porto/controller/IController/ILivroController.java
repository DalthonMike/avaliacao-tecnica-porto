package com.avaliacao.tecnica.porto.controller.IController;

import com.avaliacao.tecnica.porto.dto.request.LivroRequest;
import com.avaliacao.tecnica.porto.dto.response.ComentarioResponse;
import com.avaliacao.tecnica.porto.dto.response.LivroResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface ILivroController {

    @GetMapping("/todos")
    ResponseEntity<List<LivroResponse>> buscarTodos();

    @GetMapping("/{id}")
    ResponseEntity<LivroResponse> buscarPorId(@PathVariable Long id);

    @PostMapping
    ResponseEntity<LivroResponse> cadastrar(@Valid @RequestBody LivroRequest request);

    @PutMapping("/{id}")
    ResponseEntity<LivroResponse> atualizar(@Valid @RequestBody LivroRequest request, @NotNull @PathVariable Long id);

    @DeleteMapping("/{id}")
    ResponseEntity<?> excluir(@PathVariable Long id);

    @GetMapping("/{livroId}/comentarios")
    ResponseEntity<List<ComentarioResponse>> listarComentariosPorLivro(@PathVariable Long livroId, @RequestParam Long usuarioId);

    @GetMapping()
    ResponseEntity<List<ComentarioResponse>> listarTodosPorLivro(@RequestParam("livroId") Long livroId);
}
