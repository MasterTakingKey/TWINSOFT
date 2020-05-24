package application;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PuntuacionTest {
	private Puntuacion puntuacion = new Puntuacion();

    private int puntos;

    @Test
    void testPuntosAñadidos() {
        puntos = 10;
        assertTrue(puntos == puntuacion.sumaPuntos(10, false, 1));
    }

    @Test
    void testPuntosRestados() {
        puntos = -1;
        assertTrue(puntos == puntuacion.sumaPuntos(-1, true, 0));
    }

    @Test
    void testPuntosRestadosRepetido2() {
        puntos = -2;
        assertTrue(puntos == puntuacion.sumaPuntos(-1, true, 1));
    }

    @Test
    void testPuntosRestadosRepetido5() {
        puntos = -6;
        assertTrue(puntos == puntuacion.sumaPuntos(-1, true, 5));
    }

    @Test
    void testBonificacionVictoria() {
        int tiempoRestante = 20;
        int numeroParejas = 8;
        puntos = (int) (tiempoRestante*0.5) + numeroParejas;
        assertTrue(puntos == puntuacion.sumarBonificacionVictoria(tiempoRestante, numeroParejas));
    }
}
