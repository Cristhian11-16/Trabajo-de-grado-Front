package com.miprimeraapp.pruebaconexion.models;

public class Pago {
    private long id_pago;
    private long id_usuario;
    private long id_cancha;
    private boolean estado;
    private String medio_pago;

    public Pago(long id_pago, long id_usuario, long id_cancha, boolean estado, String medio_pago) {
        this.id_pago = id_pago;
        this.id_usuario = id_usuario;
        this.id_cancha = id_cancha;
        this.estado = estado;
        this.medio_pago = medio_pago;
    }

    public Pago(long id_usuario, long id_cancha, boolean estado, String medio_pago) {
        this.id_usuario = id_usuario;
        this.id_cancha = id_cancha;
        this.estado = estado;
        this.medio_pago = medio_pago;
    }

    public long getId_pago() {
        return id_pago;
    }

    public void setId_pago(long id_pago) {
        this.id_pago = id_pago;
    }

    public long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public long getId_cancha() {
        return id_cancha;
    }

    public void setId_cancha(long id_cancha) {
        this.id_cancha = id_cancha;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getMedio_pago() {
        return medio_pago;
    }

    public void setMedio_pago(String medio_pago) {
        this.medio_pago = medio_pago;
    }

    @Override
    public String toString() {
        return "Pago{" +
                "id_pago=" + id_pago +
                ", id_usuario=" + id_usuario +
                ", id_cancha=" + id_cancha +
                ", estado=" + estado +
                ", medio_pago='" + medio_pago + '\'' +
                '}';
    }
}
