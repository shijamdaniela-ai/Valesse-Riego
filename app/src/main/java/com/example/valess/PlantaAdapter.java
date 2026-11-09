package com.example.valess;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class PlantaAdapter extends RecyclerView.Adapter<PlantaAdapter.PlantaViewHolder> {

    private List<Planta> listaPlantas = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Planta planta);
        void onDeleteClick(Planta planta);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setPlantas(List<Planta> plantas) {
        this.listaPlantas = plantas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlantaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_planta, parent, false);
        return new PlantaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantaViewHolder holder, int position) {
        Planta plantaActual = listaPlantas.get(position);
        holder.tvNombre.setText(plantaActual.getNombre());
        holder.tvUbicacion.setText("Ubicación: " + plantaActual.getUbicacion());
        holder.tvHumedad.setText("💧 Humedad: " + plantaActual.getHumedadActual() + "%");
        holder.tvHumedadOptima.setText("🎯 Óptimo: " + plantaActual.getHumedadOptima() + "%");

        holder.itemView.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, activity_monitoreo.class);
            intent.putExtra("PLANTA_NOMBRE", plantaActual.getNombre());
            context.startActivity(intent);
        });

        holder.btnEliminar.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteClick(plantaActual);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaPlantas.size();
    }

    static class PlantaViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNombre, tvUbicacion, tvHumedad, tvHumedadOptima;
        private ImageButton btnEliminar;

        public PlantaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombreItem);
            tvUbicacion = itemView.findViewById(R.id.tvUbicacionItem);
            tvHumedad = itemView.findViewById(R.id.tvHumedadItem);
            tvHumedadOptima = itemView.findViewById(R.id.tvHumedadOptimaItem);
            btnEliminar = itemView.findViewById(R.id.btnEliminarItem);
        }
    }
}