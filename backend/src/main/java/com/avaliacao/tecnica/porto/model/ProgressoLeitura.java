package com.avaliacao.tecnica.porto.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "TBL_PROGRESO_LEITURA")
public class ProgressoLeitura {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROGRESO_LEITURA_SEQ")
    @SequenceGenerator(name = "PROGRESO_LEITURA_SEQ", sequenceName = "PROGRESO_LEITURA_SEQ", initialValue = 1, allocationSize = 1)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "USUARIO_ID", nullable = false)
    private Usuario usuario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "LIVRO_ID", nullable = false)
    private Livro livro;

    @Column(nullable = false)
    private Integer paginaAtual;

    @Column(nullable = false)
    private LocalDateTime dataRegistro;
}
