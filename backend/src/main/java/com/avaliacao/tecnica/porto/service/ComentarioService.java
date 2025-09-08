package com.avaliacao.tecnica.porto.service;

import com.avaliacao.tecnica.porto.dto.request.ComentarioRequest;
import com.avaliacao.tecnica.porto.dto.response.ComentarioResponse;
import com.avaliacao.tecnica.porto.exception.NegocioException;
import com.avaliacao.tecnica.porto.model.Comentario;
import com.avaliacao.tecnica.porto.model.Livro;
import com.avaliacao.tecnica.porto.model.Usuario;
import com.avaliacao.tecnica.porto.repository.ComentarioRepository;
import com.avaliacao.tecnica.porto.repository.LivroRepository;
import com.avaliacao.tecnica.porto.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final UsuarioRepository usuarioRepository;
    private final LivroRepository livroRepository;

    public ComentarioService(ComentarioRepository comentarioRepository,
                             UsuarioRepository usuarioRepository,
                             LivroRepository livroRepository) {
        this.comentarioRepository = comentarioRepository;
        this.usuarioRepository = usuarioRepository;
        this.livroRepository = livroRepository;
    }

    public Comentario cadastrar(ComentarioRequest request) {
        // Verifica se usuário existe
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new NegocioException("Usuário não encontrado com id " + request.getUsuarioId()));

        // Verifica se livro existe
        Livro livro = livroRepository.findById(request.getLivroId())
                .orElseThrow(() -> new NegocioException("Livro não encontrado com id " + request.getLivroId()));

        Comentario comentario = Comentario.builder()
                .usuario(usuario)
                .livro(livro)
                .mensagem(request.getMensagem())
                .dataComentario(LocalDateTime.now())
                .spoiler(request.isSpoiler())
                .build();

        return comentarioRepository.save(comentario);
    }

    public List<ComentarioResponse> listarComentariosPorLivro(Long livroId, Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new NegocioException("Usuário não encontrado"));

        List<Comentario> comentarios = comentarioRepository.findByLivroId(livroId);

        if (!usuario.isVerSpoilers()) {
            comentarios = comentarios.stream()
                    .filter(c -> !c.isSpoiler())
                    .toList();
        }

        return comentarios.stream()
                .map(c -> ComentarioResponse.builder()
                        .id(c.getId())
                        .usuario(c.getUsuario().getNome())
                        .livro(c.getLivro().getTitulo())
                        .mensagem(c.getMensagem())
                        .dataComentario(c.getDataComentario())
                        .spoiler(c.isSpoiler())
                        .build())
                .collect(Collectors.toList());
    }

    public List<Comentario> listarTodosPorLivro(Long livroId) {
        return comentarioRepository.findByLivroId(livroId);
    }
}