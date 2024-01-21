package org.iesalandalus.programacion.cuatroenraya.modelo;

import java.util.Objects;

public record Jugador(String nombre, Ficha colorFichas) {
    public Jugador {
        validarNombre(nombre);
        validarColor(colorFichas);
    }
    public void validarNombre(String nombre){Objects.requireNonNull(nombre,"El nombre no puede ser nulo.");
       if (nombre.isBlank()){
           throw new IllegalArgumentException("El nombre no puede estar en blanco.");
       }
    }
    public void validarColor(Ficha colorFicha){
        Objects.requireNonNull(colorFicha, "El color de las fichas no puede ser nulo.");

    }

    @Override
    public String toString() {
        return String.format("%s (%s)", this.nombre, this.colorFichas);
    }
}
