package org.iesalandalus.programacion.cuatroenraya.modelo;

import javax.naming.OperationNotSupportedException;
import java.util.Objects;


public class Tablero {
    public static final int FILAS = 6;
    public static final int COLUMNAS = 7;
    public static final int FICHAS_IGUALES_CONSECUTIVAS_NECESARIAS = 4;
    private final Casilla[][] casillas;

    public Tablero(){
        casillas = new Casilla[FILAS][COLUMNAS];
        for (int i = 0; i < FILAS; i++){
            for (int j = 0; j < COLUMNAS; j++){
                casillas[i][j] = new Casilla();
            }
        }
    }

    private boolean columnaVacia(int columna){
        return casillas[FILAS - 1][columna].getFicha() == null;
    }

    public boolean estaVacio(){
        boolean vacio = true;
        for (int i = 0; i < COLUMNAS && vacio; i++) {
            vacio = columnaVacia(i);
        }
        return vacio;
    }

    private boolean columnaLlena(int columna){
        return casillas[0][columna].getFicha() != null;
    }

    public boolean estaLleno(){
        boolean lleno = true;
        for (int i = 0; i < COLUMNAS && lleno; i++) {
            lleno = columnaLlena(i);
        }
        return lleno;
    }

    private void comprobarFicha(Ficha ficha){
        Objects.requireNonNull(ficha, "La ficha no puede ser nula.");
    }

    private void comprobarColumna(int columna){
        if (columna >= COLUMNAS || columna < 0){
            throw new IllegalArgumentException("Columna incorrecta.");
        }
    }

    private int getPrimeraFilaVacia(int columna) throws OperationNotSupportedException {
        if (columnaLlena(columna)){
            throw new OperationNotSupportedException("Columna llena.");
        }
        int nFila = -1 ;
        for (int i = FILAS - 1; i >= 0  && nFila == -1; i--){
            if (casillas[i][columna].getFicha() == null){
                nFila = i;
            }
        }
        return nFila;
    }

    private boolean objetivoAlcanzado(int fichasIgualesConsecutivas){
        return fichasIgualesConsecutivas >= FICHAS_IGUALES_CONSECUTIVAS_NECESARIAS;
    }

    private boolean comprobarHorizontal(int fila, Ficha ficha){
        int contadorFichasIguales = 0;
        for (int i = 0; i < COLUMNAS && !objetivoAlcanzado(contadorFichasIguales); i++){
            if (casillas[fila][i].estaOcupada() && casillas[fila][i].getFicha().equals(ficha)) {
                contadorFichasIguales++;
            } else {
                contadorFichasIguales = 0;
            }
        }
        return (objetivoAlcanzado(contadorFichasIguales));
    }

    private boolean comprobarVertical(int columna, Ficha ficha){
        int contadorFichasIguales = 0;
        for (int i = 0; i < FILAS && !objetivoAlcanzado(contadorFichasIguales); i++){
            if (casillas[i][columna].estaOcupada() && casillas[i][columna].getFicha().equals(ficha)) {
                contadorFichasIguales++;
            } else {
                contadorFichasIguales = 0;
            }
        }
        return (objetivoAlcanzado(contadorFichasIguales));
    }
    private int menor(int fila, int columna){
        return Math.min(fila, columna);
    }

    private boolean comprobarDiagonalNE(int filaSemilla, int columnaSemilla, Ficha ficha) {
        int desplazamiento = menor(filaSemilla, columnaSemilla);
        int filaInicial = filaSemilla - desplazamiento;
        int columnaInicial = columnaSemilla - desplazamiento;
        int contador = 0;
        boolean esGanador = false;

        for (int i = filaInicial, j = columnaInicial; i < FILAS && j < COLUMNAS && !esGanador; i++, j++) {
            if (casillas[i][j].estaOcupada() && casillas[i][j].getFicha().equals(ficha)) {
                contador++;
            } else {
                contador = 0;
            }
            if (objetivoAlcanzado(contador)) {
                esGanador = true;
            }
        }
        return esGanador;
    }
    private boolean comprobarDiagonalNO(int filaSemilla, int columnaSemilla, Ficha ficha) {
        int desplazamiento = menor(filaSemilla, (COLUMNAS - 1) - columnaSemilla);
        int filaInicial = filaSemilla + desplazamiento;
        int columnaInicial = columnaSemilla + desplazamiento;
        int contador = 0;
        boolean esGanador = false;
        for (int i = filaInicial, j = columnaInicial; i < FILAS && j >= 0 && !esGanador; i++, j--) {
            if (casillas[i][j].estaOcupada() && casillas[i][j].getFicha().equals(ficha)) {
                contador++;
            } else {
                contador = 0;
            }
            if (objetivoAlcanzado(contador)) {
                esGanador = true;
            }
        }
        return esGanador;
    }

    private boolean comprobarTirada(int fila, int columna, Ficha ficha){
        return (comprobarHorizontal(fila, ficha) || comprobarVertical(columna, ficha) || comprobarDiagonalNE(fila, columna, ficha) || comprobarDiagonalNO(fila, columna, ficha));
    }

    public boolean introducirFicha(int columna, Ficha ficha) throws OperationNotSupportedException {

        if (estaLleno()) {
            throw new IllegalArgumentException("No se pueden introducir más fichas porque el tablero está lleno.");
        }
        comprobarColumna(columna);
        comprobarFicha(ficha);

        int fila = getPrimeraFilaVacia(columna);
        casillas[fila][columna].setFicha(ficha);
        return comprobarTirada(fila, columna, ficha);
    }


    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();

        for (int i =  0; i <= FILAS -1; i++) {
            stringBuilder.append('|');
            for (int j = 0; j < COLUMNAS; j++) {
                if (casillas[i][j].estaOcupada()) {
                    stringBuilder.append(casillas[i][j].getFicha());
                } else {
                    stringBuilder.append(' ');
                }
            }
            stringBuilder.append('|').append(System.lineSeparator());
        }

        for (int j = 0; j < COLUMNAS; j++) {
            if (j == 0) {
                stringBuilder.append(' ');
            }
            stringBuilder.append('-');
        }
        stringBuilder.append(System.lineSeparator());
        return stringBuilder.toString().replace("\r\n", "\n");
    }
}
