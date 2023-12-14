package com.miprimeraapp.pruebaconexion.activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.miprimeraapp.pruebaconexion.R;
import com.miprimeraapp.pruebaconexion.constante.Constante;
import com.miprimeraapp.pruebaconexion.fragments.CuentaFragment;
import com.miprimeraapp.pruebaconexion.fragments.PantallaPrincipalFragment;
import com.miprimeraapp.pruebaconexion.fragments.ListReservaFragment;
import com.miprimeraapp.pruebaconexion.interfaces.ProductAPI;
import com.miprimeraapp.pruebaconexion.models.Usuario;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivityPrueba extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    //variables para cargar fragment
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    TextView tvNombre, tvCorreo;
    ProductAPI productAPI;
    String correo;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiry_main_prueba );
        Bundle args = new Bundle();

        //UI
        drawerLayout = findViewById(R.id.drawer_layout_prueba);
        navigationView = findViewById(R.id.nav_view_prueba);
        toolbar = findViewById(R.id.toolbarPrueba);
        //estblecer evento Onclic al NavigationVIew

        setSupportActionBar(toolbar);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                toolbar,
                R.string.open,
                R.string.close);
        Bundle extras = getIntent().getExtras();
        correo = extras.getString("correo");


        Fragment fragment = new PantallaPrincipalFragment();
        args.putString("correo",correo);
        fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().add(R.id.content, fragment).commit();
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        tvNombre = header.findViewById(R.id.nombreUsuarioNav);
        tvCorreo = header.findViewById(R.id.correoHeader);
        getUsuario(correo);



        //cargar fragment principal
        /*
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.pantallaPrincipalFragment, new canchaPrincipalFragment());
        fragmentTransaction.commit();*/
        //navigationView.setNavigationItemSelectedListener(this);
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
                Usuario usuario = response.body();
                tvNombre.setText(usuario.getNombre()+" "+usuario.getApellido());
                tvCorreo.setText(usuario.getCorreo());
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Bundle args = new Bundle();
        if (item.getItemId() == R.id.nav_canchas){
            Fragment fragment = new PantallaPrincipalFragment();
            args.putString("correo",correo);
            fragment.setArguments(args);
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.pantallaPrincipalFragment, fragment , null);
            fragmentTransaction.commit();
        }
        if (item.getItemId() == R.id.nav_cuenta){
            args.putString("correo",correo);
            Fragment fragment = new CuentaFragment();
            fragment.setArguments(args);
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.pantallaPrincipalFragment, fragment, null);
            fragmentTransaction.commit();
        }
        if (item.getItemId() == R.id.nav_reserva){
            Fragment fragment = new ListReservaFragment();
            args.putString("correo",correo);
            fragment.setArguments(args);
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.pantallaPrincipalFragment, fragment);
            fragmentTransaction.commit();
        }
        if (item.getItemId() == R.id.nav_cerrar){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("¿Deseas salir de FutyForAll?")
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(MainActivityPrueba.this, MainActivity.class);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            builder.show();

        }
        drawerLayout.closeDrawers();
        return true;
    }

    /*
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==event.KEYCODE_BACK){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("¿Deseas salir de FutyForAll?")
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory((Intent.CATEGORY_HOME));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            builder.show();
        }
        return super.onKeyDown(keyCode, event);
    }*/
    @Override
    public void onBackPressed() {

    }
/*
    private void selectItemNav(MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav
                break;
            case R.id.nav_reserva:
                break;
            case R.id.nav_cerrar:
                break;
        }
    }*/
}
