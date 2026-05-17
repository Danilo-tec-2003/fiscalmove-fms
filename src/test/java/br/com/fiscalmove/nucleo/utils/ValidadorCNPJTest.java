package br.com.fiscalmove.nucleo.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidadorCNPJTest {

    @Test
    void aceitaCnpjValidoComOuSemMascara() {
        assertTrue(ValidadorCNPJ.isValido("11222333000181"));
        assertTrue(ValidadorCNPJ.isValido("11.222.333/0001-81"));
    }

    @Test
    void rejeitaCnpjComDigitosInvalidosOuRepetidos() {
        assertFalse(ValidadorCNPJ.isValido("11222333000180"));
        assertFalse(ValidadorCNPJ.isValido("00000000000000"));
    }
}
