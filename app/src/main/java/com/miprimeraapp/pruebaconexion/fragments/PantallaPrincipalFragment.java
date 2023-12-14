package com.miprimeraapp.pruebaconexion.fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.miprimeraapp.pruebaconexion.R;
import com.miprimeraapp.pruebaconexion.constante.Constante;
import com.miprimeraapp.pruebaconexion.interfaces.ProductAPI;
import com.miprimeraapp.pruebaconexion.models.Cancha;
import com.miprimeraapp.pruebaconexion.models.CanchaAdapter;
import com.miprimeraapp.pruebaconexion.models.CanchaCard;
import com.miprimeraapp.pruebaconexion.models.Direccion;
import com.miprimeraapp.pruebaconexion.models.Precios;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PantallaPrincipalFragment extends Fragment implements SearchView.OnQueryTextListener {

    CanchaAdapter canchaAdapter;
    RecyclerView recyclerViewCancha;
    ProductAPI productAPI;
    Precios precios;
    ArrayList<Cancha> cancha;
    ArrayList<CanchaCard> listaCancha;
    ArrayList<Direccion> listDireccion;
    SearchView searchView;
    String barrio = "Barrios", dia = "Dia", localidad = "Localidades", hora = "Hora", correo;
    ImageButton ic_borrar, ic_buscar;
    Spinner spinnerLocalidades, spinnerBarrios, spinnerDias, spinnerHora;
    CheckBox parqueadero, fut_8;

    //Referencias
    Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pantalla_principal, container, false);
        recyclerViewCancha = view.findViewById(R.id.recyclerViewCancha);
        searchView = view.findViewById(R.id.buscadorPag);
        searchView.setOnQueryTextListener(this);
        spinnerLocalidades = view.findViewById(R.id.spinnerLocalidad);
        spinnerBarrios = view.findViewById(R.id.spinnerBarrio);
        spinnerDias = view.findViewById(R.id.spinnerDia);
        spinnerHora = view.findViewById(R.id.spinnerHora);
        ic_borrar = view.findViewById(R.id.ic_limpiar);
        ic_buscar = view.findViewById(R.id.ic_search);
        parqueadero = view.findViewById(R.id.checkBoxPark);
        fut_8 = view.findViewById(R.id.checkBoxFut8);
        Bundle datosRecuperados = getArguments();
        correo = datosRecuperados.getString("correo");
        listaCancha = new ArrayList<>();
        initRecyclerView();
        initSpinner();
        ic_borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (localidad.equals("Localidades") && dia.equals("Dia")) {
                    borrarData();
                    initRecyclerView();
                    parqueadero.setChecked(false);
                    fut_8.setChecked(false);
                } else {
                    spinnerLocalidades.setSelection(0);
                    spinnerDias.setSelection(0);
                    parqueadero.setChecked(false);
                    fut_8.setChecked(false);
                    borrarData();
                    initRecyclerView();
                }
            }
        });
        ic_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (localidad.equals("Localidades")&& dia.equals("Dia")) {
                    borrarData();
                    initRecyclerView();
                } else {
                    borrarData();
                    cargarLista(localidad, barrio, dia, hora);
                }
            }
        });
        spinnerLocalidades.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                localidad = spinnerLocalidades.getItemAtPosition(position).toString();
                llenarSpinnerBarrio(localidad);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerBarrios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                barrio = spinnerBarrios.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerDias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dia = spinnerDias.getItemAtPosition(position).toString();
                llenarSpinnerDias(dia);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerHora.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hora = spinnerHora.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }


    private void llenarSpinnerBarrio(String barrio) {
        if (barrio.equals("Localidades")) {
            spinnerBarrios.setAdapter(null);
        }
        if (barrio.equals("Puente Aranda")) {
            ArrayAdapter<CharSequence> adp1 = ArrayAdapter.createFromResource(getActivity(), R.array.barriosPuente, R.layout.spinner_basico);
            spinnerBarrios.setAdapter(adp1);
        }
        if (barrio.equals("Rafael Uribe Uribe")) {
            ArrayAdapter<CharSequence> adp1 = ArrayAdapter.createFromResource(getActivity(), R.array.barriosUribe, R.layout.spinner_basico);
            spinnerBarrios.setAdapter(adp1);
        }
        if (barrio.equals("Antonio Nariño")) {
            ArrayAdapter<CharSequence> adp1 = ArrayAdapter.createFromResource(getActivity(), R.array.barriosAntonio, R.layout.spinner_basico);
            spinnerBarrios.setAdapter(adp1);
        }
        if (barrio.equals("Teusaquillo")) {
            ArrayAdapter<CharSequence> adp1 = ArrayAdapter.createFromResource(getActivity(), R.array.barriosTeusaquillo, R.layout.spinner_basico);
            spinnerBarrios.setAdapter(adp1);
        }
        if (barrio.equals("Martires")) {
            ArrayAdapter<CharSequence> adp1 = ArrayAdapter.createFromResource(getActivity(), R.array.barriosMartires, R.layout.spinner_basico);
            spinnerBarrios.setAdapter(adp1);
        }
    }

    private void llenarSpinnerDias(String dia) {
        if (dia.equals("Dia")) {
            spinnerHora.setAdapter(null);
        } else {
            ArrayAdapter<CharSequence> adp4 = ArrayAdapter.createFromResource(getActivity(), R.array.hora, R.layout.spinner_basico);
            spinnerHora.setAdapter(adp4);
        }
    }

    private void initSpinner() {
        ArrayAdapter<CharSequence> adp2 = ArrayAdapter.createFromResource(getActivity(), R.array.localidades, R.layout.spinner_basico);
        spinnerLocalidades.setAdapter(adp2);
        ArrayAdapter<CharSequence> adp3 = ArrayAdapter.createFromResource(getActivity(), R.array.dia, R.layout.spinner_basico);
        spinnerDias.setAdapter(adp3);
    }

    private void initRecyclerView() {
        getCanchasApi();
        System.out.println(cancha);
    }

    private void borrarData() {
        listaCancha.clear();
        canchaAdapter.notifyDataSetChanged();
    }

    private void mostrarData(ArrayList<CanchaCard> listaCanchaM) {
        recyclerViewCancha.setLayoutManager(new LinearLayoutManager(getContext()));
        canchaAdapter = new CanchaAdapter(listaCanchaM, getContext());
        recyclerViewCancha.setAdapter(canchaAdapter);

        canchaAdapter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                long id_cancha = listaCanchaM.get(recyclerViewCancha.getChildAdapterPosition(v)).getId_cancha();
                String dia = listaCanchaM.get(recyclerViewCancha.getChildAdapterPosition(v)).getDia();
                String hora = listaCanchaM.get(recyclerViewCancha.getChildAdapterPosition(v)).getHora();
                if (dia.equals("")){

                }else {
                    args.putString("dia",dia);
                    args.putString("hora",hora);
                }
                System.out.println(id_cancha);
                Fragment fragment = new canchaPrincipalFragment();

                args.putLong("id_cancha",id_cancha);
                args.putString("correo", correo);
                fragment.setArguments(args);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.pantallaPrincipalFragment, fragment);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });
    }

    private void cargarLista(String localidad, String barrio, String dia, String hora) {
        if (localidad.equals("Localidades")) {
                if (fut_8.isChecked()) {
                    if (parqueadero.isChecked()) {
                        for (int i = 0; i < cancha.size(); i++) {
                            if (cancha.get(i).isParqueadero() && cancha.get(i).isFutbol_8()) {
                                listaCancha.add(new CanchaCard(
                                        cancha.get(i).getId_cancha(),
                                        cancha.get(i).getName(),
                                        "Direccion: " + listDireccion.get(i).getTipo() + " " + listDireccion.get(i).getNum_tipo() + " " + listDireccion.get(i).getOrientacion() + " # " + listDireccion.get(i).getNum_calle() + "-" + listDireccion.get(i).getNum_casa(),
                                        "Barrio: " + listDireccion.get(i).getBarrio(),
                                        "Localidad: " + listDireccion.get(i).getLocalidad(),
                                        "",
                                        "",
                                        "",
                                        cancha.get(i).getLogo(),
                                        cancha.get(i).isParqueadero(),
                                        cancha.get(i).isFutbol_5(),
                                        cancha.get(i).isFutbol_8()));
                            }
                        }
                    } else {
                        for (int i = 0; i < cancha.size(); i++) {
                            if (cancha.get(i).isFutbol_8()) {
                                listaCancha.add(new CanchaCard(
                                        cancha.get(i).getId_cancha(),
                                        cancha.get(i).getName(),
                                        "Direccion: " + listDireccion.get(i).getTipo() + " " + listDireccion.get(i).getNum_tipo() + " " + listDireccion.get(i).getOrientacion() + " # " + listDireccion.get(i).getNum_calle() + "-" + listDireccion.get(i).getNum_casa(),
                                        "Barrio: " + listDireccion.get(i).getBarrio(),
                                        "Localidad: " + listDireccion.get(i).getLocalidad(),
                                        "",
                                        "",
                                        "",
                                        cancha.get(i).getLogo(),
                                        cancha.get(i).isParqueadero(),
                                        cancha.get(i).isFutbol_5(),
                                        cancha.get(i).isFutbol_8()));
                            }
                        }
                    }
                } else {
                    if (parqueadero.isChecked()) {
                        for (int i = 0; i < cancha.size(); i++) {
                            if (cancha.get(i).isParqueadero()) {
                                listaCancha.add(new CanchaCard(
                                        cancha.get(i).getId_cancha(),
                                        cancha.get(i).getName(),
                                        "Direccion: " + listDireccion.get(i).getTipo() + " " + listDireccion.get(i).getNum_tipo() + " " + listDireccion.get(i).getOrientacion() + " # " + listDireccion.get(i).getNum_calle() + "-" + listDireccion.get(i).getNum_casa(),
                                        "Barrio: " + listDireccion.get(i).getBarrio(),
                                        "Localidad: " + listDireccion.get(i).getLocalidad(),
                                        "",
                                        "",
                                        "",
                                        cancha.get(i).getLogo(),
                                        cancha.get(i).isParqueadero(),
                                        cancha.get(i).isFutbol_5(),
                                        cancha.get(i).isFutbol_8()));
                            }
                        }
                    } else {
                        for (int i = 0; i < cancha.size(); i++) {
                            listaCancha.add(new CanchaCard(
                                    cancha.get(i).getId_cancha(),
                                    cancha.get(i).getName(),
                                    "Direccion: " + listDireccion.get(i).getTipo() + " " + listDireccion.get(i).getNum_tipo() + " " + listDireccion.get(i).getOrientacion() + " # " + listDireccion.get(i).getNum_calle() + "-" + listDireccion.get(i).getNum_casa(),
                                    "Barrio: " + listDireccion.get(i).getBarrio(),
                                    "Localidad: " + listDireccion.get(i).getLocalidad(),
                                    "",
                                    "",
                                    "",
                                    cancha.get(i).getLogo(),
                                    cancha.get(i).isParqueadero(),
                                    cancha.get(i).isFutbol_5(),
                                    cancha.get(i).isFutbol_8()));
                        }
                    }
                }
        } else {
            if (fut_8.isChecked()) {
                if (parqueadero.isChecked()) {
                    for (int i = 0; i < cancha.size(); i++) {
                        if (listDireccion.get(i).getLocalidad().equals(localidad)) {
                            if (barrio.equals("Barrios")) {
                                if (cancha.get(i).isParqueadero() && cancha.get(i).isFutbol_8()) {
                                    listaCancha.add(new CanchaCard(
                                            cancha.get(i).getId_cancha(),
                                            cancha.get(i).getName(),
                                            "Direccion: " + listDireccion.get(i).getTipo() + " " + listDireccion.get(i).getNum_tipo() + " " + listDireccion.get(i).getOrientacion() + " # " + listDireccion.get(i).getNum_calle() + "-" + listDireccion.get(i).getNum_casa(),
                                            "Barrio: " + listDireccion.get(i).getBarrio(),
                                            "Localidad: " + listDireccion.get(i).getLocalidad(),
                                            "",
                                            "",
                                            "",
                                            cancha.get(i).getLogo(),
                                            cancha.get(i).isParqueadero(),
                                            cancha.get(i).isFutbol_5(),
                                            cancha.get(i).isFutbol_8()));
                                }
                            } else {
                                if (listDireccion.get(i).getBarrio().equals(barrio)) {
                                    if (cancha.get(i).isParqueadero() && cancha.get(i).isFutbol_8()) {
                                        listaCancha.add(new CanchaCard(
                                                cancha.get(i).getId_cancha(),
                                                cancha.get(i).getName(),
                                                "Direccion: " + listDireccion.get(i).getTipo() + " " + listDireccion.get(i).getNum_tipo() + " " + listDireccion.get(i).getOrientacion() + " # " + listDireccion.get(i).getNum_calle() + "-" + listDireccion.get(i).getNum_casa(),
                                                "Barrio: " + listDireccion.get(i).getBarrio(),
                                                "Localidad: " + listDireccion.get(i).getLocalidad(),
                                                "",
                                                "",
                                                "",
                                                cancha.get(i).getLogo(),
                                                cancha.get(i).isParqueadero(),
                                                cancha.get(i).isFutbol_5(),
                                                cancha.get(i).isFutbol_8()));
                                    }
                                }
                            }
                        }
                    }
                } else {
                    for (int i = 0; i < cancha.size(); i++) {
                        if (listDireccion.get(i).getLocalidad().equals(localidad)) {
                            if (cancha.get(i).isFutbol_8()) {
                                if (barrio.equals("Barrios")) {
                                    listaCancha.add(new CanchaCard(
                                            cancha.get(i).getId_cancha(),
                                            cancha.get(i).getName(),
                                            "Direccion: " + listDireccion.get(i).getTipo() + " " + listDireccion.get(i).getNum_tipo() + " " + listDireccion.get(i).getOrientacion() + " # " + listDireccion.get(i).getNum_calle() + "-" + listDireccion.get(i).getNum_casa(),
                                            "Barrio: " + listDireccion.get(i).getBarrio(),
                                            "Localidad: " + listDireccion.get(i).getLocalidad(),
                                            "",
                                            "",
                                            "",
                                            cancha.get(i).getLogo(),
                                            cancha.get(i).isParqueadero(),
                                            cancha.get(i).isFutbol_5(),
                                            cancha.get(i).isFutbol_8()));
                                } else {
                                    if (listDireccion.get(i).getBarrio().equals(barrio) && cancha.get(i).isFutbol_8()) {
                                        listaCancha.add(new CanchaCard(
                                                cancha.get(i).getId_cancha(),
                                                cancha.get(i).getName(),
                                                "Direccion: " + listDireccion.get(i).getTipo() + " " + listDireccion.get(i).getNum_tipo() + " " + listDireccion.get(i).getOrientacion() + " # " + listDireccion.get(i).getNum_calle() + "-" + listDireccion.get(i).getNum_casa(),
                                                "Barrio: " + listDireccion.get(i).getBarrio(),
                                                "Localidad: " + listDireccion.get(i).getLocalidad(),
                                                "",
                                                "",
                                                "",
                                                cancha.get(i).getLogo(),
                                                cancha.get(i).isParqueadero(),
                                                cancha.get(i).isFutbol_5(),
                                                cancha.get(i).isFutbol_8()));
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                if (parqueadero.isChecked()) {
                    for (int i = 0; i < cancha.size(); i++) {
                        if (listDireccion.get(i).getLocalidad().equals(localidad)) {
                            if (cancha.get(i).isParqueadero()) {
                                if (barrio.equals("Barrios")) {
                                    listaCancha.add(new CanchaCard(
                                            cancha.get(i).getId_cancha(),
                                            cancha.get(i).getName(),
                                            "Direccion: " + listDireccion.get(i).getTipo() + " " + listDireccion.get(i).getNum_tipo() + " " + listDireccion.get(i).getOrientacion() + " # " + listDireccion.get(i).getNum_calle() + "-" + listDireccion.get(i).getNum_casa(),
                                            "Barrio: " + listDireccion.get(i).getBarrio(),
                                            "Localidad: " + listDireccion.get(i).getLocalidad(),
                                            "",
                                            "",
                                            "",
                                            cancha.get(i).getLogo(),
                                            cancha.get(i).isParqueadero(),
                                            cancha.get(i).isFutbol_5(),
                                            cancha.get(i).isFutbol_8()));
                                } else {
                                    if (listDireccion.get(i).getBarrio().equals(barrio)) {
                                        listaCancha.add(new CanchaCard(
                                                cancha.get(i).getId_cancha(),
                                                cancha.get(i).getName(),
                                                "Direccion: " + listDireccion.get(i).getTipo() + " " + listDireccion.get(i).getNum_tipo() + " " + listDireccion.get(i).getOrientacion() + " # " + listDireccion.get(i).getNum_calle() + "-" + listDireccion.get(i).getNum_casa(),
                                                "Barrio: " + listDireccion.get(i).getBarrio(),
                                                "Localidad: " + listDireccion.get(i).getLocalidad(),
                                                "",
                                                "",
                                                "",
                                                cancha.get(i).getLogo(),
                                                cancha.get(i).isParqueadero(),
                                                cancha.get(i).isFutbol_5(),
                                                cancha.get(i).isFutbol_8()));
                                    }
                                }
                            }
                        }
                    }
                } else {
                    for (int i = 0; i < cancha.size(); i++) {
                        if (listDireccion.get(i).getLocalidad().equals(localidad)) {
                            if (barrio.equals("Barrios")) {
                                listaCancha.add(new CanchaCard(
                                        cancha.get(i).getId_cancha(),
                                        cancha.get(i).getName(),
                                        "Direccion: " + listDireccion.get(i).getTipo() + " " + listDireccion.get(i).getNum_tipo() + " " + listDireccion.get(i).getOrientacion() + " # " + listDireccion.get(i).getNum_calle() + "-" + listDireccion.get(i).getNum_casa(),
                                        "Barrio: " + listDireccion.get(i).getBarrio(),
                                        "Localidad: " + listDireccion.get(i).getLocalidad(),
                                        "",
                                        "",
                                        "",
                                        cancha.get(i).getLogo(),
                                        cancha.get(i).isParqueadero(),
                                        cancha.get(i).isFutbol_5(),
                                        cancha.get(i).isFutbol_8()));
                            } else {
                                if (listDireccion.get(i).getBarrio().equals(barrio)) {
                                    listaCancha.add(new CanchaCard(
                                            cancha.get(i).getId_cancha(),
                                            cancha.get(i).getName(),
                                            "Direccion: " + listDireccion.get(i).getTipo() + " " + listDireccion.get(i).getNum_tipo() + " " + listDireccion.get(i).getOrientacion() + " # " + listDireccion.get(i).getNum_calle() + "-" + listDireccion.get(i).getNum_casa(),
                                            "Barrio: " + listDireccion.get(i).getBarrio(),
                                            "Localidad: " + listDireccion.get(i).getLocalidad(),
                                            "",
                                            "",
                                            "",
                                            cancha.get(i).getLogo(),
                                            cancha.get(i).isParqueadero(),
                                            cancha.get(i).isFutbol_5(),
                                            cancha.get(i).isFutbol_8()));
                                }
                            }
                        }
                    }

                }
            }
        }
        if (dia.equals("Dia") || hora.equals("Hora")){
            mostrarData(listaCancha);
        }else {
            obtenerPrecios( hora, dia);
        }

    }

    private void obtenerPrecios(String hora, String dia) {
        int horaConsulta = Integer.valueOf(hora);
        for (int i=0;i<listaCancha.size();i++){
            getPrecioCanchas(listaCancha.get(i).getId_cancha(), dia,horaConsulta, listaCancha, i);
        }
    }


    //listDireccion.forEach(p->System.out.println(p.toString()));

    public void getPrecioCanchas(long id_cancha, String dia, int hora, ArrayList<CanchaCard> listaCancha, int i){
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
                String hora = String.valueOf(precios.getHora());
                String precio = String.valueOf(precios.getPrecio());
                listaCancha.get(i).setDia(precios.getDia());
                listaCancha.get(i).setHora(hora+":00");
                listaCancha.get(i).setPrecios("$"+precio);
                //filtrarFecha(listPrecios, listaCancha);
            }
            @Override
            public void onFailure(Call<Precios> call, Throwable t) {
                System.out.println("No se conecto:"+ t.getMessage());

            }
        });
    }



    public void getCanchasApi(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constante.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        productAPI = retrofit.create(ProductAPI.class);
        Call<ArrayList<Cancha>> call = productAPI.getCanchas();
        call.enqueue(new Callback<ArrayList<Cancha>>() {
            @Override
            public void onResponse(Call<ArrayList<Cancha>> call, Response<ArrayList<Cancha>> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(getActivity(), "Fallo: "+response.message(), Toast.LENGTH_SHORT).show();
                }
                cancha = response.body();
                getCanchasDireccionApi();
            }
            @Override
            public void onFailure(Call<ArrayList<Cancha>> call, Throwable t) {
                Toast.makeText(getActivity(), "Fallo: "+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void getCanchasDireccionApi(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constante.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        productAPI = retrofit.create(ProductAPI.class);
        Call<ArrayList<Direccion>> call = productAPI.getDireccion();
        call.enqueue(new Callback<ArrayList<Direccion>>() {
            @Override
            public void onResponse(Call<ArrayList<Direccion>> call, Response<ArrayList<Direccion>> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(getActivity(), "Fallo: "+response.message(), Toast.LENGTH_SHORT).show();
                }
                listDireccion = response.body();
                cargarLista(localidad, barrio, dia, hora);

            }

            @Override
            public void onFailure(Call<ArrayList<Direccion>> call, Throwable t) {

            }
            });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        canchaAdapter.filtrado(newText);
        return false;
    }


}