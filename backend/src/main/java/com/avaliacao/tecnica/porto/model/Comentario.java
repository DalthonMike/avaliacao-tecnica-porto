package com.avaliacao.tecnica.porto.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import lombok.*;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "TBL_COMENTARIO")
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMENTARIO_SEQ")
    @SequenceGenerator(name = "COMENTARIO_SEQ", sequenceName = "COMENTARIO_SEQ", initialValue = 1, allocationSize = 1)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "LIVRO_ID", nullable = false)
    private Livro livro;

    @ManyToOne(optional = false)
    @JoinColumn(name = "USUARIO_ID", nullable = false)
    private Usuario usuario;

    @Column(nullable = false, length = 1000)
    private String mensagem;

    @Column(nullable = false)
    private LocalDateTime dataComentario;

    @Column(nullable = false)
    private boolean spoiler;
}
