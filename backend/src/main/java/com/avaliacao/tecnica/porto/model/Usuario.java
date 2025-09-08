package com.avaliacao.tecnica.porto.model;

import com.avaliacao.tecnica.porto.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TBL_USUARIO")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USUARIO_SEQ")
    @SequenceGenerator(name = "USUARIO_SEQ", sequenceName = "USUARIO_SEQ", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private boolean verSpoilers;

    @OneToMany(mappedBy = "usuario")
    private List<Comentario> comentarios;

    @OneToMany(mappedBy = "usuario")
    private List<ProgressoLeitura> progressos;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;
}
