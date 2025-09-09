package com.avaliacao.tecnica.porto.mapper;

import com.avaliacao.tecnica.porto.dto.request.UsuarioRequest;
import com.avaliacao.tecnica.porto.dto.response.UsuarioResponse;
import com.avaliacao.tecnica.porto.mapper.usuario.UsuarioMapper;
import com.avaliacao.tecnica.porto.model.Usuario;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioMapperTest {

    private final UsuarioMapper mapper = Mappers.getMapper(UsuarioMapper.class);

    @Test
    void toEntity_ShouldMapRequestToEntity() {
        UsuarioRequest request = UsuarioRequest.builder()
                .nome("João")
                .email("joao@email.com")
                .verSpoilers(true)
                .build();

        Usuario usuario = mapper.toEntity(request);

        assertNotNull(usuario);
        assertEquals("João", usuario.getNome());
        assertEquals("joao@email.com", usuario.getEmail());
        assertTrue(usuario.isVerSpoilers());

        // Campos ignorados
        assertNull(usuario.getId());
        assertNull(usuario.getComentarios());
        assertNull(usuario.getProgressos());
        assertNull(usuario.getStatus());
    }

    @Test
    void toResponse_ShouldMapEntityToResponse() {
        Usuario usuario = Usuario.builder()
                .id(1L)
                .nome("Maria")
                .email("maria@email.com")
                .verSpoilers(false)
                .build();

        UsuarioResponse response = mapper.toResponse(usuario);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Maria", response.getNome());
        assertEquals("maria@email.com", response.getEmail());
        assertFalse(response.isVerSpoilers());
    }

    @Test
    void mapComentarios_ShouldReturnNullWhenInputIsNull() {
        assertNull(mapper.mapComentarios(null));
    }

    @Test
    void mapProgressos_ShouldReturnNullWhenInputIsNull() {
        assertNull(mapper.mapProgressos(null));
    }

    @Test
    void toResponse_ShouldHandleNullLists() {
        Usuario usuario = Usuario.builder()
                .id(2L)
                .nome("Carlos")
                .email("carlos@email.com")
                .verSpoilers(true)
                .comentarios(null)
                .progressos(null)
                .build();

        UsuarioResponse response = mapper.toResponse(usuario);

        assertNotNull(response);
        assertEquals(2L, response.getId());
        assertEquals("Carlos", response.getNome());
        assertEquals("carlos@email.com", response.getEmail());
        assertTrue(response.isVerSpoilers());
    }
}
