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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.miprimeraapp.pruebaconexion.R;
import com.miprimeraapp.pruebaconexion.constante.Constante;
import com.miprimeraapp.pruebaconexion.interfaces.ProductAPI;
import com.miprimeraapp.pruebaconexion.models.Usuario;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Registrarse2 extends AppCompatActivity {
    Button btnRegistrarse;
    TextInputLayout tlNombre, tlApellido, tlNumCelular, tlNumId;
    EditText etNombre, etApellido, etNum, etId;
    String correo, contraseña, nombre, apellido;
    long numCelular, numId;
    ProductAPI productAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse2);
        btnRegistrarse = findViewById(R.id.btnRegistrarse);
        tlNombre=findViewById(R.id.textInputLayoutNombre);
        tlApellido=findViewById(R.id.textInputLayoutApellido);
        tlNumCelular=findViewById(R.id.textInputLayoutCelular);
        tlNumId=findViewById(R.id.textInputLayoutId);
        etNombre=findViewById(R.id.etNombre);
        etApellido=findViewById(R.id.etApellido);
        etNum=findViewById(R.id.etCelular);
        etId=findViewById(R.id.etId);

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre = etNombre.getText().toString().trim();
                apellido = etApellido.getText().toString().trim();
                String numCelular1 = etNum.getText().toString();
                String numId1 = etId.getText().toString();
                try {
                    if (validar()){
                        if (validarDato(nombre)){
                            if (validarDato(apellido)) {
                                if (validarDatoNum(numCelular1)){
                                    if (validarCedula(numId1)){
                                        getInformacion();
                                    } else {
                                        toastIncorrecto("Numero de cedula invalido");
                                    }
                                }else {
                                    toastIncorrecto("Numero de telefono invalido");
                                }
                            }else {
                                toastIncorrecto("Apellido invalido");
                            }
                        }else {
                            toastIncorrecto("Nombre invalido");
                        }
                    }else {
                        toastIncorrecto("Por favor, complete todos los campos");
                    }

                }
                    catch (Exception e){
                    toastIncorrecto("Se ha producido un error al intentar registrarte: "+ e.getMessage());
                }


        }
    });
    vaciar();
    }

    private boolean validarDato(String dato) {
        boolean resultado=false;
        if (!dato.matches("[A-Za-zñÑ\\s]*")){
            resultado=false;

        }else {
            resultado=true;
        }

        return resultado;
    }

    private void vaciar(){
        etNombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               tlNombre.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etApellido.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tlApellido.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tlNumCelular.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tlNumId.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private boolean validarDatoNum(final String dato) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PARTTERN = "^(?=.*[0-9])(?=\\S+$).{10,}$";

        pattern = Pattern.compile(PASSWORD_PARTTERN);
        matcher = pattern.matcher(dato);

        return matcher.matches();
    }
    private boolean validarCedula(final String dato) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PARTTERN = "^(?=.*[0-9])(?=\\S+$).{8,}$";

        pattern = Pattern.compile(PASSWORD_PARTTERN);
        matcher = pattern.matcher(dato);

        return matcher.matches();
    }
    private boolean validar() {
        boolean retorno = true;
        nombre = etNombre.getText().toString();
        apellido = etApellido.getText().toString();
        String numCelular1 = etNum.getText().toString();
        String numId1 = etId.getText().toString();
        if (nombre.isEmpty()){
            tlNombre.setError("Complete los campos");
            retorno=false;
        }else {
            tlNombre.setErrorEnabled(false);
        }
        if (apellido.isEmpty()){
            tlApellido.setError("Complete los campos");
            retorno=false;
        }else {
            tlApellido.setErrorEnabled(false);
        }
        if (numCelular1.isEmpty()){
            tlNumCelular.setError("Complete los campos");
            retorno=false;
        }else {
            tlNumCelular.setErrorEnabled(false);
        }
        if (numId1.isEmpty()){
            tlNumId.setError("Complete los campos");
            retorno=false;
        }else {
            tlNumId.setErrorEnabled(false);
        }
        return retorno;
    }
    private void getInformacion(){
        Intent intent = new Intent(Registrarse2.this, MenuLateral.class);
        Bundle extras = getIntent().getExtras();
         correo = extras.getString("correo");
         contraseña= extras.getString("contraseña");
        nombre = etNombre.getText().toString();
        apellido = etApellido.getText().toString();
        numCelular = Long.parseLong(etNum.getText().toString());
        numId = Long.parseLong(etId.getText().toString());
        Usuario usuario = new Usuario(nombre.toString(),apellido.toString(),numId,numCelular,correo.toString(),contraseña.toString());
        registrarUsuario(usuario);

    }
    private void registrarUsuario(Usuario usuario){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constante.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        productAPI = retrofit.create(ProductAPI.class);
        Call<Usuario> call = productAPI.addUsuario(usuario);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(response.isSuccessful()){
                    Intent intent = new Intent(Registrarse2.this, MenuLateral.class);
                    intent.putExtra("correo", correo);
                    startActivity(intent);
                }else {
                    toastIncorrecto("Correo ya registrado");
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                toastIncorrecto("Error al conectar");
            }
        });
    }
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
}