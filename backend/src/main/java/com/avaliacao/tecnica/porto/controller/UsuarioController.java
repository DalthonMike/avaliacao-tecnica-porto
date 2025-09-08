package com.avaliacao.tecnica.porto.controller;

import com.avaliacao.tecnica.porto.controller.IController.IUsuarioController;
import com.avaliacao.tecnica.porto.dto.request.UsuarioRequest;
import com.avaliacao.tecnica.porto.dto.response.UsuarioResponse;
import com.avaliacao.tecnica.porto.mapper.usuario.UsuarioMapper;
import com.avaliacao.tecnica.porto.model.Usuario;
import com.avaliacao.tecnica.porto.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController implements IUsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    public UsuarioController(UsuarioService usuarioService, UsuarioMapper usuarioMapper) {
        this.usuarioService = usuarioService;
        this.usuarioMapper = usuarioMapper;
    }

    @Override
    public ResponseEntity<List<UsuarioResponse>> buscarTodos() {
        List<Usuario> usuarios = usuarioService.buscarTodosAtivos();

        List<UsuarioResponse> responseList = usuarios.stream()
                .map(usuarioMapper::toResponse)
                .toList();

        return ResponseEntity.ok(responseList);
    }

    @Override
    public ResponseEntity<UsuarioResponse> buscarPorId(Long id) {
        return ResponseEntity.ok(usuarioMapper.toResponse(usuarioService.buscarPorId(id)));
    }

    @Override
    public ResponseEntity<UsuarioResponse> cadastrar(UsuarioRequest request) {
        Usuario usuarioCadastrado = usuarioService.cadastrar(usuarioMapper.toEntity(request));
        return ResponseEntity.ok(usuarioMapper.toResponse(usuarioCadastrado));
    }

    @Override
    public ResponseEntity<UsuarioResponse> atualizar(UsuarioRequest request, Long id) {
        Usuario atualizado = usuarioService.atualizar(request, id);
        return ResponseEntity.ok(usuarioMapper.toResponse(atualizado));
    }

    @Override
    public ResponseEntity<?> excluir(Long id) {
        usuarioService.excluirLogicamente(id);
        return ResponseEntity.noContent().build();
    }

}
