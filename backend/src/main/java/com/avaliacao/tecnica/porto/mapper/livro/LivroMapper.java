package com.avaliacao.tecnica.porto.mapper.livro;

import com.avaliacao.tecnica.porto.dto.request.LivroRequest;
import com.avaliacao.tecnica.porto.dto.response.ComentarioResponse;
import com.avaliacao.tecnica.porto.dto.response.LivroResponse;
import com.avaliacao.tecnica.porto.dto.response.ProgressoLeituraResponse;
import com.avaliacao.tecnica.porto.model.Comentario;
import com.avaliacao.tecnica.porto.model.Livro;
import com.avaliacao.tecnica.porto.model.ProgressoLeitura;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LivroMapper {

    @Mapping(target = "comentarios", ignore = true)
    @Mapping(target = "progressos", ignore = true)
    @Mapping(target = "status", ignore = true)
    Livro toEntity(LivroRequest request);

    @Mapping(target = "comentarios", source = "comentarios")
    @Mapping(target = "progressos", source = "progressos")
    LivroResponse toResponse(Livro livro);

    // MapStruct vai usar automaticamente esses métodos para converter listas
    default List<ProgressoLeituraResponse> mapProgressos(List<ProgressoLeitura> progressos) {
        if (progressos == null) return null;
        return progressos.stream()
                .map(p -> ProgressoLeituraResponse.builder()
                        .id(p.getId())
                        .usuario(p.getUsuario() != null ? p.getUsuario().getNome() : null)
                        .livro(p.getLivro() != null ? p.getLivro().getTitulo() : null)
                        .paginaAtual(p.getPaginaAtual())
                        .dataRegistro(p.getDataRegistro())
                        .build())
                .collect(Collectors.toList());
    }

    default List<ComentarioResponse> mapComentarios(List<Comentario> comentarios) {
        if (comentarios == null) return null;
        return comentarios.stream()
                .map(c -> ComentarioResponse.builder()
                        .id(c.getId())
                        .usuario(c.getUsuario() != null ? c.getUsuario().getNome() : null)
                        .mensagem(c.getMensagem())
                        .dataComentario(c.getDataComentario())
                        .build())
                .collect(Collectors.toList());
    }
}