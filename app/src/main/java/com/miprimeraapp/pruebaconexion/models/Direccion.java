package com.miprimeraapp.pruebaconexion.models;

public class Direccion {
    private long id_cancha;
    private String tipo;
    private int num_tipo;
    private int num_calle;
    private int num_casa;
    private String orientacion;
    private String barrio;
    private String localidad;

    public Direccion(String tipo, int num_tipo, int num_calle, int num_casa, String orientacion, String barrio, String localidad) {
        this.tipo = tipo;
        this.num_tipo = num_tipo;
        this.num_calle = num_calle;
        this.num_casa = num_casa;
        this.orientacion = orientacion;
        this.barrio = barrio;
        this.localidad = localidad;
    }

    public long getId_cancha() {
        return id_cancha;
    }

    public void setId_cancha(long id_cancha) {
        this.id_cancha = id_cancha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getNum_tipo() {
        return num_tipo;
    }

    public void setNum_tipo(int num_tipo) {
        this.num_tipo = num_tipo;
    }

    public int getNum_calle() {
        return num_calle;
    }

    public void setNum_calle(int num_calle) {
        this.num_calle = num_calle;
    }

    public int getNum_casa() {
        return num_casa;
    }

    public void setNum_casa(int num_casa) {
        this.num_casa = num_casa;
    }

    public String getOrientacion() {
        return orientacion;
    }

    public void setOrientacion(String orientacion) {
        this.orientacion = orientacion;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    @Override
    public String toString() {
        return "Direccion{" +
                "id_cancha=" + id_cancha +
                ", tipo='" + tipo + '\'' +
                ", num_tipo=" + num_tipo +
                ", num_calle=" + num_calle +
                ", num_casa=" + num_casa +
                ", orientacion='" + orientacion + '\'' +
                ", barrio='" + barrio + '\'' +
                ", localidad='" + localidad + '\'' +
                '}';
    }
}
