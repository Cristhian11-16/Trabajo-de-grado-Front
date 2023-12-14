package com.miprimeraapp.pruebaconexion.models;

public class Usuario {
    private long id_usuario;
    private String nombre;
    private String apellido;
    private long num_id;
    private long num_celular;
    private String correo;
    private String rol;
    private String contraseña;

    public long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public long getNum_id() {
        return num_id;
    }

    public void setNum_id(long num_id) {
        this.num_id = num_id;
    }

    public long getNum_celular() {
        return num_celular;
    }

    public void setNum_celular(long num_celular) {
        this.num_celular = num_celular;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id_usuario=" + id_usuario +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", num_id=" + num_id +
                ", num_celular=" + num_celular +
                ", correo='" + correo + '\'' +
                ", rol='" + rol + '\'' +
                ", contraseña='" + contraseña + '\'' +
                '}';
    }

    public Usuario(long id_usuario, String nombre, String apellido, long num_id, long num_celular, String correo, String contraseña, String rol) {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.num_id = num_id;
        this.num_celular = num_celular;
        this.correo = correo;
        this.contraseña = contraseña;
        this.rol = rol;

    }

    public Usuario(String nombre, String apellido, long num_id, long num_celular, String correo, String contraseña) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.num_id = num_id;
        this.num_celular = num_celular;
        this.correo = correo;
        this.contraseña = contraseña;
    }
}
