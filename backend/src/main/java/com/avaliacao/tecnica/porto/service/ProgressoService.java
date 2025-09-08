package com.avaliacao.tecnica.porto.service;

import com.avaliacao.tecnica.porto.dto.request.ProgressoRequest;
import com.avaliacao.tecnica.porto.exception.NegocioException;
import com.avaliacao.tecnica.porto.model.ProgressoLeitura;
import com.avaliacao.tecnica.porto.repository.LivroRepository;
import com.avaliacao.tecnica.porto.repository.ProgressoRepository;
import com.avaliacao.tecnica.porto.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ProgressoService {

    private final ProgressoRepository progressoRepository;
    private final UsuarioRepository usuarioRepository;
    private final LivroRepository livroRepository;

    public ProgressoService(ProgressoRepository progressoRepository, UsuarioRepository usuarioRepository, LivroRepository livroRepository) {
        this.progressoRepository = progressoRepository;
        this.usuarioRepository = usuarioRepository;
        this.livroRepository = livroRepository;
    }

    public ProgressoLeitura buscarPorId(Long id) {
        return progressoRepository.findById(id).orElseThrow(() -> new NegocioException("Não existe um progresso com o id: " + id + "informado"));
    }

    public ProgressoLeitura salvarOuAtualizar(ProgressoRequest request) {
        Optional<ProgressoLeitura> progressoExistenteOpt =
                Optional.ofNullable(progressoRepository.findByUsuarioIdAndLivroId(request.getUsuarioId(), request.getLivroId()));

        if (progressoExistenteOpt.isPresent()) {
            // Atualiza progresso existente
            ProgressoLeitura progressoExistente = progressoExistenteOpt.get();
            progressoExistente.setPaginaAtual(request.getPaginaAtual());
            progressoExistente.setDataRegistro(LocalDateTime.now());
            return progressoRepository.save(progressoExistente);
        } else {
            // Cria novo progresso
            ProgressoLeitura novoProgresso = ProgressoLeitura.builder()
                    .usuario(usuarioRepository.findById(request.getUsuarioId())
                            .orElseThrow(() -> new NegocioException("Usuário não encontrado")))
                    .livro(livroRepository.findById(request.getLivroId())
                            .orElseThrow(() -> new NegocioException("Livro não encontrado")))
                    .paginaAtual(request.getPaginaAtual())
                    .dataRegistro(LocalDateTime.now())
                    .build();

            return progressoRepository.save(novoProgresso);
        }
    }

}
