package com.miprimeraapp.pruebaconexion.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.miprimeraapp.pruebaconexion.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registrarse extends AppCompatActivity {

    Button btnLogin2, btnRegistrarse;
    ImageButton btnInformacion;
    TextInputLayout tlCorreo, tlContraseña, tlContraseña2;
    EditText etCorreo, etContraseña, etContraseña2;
    String correo, contraseña, contraseña2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        btnRegistrarse=findViewById(R.id.btnRegistrarse);
        tlCorreo=findViewById(R.id.textInputLayoutCorreo);
        tlContraseña=findViewById(R.id.textInputLayoutContraseña);
        tlContraseña2=findViewById(R.id.textInputLayoutContraseña2);
        etCorreo=findViewById(R.id.etCorreo);
        etContraseña=findViewById(R.id.etContraseña);
        etContraseña2=findViewById(R.id.etContraseña2);
        btnInformacion=findViewById(R.id.btnInformacion);
        btnLogin2=findViewById(R.id.btn2Login);

        btnLogin2.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             Intent intent = new Intent(Registrarse.this, Login.class);
                                             startActivity(intent);
                                         }
                                     });

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (validar()){
                        correo = etCorreo.getText().toString().toLowerCase().trim();
                        contraseña = etContraseña.getText().toString();
                        if (validarCorreo(correo)){
                            if (validarContraseña(contraseña)){
                                    if (compararContraseñas()){
                                        getInformacion();
                                    }else {
                                        toastIncorrecto("Las contraseñas no coinciden");
                                    }
                            }else{
                                toastIncorrecto("Contraseña invalida");
                            }
                        }else {
                            toastIncorrecto("Correo invalido");
                        }
                    }else{
                        toastIncorrecto("Por favor, complete todos los campos");
                    }
                }catch (Exception e){
                    toastIncorrecto("Se ha producido un error al intentar registrarte: "+ e.getMessage());
                }
                }
            });
        vaciar();
        btnInformacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toastInformacion("La contraseña debe contener:\n" +
                        "•Entre 8-20 caracteres\n" +
                        "•Debe contener minimo una mayuscula\n" +
                        "•Debe contener minimo un numero" +
                        "•Debe contener alguno de estos simbolos: #@$%^&+=");
            }
        });

        }

    private boolean validarEspacio(String correo) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PARTTERN = "(?=\\S)";

        pattern = Pattern.compile(PASSWORD_PARTTERN);
        matcher = pattern.matcher(correo);

        return matcher.matches();
    }

    private boolean validarContraseña(final String contraseña) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PARTTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[#@$%^&+=])(?=\\S+$).{8,}$";

        pattern = Pattern.compile(PASSWORD_PARTTERN);
        matcher = pattern.matcher(contraseña);

        return matcher.matches();
    }

    private boolean validarCorreo(String correo) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(correo).matches();
    }

    private boolean compararContraseñas() {
        boolean retorno=true;
        contraseña = etContraseña.getText().toString();
        contraseña2= etContraseña2.getText().toString();
        if (contraseña.equals(contraseña2)){
            retorno = true;
        }else{
            retorno=false;
        }
        return retorno;
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
            etContraseña2.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    tlContraseña2.setErrorEnabled(false);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    private boolean validar() {
        boolean retorno = true;

        correo = etCorreo.getText().toString();
        contraseña = etContraseña.getText().toString();
        contraseña2= etContraseña2.getText().toString();
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
        if(contraseña2.isEmpty()){
            tlContraseña2.setError("Complete los campos");
            retorno=false;
        }else {
            tlContraseña2.setErrorEnabled(false);
        }

        return retorno;
    }
    private void getInformacion(){

        Intent intent = new Intent(Registrarse.this, Registrarse2.class);
        intent.putExtra("correo",correo);
        intent.putExtra("contraseña",contraseña);
        startActivity(intent);
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
    public void toastInformacion(String mensaje){
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.custom_informacion, (ViewGroup) findViewById(R.id.ll_custom_toast_info));
        TextView txtMensaje = view.findViewById(R.id.txtMensajeToastInfo);
        txtMensaje.setText(mensaje);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0,200);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }

}