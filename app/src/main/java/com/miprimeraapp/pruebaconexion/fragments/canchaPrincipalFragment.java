package com.miprimeraapp.pruebaconexion.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.miprimeraapp.pruebaconexion.R;
import com.miprimeraapp.pruebaconexion.constante.Constante;
import com.miprimeraapp.pruebaconexion.interfaces.ProductAPI;
import com.miprimeraapp.pruebaconexion.models.Cancha;
import com.miprimeraapp.pruebaconexion.models.Direccion;
import com.miprimeraapp.pruebaconexion.models.Precios;
import com.miprimeraapp.pruebaconexion.models.Reserva;
import com.miprimeraapp.pruebaconexion.models.Usuario;
import com.squareup.picasso.Picasso;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class canchaPrincipalFragment extends Fragment {
    Usuario usuario;
    Precios precios;
    TextView tvNombre, tvDireccion, tvPrecio2, tvDia;
    Button btnReservar;
    EditText etFecha;
    ProductAPI productAPI;
    Cancha cancha;
    Direccion direccion;
    int diaMes,diaSemana, mesCalendar, añoCalendar;
    String dia, hora, correo, precio, dia1, hora1;
    long id_cancha;
    int horaConsulta, horaUpdate;
    Spinner spinnerHora;
    ImageSlider imageSlider;
    ImageView tvLogoCancha;
    DayOfWeek dayOfWeek;
    Date date;
    LocalDate todayDate;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cancha_principal, container, false);
        Bundle datosRecuperados = getArguments();
        id_cancha = datosRecuperados.getLong("id_cancha");
        correo = datosRecuperados.getString("correo");
        dia1 = datosRecuperados.getString("dia");
        hora1 = datosRecuperados.getString("hora");
        imageSlider = view.findViewById(R.id.imageSlider);
        spinnerHora = view.findViewById(R.id.spinnerHora);
        tvNombre = view.findViewById(R.id.tvNombreCancha);
        tvLogoCancha = view.findViewById(R.id.imageCardCancha);
        tvDireccion = view.findViewById(R.id.textView3);
        tvDia = view.findViewById(R.id.textView4);
        etFecha = view.findViewById(R.id.spinnerDia);
        tvPrecio2= view.findViewById(R.id.tvPrecioEdit);
        btnReservar = view.findViewById(R.id.buttonResevar);
        getCancha(id_cancha);
        tvDia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                diaMes = c.get(Calendar.DAY_OF_MONTH);
                diaSemana=c.get(Calendar.DAY_OF_WEEK);
                mesCalendar = c.get(Calendar.MONTH);
                añoCalendar=c.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        etFecha.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                        todayDate = LocalDate.parse(year+"-"+(month+1)+"-"+dayOfMonth);
                        dayOfWeek=todayDate.getDayOfWeek();
                        obtenerDia(todayDate);
                    }
                }
                ,añoCalendar,mesCalendar,diaMes);
                datePickerDialog.show();
                datePickerDialog.getDatePicker().setMinDate(2023);
            }
        });
        spinnerHora.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hora = spinnerHora.getItemAtPosition(position).toString();
                if (hora.equals("Hora")){

                }else {
                    btnReservar.setEnabled(true);
                    getInformacion(hora,dayOfWeek.getValue());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnReservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datosReserva(id_cancha,correo,todayDate,hora);
            }
        });
        return view;
    }

    private void datosReserva(long idCancha, String correo, LocalDate todayDate, String hora) {
        getUsuario(correo, idCancha,todayDate,hora);
    }

    private void getInformacion(String hora, int value) {
        if (value==1){
            horaConsulta = Integer.parseInt(hora);
            getPrecioCanchas(id_cancha,"Lunes",horaConsulta);
        }
        if (value==2){
            horaConsulta = Integer.parseInt(hora);
            getPrecioCanchas(id_cancha,"Martes",horaConsulta);
        }
        if (value==3){
            horaConsulta = Integer.parseInt(hora);
            getPrecioCanchas(id_cancha,"Miercoles",horaConsulta);
        }
        if (value==4){
            horaConsulta = Integer.parseInt(hora);
            getPrecioCanchas(id_cancha,"Jueves",horaConsulta);
        }
        if (value==5){
            horaConsulta = Integer.parseInt(hora);
            getPrecioCanchas(id_cancha,"Viernes",horaConsulta);
        }
        if (value==6){
            horaConsulta = Integer.parseInt(hora);
            getPrecioCanchas(id_cancha,"Sabado",horaConsulta);
        }
        if (value==7){
            horaConsulta = Integer.parseInt(hora);
            getPrecioCanchas(id_cancha,"Domingo",horaConsulta);
        }


    }

    private void obtenerDia(LocalDate todayDate) {
        if (etFecha.getText().equals("")){
            System.out.println("Seleccione una fecha");
        }else {
            llenarSpinnerDias();
        }

    }



    private void llenarSpinnerDias() {
            ArrayAdapter<CharSequence> adp4 = ArrayAdapter.createFromResource(getActivity(), R.array.hora, com.google.android.material.R.layout.support_simple_spinner_dropdown_item);
            spinnerHora.setAdapter(adp4);

    }

    private void agregarDatos( Cancha cancha, Direccion direccion) {
        //int horaConsulta = Integer.parseInt(hora);
        //getPrecioCanchas(id_cancha,dia,horaConsulta);
        int hora2;
        System.out.println(dia1);
        System.out.println(hora1);
        tvNombre.setText(cancha.getName());
        tvDireccion.setText(("Direccion: " + direccion.getTipo() + " "
                + direccion.getNum_tipo()+ " "
                + direccion.getOrientacion()+ " # "
                + direccion.getNum_calle()+ "-"
                + direccion.getNum_casa()));
        Picasso.get().load(cancha.getLogo()).into(tvLogoCancha);
        llenarImagenes(cancha);
        /*
        if (dia1.isEmpty()){

        }else {
            if (dia.equals("Lunes")){
                hora2 = Integer.parseInt(hora);
                spinnerDias.setSelection(1);
                spinnerHora.setSelection(hora2+1);
            }
        }*/
    }


    public void llenarImagenes(Cancha cancha){
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(cancha.getImagen1()
                ,"",
                ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(cancha.getImagen2()
                ,""
                ,ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(cancha.getImagen3()
                ,""
                ,ScaleTypes.CENTER_CROP));

        imageSlider.setImageList(slideModels);
    }
    public void getCancha(long id_cancha){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constante.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        productAPI = retrofit.create(ProductAPI.class);
        Call<Cancha> call = productAPI.getCancha(id_cancha);
        call.enqueue(new Callback<Cancha>() {
            @Override
            public void onResponse(Call<Cancha> call, Response<Cancha> response) {
                if (!response.isSuccessful()){
                    System.out.println("Salio mal: "+ response.message());
                }
                cancha = response.body();
                getDireccion(id_cancha);
                System.out.println(cancha.getName());
            }

            @Override
            public void onFailure(Call<Cancha> call, Throwable t) {

            }
        });
    }
    public void getDireccion(long id_cancha){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constante.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        productAPI = retrofit.create(ProductAPI.class);
        Call<Direccion> call = productAPI.getDireccionCancha(id_cancha);
        call.enqueue(new Callback<Direccion>() {
            @Override
            public void onResponse(Call<Direccion> call, Response<Direccion> response) {
                if (!response.isSuccessful()){
                    System.out.println("Salio mal: "+ response.message());
                }
                direccion = response.body();
                agregarDatos(cancha,direccion);

            }
            @Override
            public void onFailure(Call<Direccion> call, Throwable t) {

            }
        });
    }
    public void getPrecioCanchas(long id_cancha, String dia, int hora){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constante.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        productAPI = retrofit.create(ProductAPI.class);
        Call<Precios> call = productAPI.gerPrecio(hora, dia, id_cancha);
        call.enqueue(new Callback<Precios>() {
            @Override
            public void onResponse(Call<Precios> call, Response<Precios> response) {
                if (!response.isSuccessful()){
                    System.out.println("Salio mal: "+ response.message());

                }
                precios = response.body();
                precio = String.valueOf(precios.getPrecio());
                tvPrecio2.setText("$"+precio);
                //filtrarFecha(listPrecios, listaCancha);
            }
            @Override
            public void onFailure(Call<Precios> call, Throwable t) {
                System.out.println("No se conecto:"+ t.getMessage());

            }
        });
    }
    private void getUsuario(String correo, long idCancha, LocalDate todayDate, String hora) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constante.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        productAPI = retrofit.create(ProductAPI.class);
        Call<Usuario> call = productAPI.find(correo);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (!response.isSuccessful()){

                }
                usuario = response.body();
                updateInformacion(usuario,idCancha,todayDate,hora);
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
            }
        });
    }

    private void updateInformacion(Usuario usuario, long idCancha, LocalDate todayDate, String hora) {
        horaUpdate = Integer.parseInt(hora);
        String estado = "Abierto";
        String fecha = todayDate.toString();
        System.out.println(fecha);

        Bundle args = new Bundle();
        Fragment fragment = new pagoFragement();

        args.putLong("id_usuario",usuario.getId_usuario());
        args.putString("correo", usuario.getCorreo());
        args.putLong("id_cancha", idCancha);
        args.putString("fecha", String.valueOf(todayDate));
        args.putDouble("precio", precios.getPrecio());
        args.putString("hora", hora);
        fragment.setArguments(args);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.pantallaPrincipalFragment, fragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}