package com.avaliacao.tecnica.porto.enums;

public enum Status {

    ATIVO("ATIVO", "Ativo"),
    INATIVO("INATIVO", "Inativo");

    private String codigo;
    private String descricao;

    Status(String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }
}
