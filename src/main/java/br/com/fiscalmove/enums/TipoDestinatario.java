package br.com.fiscalmove.enums;

public enum TipoDestinatario {
    PESSOA_FISICA("Pessoa Física"),
    PESSOA_JURIDICA("Pessoa Jurídica");

    private final String descricao;

    TipoDestinatario(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
