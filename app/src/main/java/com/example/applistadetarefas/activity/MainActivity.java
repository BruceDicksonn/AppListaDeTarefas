package com.example.applistadetarefas.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import com.example.applistadetarefas.adapter.AdapterTarefa;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.applistadetarefas.databinding.ActivityMainBinding;
import com.example.applistadetarefas.helper.TarefaDAO;
import com.example.applistadetarefas.listener.RecyclerItemClickListener;
import com.example.applistadetarefas.model.Tarefa;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    AdapterTarefa adapter;
    List<Tarefa> listaTarefas = new ArrayList<>();

    TarefaDAO dao = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        carregarListaTarefas();
        dao = new TarefaDAO(getApplicationContext());

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), AdicionarTarefaActivity.class);
                startActivity(intent);

            }
        });

    }

    @Override
    protected void onStart() {
        carregarListaTarefas();
        super.onStart();
    }

    public void carregarListaTarefas(){

        TarefaDAO dao = new TarefaDAO(this);
        listaTarefas = dao.listar();

        if(adapter != null) adapter.setListaTarefas(listaTarefas);
        else initComponents();

    }

    private void initComponents() {

        adapter = new AdapterTarefa(listaTarefas);
        binding.contentMain.recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,
                false));

        // adiciona um divisor a cada item inflado
        binding.contentMain.recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        binding.contentMain.recyclerView.setAdapter(adapter);

        binding.contentMain.recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                this,
                binding.contentMain.recyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        Tarefa tarefa = listaTarefas.get(position);

                        Intent intent = new Intent(getApplicationContext(), AdicionarTarefaActivity.class);
                        intent.putExtra("tarefa", tarefa);

                        startActivity(intent);

                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                        Tarefa tarefa = listaTarefas.get(position);
                        showDialogSwitch(tarefa);

                    }

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                }
        ));

    }

    public void showDialogSwitch(Tarefa tarefa){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Deseja excluir essa tarefa?");
        builder.setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(dao.deletar(tarefa)) {

                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Tarefa excluída com sucesso!", Toast.LENGTH_SHORT).show();
                    carregarListaTarefas();

                }
            }
        });
        builder.setNegativeButton("Voltar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create();
        builder.show();
    }

}