package com.avaliacao.tecnica.porto.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatusTest {

    @Test
    void testEnumValues() {
        Status ativo = Status.ATIVO;
        Status inativo = Status.INATIVO;

        assertNotNull(ativo);
        assertNotNull(inativo);

        assertEquals("ATIVO", ativo.getCodigo());
        assertEquals("INATIVO", inativo.getCodigo());

        assertEquals("Ativo", ativo.getDescricao());
        assertEquals("Inativo", inativo.getDescricao());
    }

    @Test
    void testEnumValuesArray() {
        Status[] values = Status.values();
        assertArrayEquals(new Status[]{Status.ATIVO, Status.INATIVO}, values);
    }
}
