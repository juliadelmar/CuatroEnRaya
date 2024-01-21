package org.iesalandalus.programacion.cuatroenraya.vista;

import org.iesalandalus.programacion.cuatroenraya.modelo.Ficha;
import org.iesalandalus.programacion.cuatroenraya.modelo.Jugador;
import org.iesalandalus.programacion.cuatroenraya.modelo.Tablero;
import org.iesalandalus.programacion.utilidades.Entrada;

public class Consola {
    private Consola(){

    }
    public static String leerNombre(){
        String nombre;
        do {
            System.out.print("Introduce el nombre del jugador: ");
            nombre = Entrada.cadena();
        } while (nombre.isBlank());
        return nombre;
    }
    public static Ficha elegirColorFicha(){
        Ficha fichaElegida;
        int opcion;

        do {
            System.out.print("Elige el color de tus fichas (0-AZUL, 1-VERDE):");
            opcion = Entrada.entero();
            if (opcion == 0) {
                fichaElegida = Ficha.AZUL;
            } else {
                fichaElegida = Ficha.VERDE;
            }
        } while (opcion < 0 || opcion > 1);
        return fichaElegida;
    }
    public static Jugador leerJugador() {
        return new Jugador(leerNombre(), elegirColorFicha());
    }
    public static Jugador leerJugador(Ficha ficha) {
        return new Jugador(leerNombre(), ficha);
    }

    public static int leerColumna(Jugador jugador) {
        int columna;
        do {
            System.out.print(jugador.nombre() + ", " + "introduce la columna en la que quieres introducir la ficha (0 - 6): ");
            columna = Entrada.entero();
        } while (columna < 0 || columna > Tablero.COLUMNAS -1);
        return columna;
    }

}
