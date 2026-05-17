package br.com.fiscalmove.nucleo.login;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UsuarioTest {

    @Test
    void usaNomeComoRepresentacaoTextualQuandoDisponivel() {
        Usuario usuario = new Usuario(1, "Administrador", "admin");

        assertEquals("Administrador", usuario.toString());
    }

    @Test
    void usaLoginComoFallbackQuandoNomeNaoExiste() {
        Usuario usuario = new Usuario(1, null, "admin");

        assertEquals("admin", usuario.toString());
    }
}
