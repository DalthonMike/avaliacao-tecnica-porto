package com.avaliacao.tecnica.porto.mapper.comentario;

import com.avaliacao.tecnica.porto.dto.response.ComentarioResponse;
import com.avaliacao.tecnica.porto.model.Comentario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ComentarioMapper {

    @Mapping(target = "usuario", source = "usuario.nome")
    @Mapping(target = "livro", source = "livro.titulo")
    ComentarioResponse toResponse(Comentario comentario);
}
