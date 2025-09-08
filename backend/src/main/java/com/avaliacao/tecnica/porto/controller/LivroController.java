package com.avaliacao.tecnica.porto.controller;

import com.avaliacao.tecnica.porto.controller.IController.ILivroController;
import com.avaliacao.tecnica.porto.dto.request.LivroRequest;
import com.avaliacao.tecnica.porto.dto.response.ComentarioResponse;
import com.avaliacao.tecnica.porto.dto.response.LivroResponse;
import com.avaliacao.tecnica.porto.mapper.comentario.ComentarioMapper;
import com.avaliacao.tecnica.porto.mapper.livro.LivroMapper;
import com.avaliacao.tecnica.porto.model.Comentario;
import com.avaliacao.tecnica.porto.model.Livro;
import com.avaliacao.tecnica.porto.service.ComentarioService;
import com.avaliacao.tecnica.porto.service.LivroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/livro")
public class LivroController implements ILivroController {

    private final LivroService livroService;
    private final LivroMapper livroMapper;
    private final ComentarioService comentarioService;
    private final ComentarioMapper comentarioMapper;

    public LivroController(LivroService livroService, LivroMapper livroMapper, ComentarioService comentarioService, ComentarioMapper comentarioMapper) {
        this.livroService = livroService;
        this.livroMapper = livroMapper;
        this.comentarioService = comentarioService;
        this.comentarioMapper = comentarioMapper;
    }

    @Override
    public ResponseEntity<List<LivroResponse>> buscarTodos() {
        List<Livro> livros = livroService.buscarTodosAtivos();

        List<LivroResponse> responseList = livros.stream()
                .map(livroMapper::toResponse)
                .toList();

        return ResponseEntity.ok(responseList);
    }

    @Override
    public ResponseEntity<LivroResponse> buscarPorId(Long id) {
        return ResponseEntity.ok(livroMapper.toResponse(livroService.buscarPorId(id)));
    }

    @Override
    public ResponseEntity<LivroResponse> cadastrar(LivroRequest request) {
        Livro livroCadastrado = livroService.cadastrar(livroMapper.toEntity(request));
        return ResponseEntity.ok(livroMapper.toResponse(livroCadastrado));
    }

    @Override
    public ResponseEntity<LivroResponse> atualizar(LivroRequest request, Long id) {
        Livro atualizado = livroService.atualizar(request, id);
        return ResponseEntity.ok(livroMapper.toResponse(atualizado));
    }

    @Override
    public ResponseEntity<?> excluir(Long id) {
        livroService.excluirLogicamente(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<ComentarioResponse>> listarComentariosPorLivro(@PathVariable("livroId") Long livroId, @RequestParam("usuarioId") Long usuarioId) {
        List<ComentarioResponse> comentarios = comentarioService.listarComentariosPorLivro(livroId, usuarioId);

        return ResponseEntity.ok(comentarios);
    }

    @Override
    public ResponseEntity<List<ComentarioResponse>> listarTodosPorLivro(@RequestParam("livroId") Long livroId) {
        List<Comentario> comentarios = comentarioService.listarTodosPorLivro(livroId);
        List<ComentarioResponse> comentariosResponse = comentarios.stream()
                .map(comentarioMapper::toResponse)
                .toList();

        return ResponseEntity.ok(comentariosResponse);
    }

}
