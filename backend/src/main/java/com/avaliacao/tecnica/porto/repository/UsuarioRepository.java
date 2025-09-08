package com.avaliacao.tecnica.porto.repository;

import com.avaliacao.tecnica.porto.enums.Status;
import com.avaliacao.tecnica.porto.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    List<Usuario> findAllByStatus(Status status);

    Usuario findByIdAndStatus(Long id, Status status);

    Usuario findByEmail(String email);
}
