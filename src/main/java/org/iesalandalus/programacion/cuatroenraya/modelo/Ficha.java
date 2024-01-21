package org.iesalandalus.programacion.cuatroenraya.modelo;

public enum Ficha {

    VERDE("V"),
    AZUL("A");
    private final String nombre;
    Ficha(String nombre){
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return String.format(nombre );
    }

}
