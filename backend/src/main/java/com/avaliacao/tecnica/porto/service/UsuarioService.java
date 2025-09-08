package com.avaliacao.tecnica.porto.service;

import com.avaliacao.tecnica.porto.dto.request.UsuarioRequest;
import com.avaliacao.tecnica.porto.enums.Status;
import com.avaliacao.tecnica.porto.exception.NegocioException;
import com.avaliacao.tecnica.porto.model.Usuario;
import com.avaliacao.tecnica.porto.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> buscarTodosAtivos() {
        return usuarioRepository.findAllByStatus(Status.ATIVO);
    }

    public Usuario buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findByIdAndStatus(id, Status.ATIVO);
        if (Objects.isNull(usuario)) {
            throw new NegocioException("Não foi possível encontrar um usuario com o identificador " + id);
        }
        return usuario;
    }

    public Usuario cadastrar(Usuario usuario) {
        validarEmailUnico(usuario.getEmail());
        usuario.setStatus(Status.ATIVO);
        return usuarioRepository.save(usuario);
    }

    public Usuario atualizar(UsuarioRequest request, Long id) {
        Usuario usuarioExistente = buscarPorId(id);
        if (!usuarioExistente.getEmail().equals(request.getEmail())) {
            validarEmailUnico(request.getEmail());
        }
        Usuario usuarioAtualizado = builderUsuario(usuarioExistente, request);
        return usuarioRepository.save(usuarioAtualizado);
    }

    public void excluirLogicamente(Long id) {
        Usuario usuario = buscarPorId(id);
        usuario.setStatus(Status.INATIVO);
        usuarioRepository.save(usuario);
    }

    private Usuario builderUsuario(Usuario usuarioExistente, UsuarioRequest request) {
        usuarioExistente.setNome(request.getNome());
        usuarioExistente.setEmail(request.getEmail());
        usuarioExistente.setVerSpoilers(request.isVerSpoilers());

        return usuarioExistente;
    }

    private void validarEmailUnico(String email) {
        Usuario existe = usuarioRepository.findByEmail(email);
        if (Objects.nonNull(existe)) {
            throw new NegocioException("Já existe um usuário com o e-mail informado: " + email);
        }
    }
}
