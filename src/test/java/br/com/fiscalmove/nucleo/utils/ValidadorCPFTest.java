package br.com.fiscalmove.nucleo.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidadorCPFTest {

    @Test
    void aceitaCpfValidoComOuSemMascara() {
        assertTrue(ValidadorCPF.isValido("52998224725"));
        assertTrue(ValidadorCPF.isValido("529.982.247-25"));
    }

    @Test
    void rejeitaCpfComDigitosInvalidosOuRepetidos() {
        assertFalse(ValidadorCPF.isValido("52998224724"));
        assertFalse(ValidadorCPF.isValido("11111111111"));
    }
}
