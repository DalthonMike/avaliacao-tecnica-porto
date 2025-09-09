package com.avaliacao.tecnica.porto.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleNegocioException_ShouldReturnNotFoundResponse() {
        String errorMessage = "Recurso não encontrado";
        NegocioException ex = new NegocioException(errorMessage);

        ResponseEntity<Map<String, Object>> response = handler.handleNegocioException(ex);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(HttpStatus.NOT_FOUND.value(), body.get("status"));
        assertEquals("Not Found", body.get("error"));
        assertEquals(errorMessage, body.get("message"));
        assertTrue(body.containsKey("timestamp"));
    }
}
