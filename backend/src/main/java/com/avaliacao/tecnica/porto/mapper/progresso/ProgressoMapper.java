package com.avaliacao.tecnica.porto.mapper.progresso;

import com.avaliacao.tecnica.porto.dto.request.ProgressoRequest;
import com.avaliacao.tecnica.porto.dto.response.ProgressoLeituraResponse;
import com.avaliacao.tecnica.porto.model.ProgressoLeitura;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProgressoMapper {

    ProgressoLeitura toEntity(ProgressoRequest request);

    @Mapping(target = "usuario", source = "usuario.nome")
    @Mapping(target = "livro", source = "livro.titulo")
    ProgressoLeituraResponse toResponse(ProgressoLeitura progresso);
}
