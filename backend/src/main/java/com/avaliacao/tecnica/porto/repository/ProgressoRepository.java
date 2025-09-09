package com.avaliacao.tecnica.porto.repository;

import com.avaliacao.tecnica.porto.model.ProgressoLeitura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgressoRepository extends JpaRepository<ProgressoLeitura, Long> {

    ProgressoLeitura findByUsuarioIdAndLivroId(Long idUsuario, Long idLivro);
}
