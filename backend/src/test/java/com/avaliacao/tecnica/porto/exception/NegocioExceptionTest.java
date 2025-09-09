package com.avaliacao.tecnica.porto.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NegocioExceptionTest {

    @Test
    void constructorWithMessage_ShouldStoreMessage() {
        String message = "Erro de negócio";

        NegocioException exception = new NegocioException(message);

        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void constructorWithMessageAndCause_ShouldStoreMessageAndCause() {
        String message = "Erro de negócio com causa";
        Throwable cause = new RuntimeException("Causa original");

        NegocioException exception = new NegocioException(message, cause);

        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}
