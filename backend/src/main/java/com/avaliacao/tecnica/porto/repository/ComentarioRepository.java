package com.avaliacao.tecnica.porto.repository;

import com.avaliacao.tecnica.porto.model.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

    List<Comentario> findByLivroId(Long livroId);

    List<Comentario> findByLivroIdAndUsuarioId(Long livroId, Long usuarioId);
}
