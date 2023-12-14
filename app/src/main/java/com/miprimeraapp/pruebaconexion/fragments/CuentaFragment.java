package com.miprimeraapp.pruebaconexion.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.miprimeraapp.pruebaconexion.R;
import com.miprimeraapp.pruebaconexion.activitys.MenuLateral;
import com.miprimeraapp.pruebaconexion.activitys.Registrarse2;
import com.miprimeraapp.pruebaconexion.constante.Constante;
import com.miprimeraapp.pruebaconexion.databinding.ActivityMenuLateralBinding;
import com.miprimeraapp.pruebaconexion.interfaces.ProductAPI;
import com.miprimeraapp.pruebaconexion.models.Usuario;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CuentaFragment extends Fragment {

    private ActivityMenuLateralBinding binding;
    String correo, numCel, numId, contraseña, rol, nombre, apellido;
    long id_usuario, num_id, num_celular;
    TextView tvNombre, tvCorreo, tvApellido, tvIdentificacion, tvNumero;
    Button btnAceptar, btnCancelar;
    ImageButton btnEdit;
    ProductAPI productAPI;
    Usuario usuarioPut, usuario;
    LinearLayout ll_toast;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cuenta, container, false);
        Bundle datosRecuperados = getArguments();
        correo = datosRecuperados.getString("correo");
        System.out.println(correo);
        tvNombre = view.findViewById(R.id.tvNombreEdit);
        tvCorreo = view.findViewById(R.id.tvCorreoEdit);
        tvApellido = view.findViewById(R.id.tvApellidoEdit);
        tvIdentificacion = view.findViewById(R.id.itvNumIdentificacionEdit);
        tvNumero = view.findViewById(R.id.tvNumCelularEdit);
        btnAceptar = view.findViewById(R.id.btnAceptar);
        btnCancelar = view.findViewById(R.id.btnCancelar);
        btnEdit = view.findViewById(R.id.editarInfo);
        getUsuario(correo);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                habilitarCampos();
            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUsuario(correo);
                deshabilitarCampos();
            }
        });
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInformacion();
                toastCorrecto("Se actualizaron tus datos");
            }
        });

        return view;
    }

    private void getInformacion() {
        nombre = tvNombre.getText().toString();
        apellido = tvApellido.getText().toString();
        correo = tvCorreo.getText().toString();
        numCel = tvNumero.getText().toString();
        numId = tvIdentificacion.getText().toString();
        num_id = Long.parseLong(numId);
        num_celular = Long.parseLong(numCel);
        usuarioPut = new Usuario(id_usuario,nombre,apellido,num_id,num_celular,correo,contraseña, rol);
        updateUsuario(usuarioPut);
        deshabilitarCampos();

    }

    private void habilitarCampos() {
        tvNombre.setEnabled(true);
        tvApellido.setEnabled(true);
        tvCorreo.setEnabled(true);
        tvIdentificacion.setEnabled(true);
        tvNumero.setEnabled(true);
        btnCancelar.setEnabled(true);
        btnAceptar.setEnabled(true);
    }
    private void deshabilitarCampos(){

        tvNombre.setEnabled(false);
        tvApellido.setEnabled(false);
        tvCorreo.setEnabled(false);
        tvIdentificacion.setEnabled(false);
        tvNumero.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnAceptar.setEnabled(false);
    }

    private void getUsuario(String correo) {
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
                llenarDatos(usuario);
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
            }
        });
    }

    private void llenarDatos(Usuario usuario) {
        contraseña = usuario.getContraseña();
        System.out.println(contraseña);
        rol = usuario.getRol();
        id_usuario = usuario.getId_usuario();
        tvNombre.setText(usuario.getNombre());
        tvApellido.setText(usuario.getApellido());
        tvCorreo.setText(usuario.getCorreo());
        numCel = String.valueOf(usuario.getNum_celular());
        numId = String.valueOf(usuario.getNum_id());
        tvIdentificacion.setText(numId);
        tvNumero.setText((numCel));
    }

    private void updateUsuario(Usuario usuario){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constante.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        productAPI = retrofit.create(ProductAPI.class);
        Call<Usuario> call = productAPI.updateUsuario(usuario);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (!response.isSuccessful()){
                    System.out.println("Salio mal: "+ response.message());

                }
                System.out.println("Se actualizo correctamente");
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                System.out.println("No se conecto: "+ t.getMessage());
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