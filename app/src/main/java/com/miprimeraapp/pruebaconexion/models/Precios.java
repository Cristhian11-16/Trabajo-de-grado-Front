package com.miprimeraapp.pruebaconexion.models;

public class Precios {
    private long id_cancha;
    private String dia;
    private int hora;
    private double precio;

    public Precios( long id_cancha, String dia, int hora, double precio) {
        this.id_cancha = id_cancha;
        this.dia = dia;
        this.hora = hora;
        this.precio = precio;
    }



    public long getId_cancha() {
        return id_cancha;
    }

    public void setId_cancha(long id_cancha) {
        this.id_cancha = id_cancha;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Precios{" +
                ", id_cancha=" + id_cancha +
                ", dia='" + dia + '\'' +
                ", hora=" + hora +
                ", precio=" + precio +
                '}';
    }
}
