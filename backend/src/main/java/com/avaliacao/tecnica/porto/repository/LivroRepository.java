package com.avaliacao.tecnica.porto.repository;

import com.avaliacao.tecnica.porto.enums.Status;
import com.avaliacao.tecnica.porto.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

    List<Livro> findAllByStatus(Status status);

    Livro findByIdAndStatus(Long id, Status status);
}
