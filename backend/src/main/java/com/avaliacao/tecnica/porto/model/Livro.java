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
@Table(name = "TBL_LIVRO")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LIVRO_SEQ")
    @SequenceGenerator(name = "LIVRO_SEQ", sequenceName = "LIVRO_SEQ", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String autor;

    @Column(length = 2000)
    private String resumo;

    @Column(nullable = false)
    private Integer anoPublicacao;

    @Column(nullable = false, unique = true)
    private String isbn;

    @OneToMany(mappedBy = "livro")
    private List<Comentario> comentarios;

    @OneToMany(mappedBy = "livro")
    private List<ProgressoLeitura> progressos;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;
}
