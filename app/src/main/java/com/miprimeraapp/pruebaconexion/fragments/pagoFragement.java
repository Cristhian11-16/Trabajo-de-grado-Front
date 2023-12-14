package com.miprimeraapp.pruebaconexion.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.miprimeraapp.pruebaconexion.R;
import com.miprimeraapp.pruebaconexion.constante.Constante;
import com.miprimeraapp.pruebaconexion.interfaces.ProductAPI;
import com.miprimeraapp.pruebaconexion.models.Cancha;
import com.miprimeraapp.pruebaconexion.models.Pago;
import com.miprimeraapp.pruebaconexion.models.Reserva;
import com.miprimeraapp.pruebaconexion.models.Usuario;
import com.squareup.picasso.Picasso;

import java.time.LocalDate;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class pagoFragement extends Fragment {
    long id_cancha, id_usuario;
    String fecha, hora, correo, seleccion;
    Double precio;
    Spinner spinnerMedios;
    TextView tvNombre, tvFecha, tvHora, tvPrecio, tvEstadoPago, tvNombreCancha;
    ImageView tvLogoCancha;
    Button btnPagar, btnCancelar;
    ProductAPI productAPI;
    Cancha cancha;
    Usuario usuario;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reserva_fragement, container, false);
        Bundle datosRecuperados = getArguments();
        id_cancha = datosRecuperados.getLong("id_cancha");
        id_usuario = datosRecuperados.getLong("id_usuario");
        correo = datosRecuperados.getString("correo");
        fecha = datosRecuperados.getString("fecha");
        hora = datosRecuperados.getString("hora");
        precio = datosRecuperados.getDouble("precio");
        spinnerMedios = view.findViewById(R.id.spinnerMedios);
        tvNombre = view.findViewById(R.id.tvNombreUsuario);
        tvNombreCancha = view.findViewById(R.id.tvNombreCancha);
        tvFecha = view.findViewById(R.id.tvFecha2);
        tvHora = view.findViewById(R.id.tvHora2);
        tvPrecio = view.findViewById(R.id.tvPrecio2);
        tvEstadoPago = view.findViewById(R.id.tvEstadoPago2);
        tvLogoCancha = view.findViewById(R.id.imageCardPago);
        btnPagar = view.findViewById(R.id.buttonPagar);
        btnCancelar = view.findViewById(R.id.buttonCancelar);
        getCancha(id_cancha,correo);
        llenarSpinnerMedioPago();

        spinnerMedios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                seleccion = spinnerMedios.getItemAtPosition(position).toString();
                if (seleccion.equals("Elegir")){
                    btnPagar.setEnabled(false);

                }else {
                    btnPagar.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postPago(id_cancha,id_usuario,seleccion, fecha, hora, precio);
            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();

                args.putString("correo", correo);
                Fragment fragment = new PantallaPrincipalFragment();
                fragment.setArguments(args);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.pantallaPrincipalFragment, fragment);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;




    }

    private void postPago(long idCancha, long idUsuario, String seleccion, String fecha, String hora, Double precio) {
        int horaUpdate = Integer.parseInt(hora);
        Pago pago = new Pago(idCancha,idUsuario, true, seleccion);
        Reserva reserva = new Reserva(idCancha, idUsuario, fecha, horaUpdate, precio,"Abierto",2);
        postReserva(reserva);
        registrarPago(pago);
    }
    private void postReserva(Reserva reserva){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constante.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        productAPI = retrofit.create(ProductAPI.class);
        Call<Reserva> call = productAPI.addReserva(reserva);
        call.enqueue(new Callback<Reserva>() {
            @Override
            public void onResponse(Call<Reserva> call, Response<Reserva> response) {
                if (!response.isSuccessful()){
                    System.out.println("Salio mal en Reserva: "+response.message());
                }else {
                    System.out.println("Salio Bien en reserva: "+ response.message());

                }
            }

            @Override
            public void onFailure(Call<Reserva> call, Throwable t) {
                System.out.println("No se conecto en reserva:"+ t.getMessage());
            }
        });
    }
    private void registrarPago(Pago pago) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constante.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        productAPI = retrofit.create(ProductAPI.class);
        Call<Pago> call = productAPI.addPago(pago);
        call.enqueue(new Callback<Pago>() {
            @Override
            public void onResponse(Call<Pago> call, Response<Pago> response) {
                if (!response.isSuccessful()){
                    System.out.println("Salio mal: "+ response.message());
                }else{
                    Bundle args = new Bundle();

                    toastCorrecto("¡Gracias por tu pago!");
                    args.putString("correo", correo);
                    Fragment fragment = new PantallaPrincipalFragment();
                    fragment.setArguments(args);
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.pantallaPrincipalFragment, fragment);
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }

            @Override
            public void onFailure(Call<Pago> call, Throwable t) {

            }
        });
    }

    private void inicializarDatos(Cancha cancha, Usuario usuario) {

        tvNombreCancha.setText(cancha.getName());
        tvNombre.setText(usuario.getNombre()+" "+usuario.getApellido());
        tvFecha.setText(fecha);
        tvHora.setText(hora+":00");
        tvPrecio.setText("$"+precio);
        Picasso.get().load(cancha.getLogo()).into(tvLogoCancha);
    }

    private void llenarSpinnerMedioPago() {
        ArrayAdapter<CharSequence> adp4 = ArrayAdapter.createFromResource(getActivity(), R.array.mediosPago, com.google.android.material.R.layout.support_simple_spinner_dropdown_item);
        spinnerMedios.setAdapter(adp4);
    }
    public void getCancha(long id_cancha, String correo){
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
                }else {
                    cancha = response.body();
                    getUsuario(correo,cancha);
                }
            }

            @Override
            public void onFailure(Call<Cancha> call, Throwable t) {
                System.out.println("No se conecto: "+ t.getMessage());
            }
        });
    }
    private void getUsuario(String correo, Cancha cancha) {
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
                inicializarDatos(cancha,usuario);
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
            }
        });
    }
    public void toastCorrecto(String mensaje){
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.custom_toast_ok, (ViewGroup) getView().findViewById(R.id.ll_custom_toast_ok));
        TextView txtMensaje = view.findViewById(R.id.txtMensajeToastOk);
        txtMensaje.setText(mensaje);

        Toast toast = new Toast(getActivity());
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0,200);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }

}