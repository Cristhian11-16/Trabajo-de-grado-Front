package com.miprimeraapp.pruebaconexion.interfaces;

import com.miprimeraapp.pruebaconexion.models.Cancha;
import com.miprimeraapp.pruebaconexion.models.Direccion;
import com.miprimeraapp.pruebaconexion.models.Pago;
import com.miprimeraapp.pruebaconexion.models.Precios;
import com.miprimeraapp.pruebaconexion.models.Reserva;
import com.miprimeraapp.pruebaconexion.models.Usuario;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProductAPI {

    //Obtener un usuario funciona
    @GET("usuario/{correo}")
    Call<Usuario> find(@Path("correo") String correo);
    //Actualizar datos de usuario
    @PUT("usuario/actualizar")
    Call<Usuario> updateUsuario(@Body Usuario usuario);

    //Obtener direccion de una cancha
    @GET("canchas/direccion/")
    Call<ArrayList<Direccion>> getDireccion();

    //listas de canchas
    @GET("/canchas/name")
    Call<ArrayList<Cancha>> getCanchas();

    //listas de reservas de usuario
    @GET("/reserva/user/{id_usuario}")
    Call<ArrayList<Reserva>> getReservas(@Path("id_usuario") long id_usuario);

    //Registrar un usuario
    //Funciona
    @POST("usuario/registrar")
    Call<Usuario> addUsuario(@Body Usuario usuario);

    @POST("reserva/registrar")
    Call<Reserva> addReserva(@Body Reserva reserva);

    @POST("reserva/pago/registrar")
    Call<Pago> addPago(@Body Pago pago);
   
    //usuario/login2/?correo=crisgonos1532@gmail.com&contraseña=Cris1532-
    //funciona
    @GET("/usuario/login2")
    Call<String> login(@Query("correo")String correo,
                       @Query("contraseña")String contraseña);
    //Obtener precios de cancha
    @GET("/canchas/precios/canchahora&dia")
    Call<Precios> gerPrecio(@Query("hora")int hora,
                            @Query("dia")String dia,
                            @Query("id_cancha") long id_cancha);
    //Obetner una cancha
    @GET("/canchas/{id_cancha}")
    Call<Cancha> getCancha(@Path("id_cancha") long id_cancha);

    @GET("/canchas/direccion/{id_cancha}")
    Call<Direccion> getDireccionCancha(@Path("id_cancha") long id_cancha);



    /*
    @POST("/upload/{nameImg}")
    @Multipart
    Call<Void> uploadPhoto(@Part Multipart.Part file);*/

}
