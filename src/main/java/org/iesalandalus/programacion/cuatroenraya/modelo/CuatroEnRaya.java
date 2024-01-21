package org.iesalandalus.programacion.cuatroenraya.modelo;

import org.iesalandalus.programacion.cuatroenraya.vista.Consola;

import javax.naming.OperationNotSupportedException;
import java.util.Arrays;


public class CuatroEnRaya {

    private Tablero tabler;
    private static final int NUMERO_JUGADORES = 2;
    private final Jugador[] jugadores;
    private final Tablero tablero;

    public CuatroEnRaya(Jugador jugador1, Jugador jugador2) {
        jugadores = new Jugador[NUMERO_JUGADORES];
        tablero = new Tablero();
        try {
            jugadores[NUMERO_JUGADORES - 2] = jugador1;
            jugadores[NUMERO_JUGADORES - 1] = jugador2;
        } catch (NullPointerException | IllegalArgumentException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private boolean tirar(Jugador jugador) {


        boolean esJugadaGanadora = false;
        boolean esColumnaValida = true; // Siendo una columna válida
        do {
            int columna = Consola.leerColumna(jugador);
            try {
                if (tablero.introducirFicha(columna, jugador.colorFichas())) {
                    esJugadaGanadora = true;

                }
            } catch (OperationNotSupportedException e) {

                System.out.println("ERROR: " + e.getMessage());
                esColumnaValida = false;
            }
        } while (!esColumnaValida);
        return esJugadaGanadora;
    }

    public void jugar() {
        boolean jugadaGanadora = false;
        do {
                for (Jugador player : jugadores) {
                jugadaGanadora = tirar(player);
                System.out.println(tablero.toString());
                }
        } while (!tablero.estaLleno() && !jugadaGanadora );

    }
}
