package com.example.applistadetarefas.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.applistadetarefas.R;
import com.example.applistadetarefas.model.Tarefa;
import java.util.List;

public class AdapterTarefa extends RecyclerView.Adapter<AdapterTarefa.ViewHolder> {

    List<Tarefa> listaTarefas;

    public AdapterTarefa(List<Tarefa> listaTarefas) {
        this.listaTarefas = listaTarefas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_tarefa, parent, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Tarefa tarefa = listaTarefas.get(position);
        holder.tarefa.setText(tarefa.getNomeTarefa());

    }

    @Override
    public int getItemCount() {
        return listaTarefas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tarefa;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tarefa = itemView.findViewById(R.id.tarefa);
        }
    }

    public void setListaTarefas(List<Tarefa> listaTarefas){
        this.listaTarefas = listaTarefas;
        notifyDataSetChanged();
    }
}
