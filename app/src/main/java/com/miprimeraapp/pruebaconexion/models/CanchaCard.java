package com.miprimeraapp.pruebaconexion.models;

import java.io.Serializable;

public class CanchaCard implements Serializable {
    private long id_cancha;
    private String name;
    private String direccion;
    private String barrio;
    private String localidad;
    private String precios;
    private String dia;
    private String hora;
    private String logo;
    private boolean parqueadero;
    private boolean futbol_5;
    private boolean futbol_8;


    public CanchaCard(long id_cancha, String name, String direccion, String barrio, String localidad, String precios, String dia, String hora, String logo, boolean parqueadero, boolean futbol_5, boolean futbol_8) {
        this.id_cancha = id_cancha;
        this.name = name;
        this.direccion = direccion;
        this.barrio = barrio;
        this.localidad = localidad;
        this.precios = precios;
        this.dia = dia;
        this.hora = hora;
        this.logo = logo;
        this.parqueadero = parqueadero;
        this.futbol_5 = futbol_5;
        this.futbol_8 = futbol_8;
    }

    public long getId_cancha() {
        return id_cancha;
    }

    public void setId_cancha(long id_cancha) {
        this.id_cancha = id_cancha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public boolean isParqueadero() {
        return parqueadero;
    }

    public void setParqueadero(boolean parqueadero) {
        this.parqueadero = parqueadero;
    }

    public boolean isFutbol_5() {
        return futbol_5;
    }

    public void setFutbol_5(boolean futbol_5) {
        this.futbol_5 = futbol_5;
    }

    public boolean isFutbol_8() {
        return futbol_8;
    }

    public void setFutbol_8(boolean futbol_8) {
        this.futbol_8 = futbol_8;
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

    public String getPrecios() {
        return precios;
    }

    public void setPrecios(String precios) {
        this.precios = precios;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public CanchaCard() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getImagenId() {
        return logo;
    }

    public void setImagenId(String logo) {
        this.logo = logo;
    }
}
