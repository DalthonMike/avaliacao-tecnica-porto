package com.avaliacao.tecnica.porto.service;

import com.avaliacao.tecnica.porto.dto.request.LivroRequest;
import com.avaliacao.tecnica.porto.enums.Status;
import com.avaliacao.tecnica.porto.exception.NegocioException;
import com.avaliacao.tecnica.porto.model.Livro;
import com.avaliacao.tecnica.porto.repository.LivroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class LivroService {

    private final LivroRepository livroRepository;

    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    public List<Livro> buscarTodosAtivos() {
        return livroRepository.findAllByStatus(Status.ATIVO);
    }

    public Livro buscarPorId(Long id) {
        Livro livro = livroRepository.findByIdAndStatus(id, Status.ATIVO);
        if (Objects.isNull(livro)) {
            throw new NegocioException("Não foi possível encontrar um livro com o identificador " + id);
        }
        return livro;
    }

    public Livro cadastrar(Livro livro) {
        livro.setStatus(Status.ATIVO);
        return livroRepository.save(livro);
    }

    public Livro atualizar(LivroRequest request, Long id) {
        Livro livroExistente = buscarPorId(id);
        Livro livroAtualizado = builderLivro(livroExistente, request);
        return livroRepository.save(livroAtualizado);
    }

    public void excluirLogicamente(Long id) {
        Livro livro = buscarPorId(id);
        livro.setStatus(Status.INATIVO);
        livroRepository.save(livro);
    }

    private Livro builderLivro(Livro livroExistente, LivroRequest request) {
        livroExistente.setTitulo(request.getTitulo());
        livroExistente.setAutor(request.getAutor());
        livroExistente.setResumo(request.getResumo());
        livroExistente.setAnoPublicacao(request.getAnoPublicacao());
        livroExistente.setIsbn(request.getIsbn());

        return livroExistente;
    }
}
