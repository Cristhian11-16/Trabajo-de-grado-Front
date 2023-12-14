package com.miprimeraapp.pruebaconexion.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.miprimeraapp.pruebaconexion.R;
import com.miprimeraapp.pruebaconexion.constante.Constante;
import com.miprimeraapp.pruebaconexion.interfaces.ProductAPI;
import com.miprimeraapp.pruebaconexion.models.Cancha;

import java.util.ArrayList;
import java.util.List;

import retrofit2.*;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Login extends AppCompatActivity {
    List<Cancha>listCancha = new ArrayList<>();
    ListView listView;
    TextInputLayout tlCorreo, tlContraseña;
    EditText etCorreo, etContraseña;
    Button btnSignin, btnLogin;
    TextView tvPrueba;
    ProductAPI productAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnSignin=findViewById(R.id.btnSingin);
        btnLogin = findViewById(R.id.btnLogin);
        tvPrueba = findViewById(R.id.tvCuenta);
        etCorreo= findViewById(R.id.etCorreo);
        tlCorreo = findViewById(R.id.textInputLayoutCorreo);
        tlContraseña = findViewById(R.id.textInputLayoutContraseña);
        etContraseña = findViewById(R.id.etContraseña);
        btnSignin.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             Intent intent = new Intent(Login.this, Registrarse.class);
                                             startActivity(intent);
                                         }
                                     }

        );
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //find(1);
                //parqueadero(true, 1);
                //login("crisgonos15322@gmail.com","Cris1532");
                try {
                    if (validar()){
                        String getCorreo = etCorreo.getText().toString().toLowerCase().trim();
                        String getContraseña = etContraseña.getText().toString();
                        login(getCorreo,getContraseña);

                    }else{
                        toastIncorrecto("Por favor, complete todos los campos");
                    }
                }catch (Exception e){
                    toastIncorrecto("Se ha producido un error al intentar loguearte: "+ e.getMessage());
                }

            }
        });
        vaciar();
    }
    private void vaciar(){
        etCorreo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tlCorreo.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etContraseña.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tlContraseña.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void login(String correo, String contraseña) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl(Constante.BASE_URL)
                .build();
        productAPI = retrofit.create(ProductAPI.class);
        Call<String> call = productAPI.login(correo,contraseña);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    String responseString = response.body();
                    if (responseString.equals("usuario")){
                        Intent intent = new Intent(Login.this, MainActivityPrueba.class);
                        intent.putExtra("correo", correo);
                        startActivity(intent);
                    }else {
                        if (responseString.equals("admin")){
                            toastCorrecto("Admin");
                        }else {
                            if (responseString.equals("Acceso denegado contraseña incorrecta")){
                                toastIncorrecto("Contraseña incorrecta");
                            }
                        }
                    }
                }else {
                    toastIncorrecto("Correo y/o contraseña invalidos");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                toastIncorrecto("Error al conectarse");
            }
        });
    }
    /*private void parqueadero(boolean parqueadero, long id_cancha){
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.1.19:8080/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        productAPI = retrofit.create(ProductAPI.class);
        Call<Cancha> call = productAPI.parqueadero(parqueadero,id_cancha);
        call.enqueue(new Callback<Cancha>() {
            @Override
            public void onResponse(Call<Cancha> call, Response<Cancha> response) {
                if (!response.isSuccessful()){
                    toastIncorrecto("Salio mal");
                }
                toastCorrecto("Salio bien");
                Cancha cancha = response.body();
                tvPrueba.setText(cancha.getName());

            }

            @Override
            public void onFailure(Call<Cancha> call, Throwable t) {
                toastIncorrecto(t.getMessage());
            }
        } );

    }*/





        public void toastCorrecto(String mensaje){
            LayoutInflater layoutInflater = getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.custom_toast_ok, (ViewGroup) findViewById(R.id.ll_custom_toast_ok));
            TextView txtMensaje = view.findViewById(R.id.txtMensajeToastOk);
            txtMensaje.setText(mensaje);

            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0,200);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(view);
            toast.show();
    }
    public void toastIncorrecto(String mensaje){
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.custom_toast_error, (ViewGroup) findViewById(R.id.ll_custom_toast_error));
        TextView txtMensaje = view.findViewById(R.id.txtMensajeToastError);
        txtMensaje.setText(mensaje);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0,200);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }
        private boolean validar(){
            boolean retorno = true;
            String correo, contraseña;
            correo = etCorreo.getText().toString();
            contraseña = etContraseña.getText().toString();
            if (correo.isEmpty()){
                tlCorreo.setError("Complete los campos");
                retorno=false;
            }else {
                tlCorreo.setErrorEnabled(false);
            }
            if (contraseña.isEmpty()){
                tlContraseña.setError("Complete los campos");
                retorno=false;
            }else {
                tlContraseña.setErrorEnabled(false);
            }

            return retorno;
        }
    }




