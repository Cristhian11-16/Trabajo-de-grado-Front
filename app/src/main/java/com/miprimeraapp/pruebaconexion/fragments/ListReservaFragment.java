package com.miprimeraapp.pruebaconexion.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.miprimeraapp.pruebaconexion.R;
import com.miprimeraapp.pruebaconexion.constante.Constante;
import com.miprimeraapp.pruebaconexion.interfaces.ProductAPI;
import com.miprimeraapp.pruebaconexion.models.Cancha;
import com.miprimeraapp.pruebaconexion.models.Reserva;
import com.miprimeraapp.pruebaconexion.models.ReservaAdapter;
import com.miprimeraapp.pruebaconexion.models.ReservaCard;
import com.miprimeraapp.pruebaconexion.models.Usuario;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListReservaFragment extends Fragment implements SearchView.OnQueryTextListener{
    RecyclerView recyclerViewReserva;
    ProductAPI productAPI;
    String correo, todayDate;
    ArrayList<Cancha> cancha;
    ReservaAdapter reservaAdapter;
    ArrayList<Reserva> reservas;
    ArrayList<ReservaCard> listaReservaCards;
    EditText etFechaReserva;
    SearchView searchView;
    Spinner spinnerEstado;
    ImageButton calendar, ic_limpiar;
    int diaMes,diaSemana, mesCalendar, añoCalendar;



    long id_usuario;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reserva, container, false);
        Bundle datosRecuperados = getArguments();
        correo = datosRecuperados.getString("correo");
        recyclerViewReserva = view.findViewById(R.id.recyclerViewReserva);
        searchView = view.findViewById(R.id.buscadorPagReserva);
        calendar = view.findViewById(R.id.ic_calendar);
        ic_limpiar = view.findViewById(R.id.ic_limpiar);
        etFechaReserva = view.findViewById(R.id.etFechaReserva);
        spinnerEstado = view.findViewById(R.id.spinnerEstado);
        searchView.setOnQueryTextListener(this);
        listaReservaCards = new ArrayList<>();
        getUsuario(correo);
        initSpinner();
        calendar.setOnClickListener(new View.OnClickListener() {
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
                        etFechaReserva.setText(year+"-"+(month+1)+"-"+dayOfMonth);
                        filtrarFecha(correo);
                    }
                }
                        ,añoCalendar,mesCalendar,diaMes);
                datePickerDialog.show();
                datePickerDialog.getDatePicker().setMinDate(2023);
            }
        });
        ic_limpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrarData();
                getUsuario(correo);
                etFechaReserva.setText("");
                spinnerEstado.setSelection(0);
            }
        });
        return view;
    }

    private void filtrarFecha( String correo) {
        borrarData();
        getUsuario(correo);
    }

    private void initSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.estadoReserva,R.layout.spinner_basico);
        spinnerEstado.setAdapter(adapter);
    }
    private void borrarData() {
        listaReservaCards.clear();
        reservaAdapter.notifyDataSetChanged();
    }

    private void initRecyclerView(long idUsuario) {
        getReservas(idUsuario);
    }
    public void cargasLista(ArrayList<Reserva> reservas, ArrayList<Cancha> cancha) {
        String fecha = String.valueOf(etFechaReserva.getText());
        System.out.println(fecha);
        if (fecha.equals("")){
            for (int i=0;i<reservas.size();i++){
                for (int j=0;j<cancha.size();j++){
                    if (reservas.get(i).getId_cancha()==cancha.get(j).getId_cancha()){
                        listaReservaCards.add(new ReservaCard(
                                        cancha.get(j).getName(),
                                        "Fecha: "+reservas.get(i).getFecha()+" - Hora: "+reservas.get(i).getHora()+":00",
                                        "Estado: "+reservas.get(i).getEstado(),
                                        "Pago: "+"Aprobado",
                                        cancha.get(j).getLogo()
                                )
                        );
                    }
                }

            }
        }else {
            for (int i = 0; i < reservas.size(); i++) {
                for (int j = 0; j < cancha.size(); j++) {
                    if (reservas.get(i).getId_cancha() == cancha.get(j).getId_cancha()) {
                        if (reservas.get(i).getFecha().equals(fecha)){
                            listaReservaCards.add(new ReservaCard(
                                            cancha.get(j).getName(),
                                            "Fecha: " + reservas.get(i).getFecha() + " - Hora: " + reservas.get(i).getHora() + ":00",
                                            "Estado: " + reservas.get(i).getEstado(),
                                            "Pago: " + "Aprobado",
                                            cancha.get(j).getLogo()
                                    )
                            );
                        }
                    }
                }
            }
        }

        mostrarDatos(listaReservaCards);

    }


    private void mostrarDatos(ArrayList<ReservaCard> reservasM) {
        recyclerViewReserva.setLayoutManager(new LinearLayoutManager(getContext()));
        reservaAdapter= new ReservaAdapter(reservasM, getContext());
        recyclerViewReserva.setAdapter(reservaAdapter);

    }

    private void getReservas(long id_Usuario) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constante.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        productAPI = retrofit.create(ProductAPI.class);
        Call<ArrayList<Reserva>> call = productAPI.getReservas(id_Usuario);
        call.enqueue(new Callback<ArrayList<Reserva>>() {
            @Override
            public void onResponse(Call<ArrayList<Reserva>> call, Response<ArrayList<Reserva>> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(getActivity(), "Fallo: "+response.message(), Toast.LENGTH_SHORT).show();
                }
                reservas = response.body();
                getCanchasApi(reservas);


            }

            @Override
            public void onFailure(Call<ArrayList<Reserva>> call, Throwable t) {
                Toast.makeText(getActivity(), "Fallo: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
                initRecyclerView(usuario.getId_usuario());

            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
            }
        });
    }
    public void getCanchasApi(ArrayList<Reserva> reservas){
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
                cargasLista(reservas,cancha);
            }
            @Override
            public void onFailure(Call<ArrayList<Cancha>> call, Throwable t) {
                Toast.makeText(getActivity(), "Fallo: "+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        reservaAdapter.filtrado(newText);
        return false;
    }
}