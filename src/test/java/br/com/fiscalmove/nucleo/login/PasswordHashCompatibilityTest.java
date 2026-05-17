package br.com.fiscalmove.nucleo.login;

import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PasswordHashCompatibilityTest {

    @Test
    void validaHashesDeDemonstracaoDoSeed() {
        assertTrue(BCrypt.checkpw(
            "admin123",
            "$2a$10$nDS/EkljmhtGGzU6cup47ummGbDfpsM4OKnpHrVPa4XvaRlx8/D3."
        ));
        assertTrue(BCrypt.checkpw(
            "operador123",
            "$2a$10$YvjdL/XH92abBRzXJJLsKO8aLbbwcsDgPDlaTgLrF5F4UhcFbKEQ."
        ));
    }
}
