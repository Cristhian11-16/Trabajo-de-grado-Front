package com.miprimeraapp.pruebaconexion.models;

import java.time.LocalDate;

public class Reserva {
    private long id_reserva;
    private long id_cancha;
    private long id_usuario;
    private String fecha;
    private int hora;
    private double precio;
    private String estado;
    private int num_cancha;

    public Reserva(long id_cancha, long id_usuario, String fecha, int hora, double precio, String estado, int num_cancha) {
        this.id_cancha = id_cancha;
        this.id_usuario = id_usuario;
        this.fecha = fecha;
        this.hora = hora;
        this.precio = precio;
        this.estado = estado;
        this.num_cancha = num_cancha;
    }

    public Reserva(long id_reserva, long id_cancha, long id_usuario, String fecha, int hora, double precio, String estado, int num_cancha) {
        this.id_reserva = id_reserva;
        this.id_cancha = id_cancha;
        this.id_usuario = id_usuario;
        this.fecha = fecha;
        this.hora = hora;
        this.precio = precio;
        this.estado = estado;
        this.num_cancha = num_cancha;
    }

    public long getId_reserva() {
        return id_reserva;
    }

    public void setId_reserva(long id_reserva) {
        this.id_reserva = id_reserva;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public long getId_cancha() {
        return id_cancha;
    }

    public void setId_cancha(long id_cancha) {
        this.id_cancha = id_cancha;
    }

    public long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(long id_usuario) {
        this.id_usuario = id_usuario;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getNum_cancha() {
        return num_cancha;
    }

    public void setNum_cancha(int num_cancha) {
        this.num_cancha = num_cancha;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "id_cancha=" + id_cancha +
                ", id_usuario=" + id_usuario +
                ", fecha=" + fecha +
                ", hora=" + hora +
                ", precio=" + precio +
                ", estado='" + estado + '\'' +
                ", num_cancha=" + num_cancha +
                '}';
    }
}
