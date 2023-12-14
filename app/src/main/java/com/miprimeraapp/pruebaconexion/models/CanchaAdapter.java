package com.miprimeraapp.pruebaconexion.models;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.miprimeraapp.pruebaconexion.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CanchaAdapter  extends RecyclerView.Adapter<CanchaAdapter.ViewHolder>implements View.OnClickListener {
    private LayoutInflater mInflater;
    private ArrayList<CanchaCard> listaCancha;
    private ArrayList<CanchaCard> listOriginal;
    private View.OnClickListener listener;




    public CanchaAdapter(ArrayList<CanchaCard> listaCancha, Context context)  {
        this.mInflater = LayoutInflater.from(context);
        this.listaCancha=listaCancha;
        this.listOriginal = new ArrayList<>();
        listOriginal.addAll(listaCancha);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.card_cancha_principal,parent,false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }
    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }


    @Override
    public void onBindViewHolder(@NonNull CanchaAdapter.ViewHolder holder, int position) {
        String nombre = listaCancha.get(position).getName();
        String direccion = listaCancha.get(position).getDireccion();
        String barrio = listaCancha.get(position).getBarrio();
        String localidad = listaCancha.get(position).getLocalidad();
        String dia = listaCancha.get(position).getDia();
        String hora = String.valueOf(listaCancha.get(position).getHora());
        String precio = listaCancha.get(position).getPrecios();
        String imagen = listaCancha.get(position).getImagenId();

        holder.nombre.setText(nombre);
        holder.direccion.setText(direccion);
        holder.barrio.setText(barrio);
        holder.localidad.setText(localidad);
        holder.dia.setText(dia);
        holder.hora.setText(hora);
        holder.precio.setText(precio);
        Picasso.get().load(imagen).into(holder.imagen);
    }

    public void filtrado(String textoBuscar){
        int longitud = textoBuscar.length();
        System.out.println(longitud);
        if (longitud==0){
            listaCancha.clear();
            listaCancha.addAll(listOriginal);
        }else{
            List<CanchaCard> coleccion = listaCancha.stream()
                    .filter(i ->i.getName().toLowerCase()
                    .contains(textoBuscar.toLowerCase())).collect(Collectors.toList());
            listaCancha.clear();
            listaCancha.addAll(coleccion);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listaCancha.size();
    }

    @Override
    public void onClick(View view) {
        if (listener!=null){
            listener.onClick(view);
        }

    }

    public class ViewHolder  extends RecyclerView.ViewHolder{
        TextView nombre, direccion, barrio, localidad, dia, hora, precio;
        ImageView imagen;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.tvNombre);
            direccion = itemView.findViewById(R.id.tvDireccion);
            barrio = itemView.findViewById(R.id.tvBarrio);
            localidad = itemView.findViewById(R.id.tvLocalidad);
            imagen = itemView.findViewById(R.id.imageCardCancha);
            dia = itemView.findViewById(R.id.tvDia);
            hora = itemView.findViewById(R.id.tvHora);
            precio = itemView.findViewById(R.id.tvPrecio);


        }
    }



}
