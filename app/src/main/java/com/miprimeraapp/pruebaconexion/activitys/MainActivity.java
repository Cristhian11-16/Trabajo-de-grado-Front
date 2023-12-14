package com.miprimeraapp.pruebaconexion.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.miprimeraapp.pruebaconexion.R;

public class MainActivity extends AppCompatActivity {
    Button btnSignin;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignin=findViewById(R.id.btnSignin);
        btnLogin=findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              Intent intent = new Intent(MainActivity.this, Login.class);
                                              startActivity(intent);
                                          }
                                      }

        );
        btnSignin.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(MainActivity.this, Registrarse.class);
                                            startActivity(intent);
                                        }
                                    }

        );

    }
    @Override
    public void onBackPressed() {

    }

}