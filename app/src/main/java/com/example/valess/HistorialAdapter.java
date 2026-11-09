package com.example.valess;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HistorialAdapter extends RecyclerView.Adapter<HistorialAdapter.HistorialViewHolder> {

    private List<HistorialRiego> listaHistorial = new ArrayList<>();

    public void setHistorial(List<HistorialRiego> historial) {
        this.listaHistorial = historial;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HistorialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_historial, parent, false);
        return new HistorialViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistorialViewHolder holder, int position) {
        HistorialRiego item = listaHistorial.get(position);

        if (holder.tvNombrePlanta != null) {
            holder.tvNombrePlanta.setText(item.getNombrePlanta());
        }
        if (holder.tvFechaHora != null) {
            holder.tvFechaHora.setText(item.getFechaHora());
        }
        if (holder.tvModo != null) {
            holder.tvModo.setText(item.getTipoRiego());
        }
    }

    @Override
    public int getItemCount() {
        return listaHistorial.size();
    }

    static class HistorialViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombrePlanta, tvFechaHora, tvModo;

        public HistorialViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombrePlanta = itemView.findViewById(R.id.tvNombrePlantaHistorial);
            tvFechaHora = itemView.findViewById(R.id.tvFechaHoraHistorial);
            tvModo = itemView.findViewById(R.id.tvModoHistorial);
        }
    }
}