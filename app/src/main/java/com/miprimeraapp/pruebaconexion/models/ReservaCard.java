package com.miprimeraapp.pruebaconexion.models;

public class ReservaCard {
    private String nombre;
    private String fecha;
    private String estado;
    private String pago;
    private String logo;

    public ReservaCard(String nombre, String fecha, String estado, String pago, String logo) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.estado = estado;
        this.pago = pago;
        this.logo = logo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPago() {
        return pago;
    }

    public void setPago(String pago) {
        this.pago = pago;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
