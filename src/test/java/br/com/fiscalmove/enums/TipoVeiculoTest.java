package br.com.fiscalmove.enums;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TipoVeiculoTest {

    @Test
    void respeitaFaixaDeCapacidadeDoTipo() {
        assertTrue(TipoVeiculo.CARRETA.permiteCapacidade(new BigDecimal("14000")));
        assertTrue(TipoVeiculo.CARRETA.permiteCapacidade(new BigDecimal("30000")));
        assertFalse(TipoVeiculo.CARRETA.permiteCapacidade(new BigDecimal("13999.99")));
        assertFalse(TipoVeiculo.CARRETA.permiteCapacidade(new BigDecimal("30000.01")));
    }
}
