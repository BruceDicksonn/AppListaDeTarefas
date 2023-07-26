package com.example.applistadetarefas.helper;

import com.example.applistadetarefas.model.Tarefa;

import java.util.ArrayList;
import java.util.List;

public interface iTarefaDAO {

    public boolean salvar(Tarefa tarefa);
    public boolean atualizar(Tarefa tarefa);
    public boolean deletar(Tarefa tarefa);
    public List<Tarefa> listar();

}
