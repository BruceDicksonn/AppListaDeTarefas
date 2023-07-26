package com.example.applistadetarefas.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.applistadetarefas.R;
import com.example.applistadetarefas.databinding.ActivityAdicionarTarefaBinding;
import com.example.applistadetarefas.helper.TarefaDAO;
import com.example.applistadetarefas.model.Tarefa;

public class AdicionarTarefaActivity extends AppCompatActivity {

    ActivityAdicionarTarefaBinding binding;
    Tarefa tarefaAtual = null;
    TarefaDAO dao = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdicionarTarefaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dao = new TarefaDAO(getApplicationContext());
        Bundle extras = getIntent().getExtras();
        if(extras != null) {

            /**
             *
             * O getSerialazable(key) já nos traz o objeto serializado
             * e pronto para uso. Não precisaremos nem mesmo descerializar ou algo do tipo.
             * Basta fazermos um cast para o tipo, pois, recebemos um objeto do tipo java.io.Serializable.
             *
            **/
            tarefaAtual = (Tarefa) extras.getSerializable("tarefa");
            binding.tarefa.setText(tarefaAtual.getNomeTarefa());

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.salvarTarefa) {
            if(tarefaAtual != null) {
                if(!checkEditTarefa()) return false;

                tarefaAtual.setNomeTarefa(binding.tarefa.getText().toString());
                if(dao.atualizar(tarefaAtual)){
                    Toast.makeText(this, "Tarefa atualizada com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            } else {
                if(!checkEditTarefa()) return false;

                Tarefa tarefa = new Tarefa(binding.tarefa.getText().toString());
                if(dao.salvar(tarefa))
                {
                    Toast.makeText(this, "Salvo com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean checkEditTarefa() {
        if(binding.tarefa.getText().toString().isEmpty()) {

            Toast.makeText(this,"Preencha o campo Tarefa", Toast.LENGTH_SHORT).show();
            return false;

        }
        return true;
    }

}