package com.miprimeraapp.pruebaconexion.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.miprimeraapp.pruebaconexion.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReservaAdapter extends RecyclerView.Adapter<ReservaAdapter.ViewHolder>implements View.OnClickListener {
private LayoutInflater mInflater;
private ArrayList<ReservaCard> listaReserva;
private ArrayList<ReservaCard> listOriginal;
private View.OnClickListener listener;




public ReservaAdapter(ArrayList<ReservaCard> listaReserva, Context context)  {
        this.mInflater = LayoutInflater.from(context);
        this.listaReserva=listaReserva;
        this.listOriginal = new ArrayList<>();
        listOriginal.addAll(listaReserva);
        }

@Override
public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.card_reserva,parent,false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
        }
public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
        }


@Override
public void onBindViewHolder(@NonNull ReservaAdapter.ViewHolder holder, int position) {
        String nombre = listaReserva.get(position).getNombre();
        String fecha = listaReserva.get(position).getFecha();
        String estado = listaReserva.get(position).getEstado();
        String pago= listaReserva.get(position).getPago();
        String imagen = listaReserva.get(position).getLogo();

        holder.nombre.setText(nombre);
        holder.fecha.setText(fecha);
        holder.estado.setText(estado);
        holder.pago.setText(pago);
        Picasso.get().load(imagen).into(holder.imagen);
        }

public void filtrado(String textoBuscar){
        int longitud = textoBuscar.length();
        System.out.println(longitud);
        if (longitud==0){
            listaReserva.clear();
            listaReserva.addAll(listOriginal);
        }else{
        List<ReservaCard> coleccion = listaReserva.stream()
        .filter(i ->i.getNombre().toLowerCase()
        .contains(textoBuscar.toLowerCase())).collect(Collectors.toList());
            listaReserva.clear();
            listaReserva.addAll(coleccion);
        }
        notifyDataSetChanged();
        }

@Override
public int getItemCount() {
        return listaReserva.size();
        }

@Override
public void onClick(View view) {
        if (listener!=null){
        listener.onClick(view);
        }
}

public class ViewHolder  extends RecyclerView.ViewHolder{
    TextView nombre, fecha, estado, pago;
    ImageView imagen;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        nombre = itemView.findViewById(R.id.tvNombreReserva);
        fecha = itemView.findViewById(R.id.tvFechaHora);
        estado = itemView.findViewById(R.id.tvEstadoReserva);
        pago = itemView.findViewById(R.id.tvEstadoPagoReserva);
        imagen = itemView.findViewById(R.id.imageCardReserva);

    }
}



}
