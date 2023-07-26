package com.example.applistadetarefas.model;

import java.io.Serializable;

/**
*  Nossos models quando são serializáveis, podem ser
*  enviados de intent para intent sem qualquer tipo
 * prévio de serialização. Por ex, ao definir Tarefa como
 * Serializable poderemos enviar uma de suas instâncias
 * de uma activity para outra somente o passando como
 * extra, sem precisarmos convertê-lo para json antes.
 * O android reconhece que a classe implementa o Serializable
 * e nos permite passar uma instância da mesma como extra.
 *
 * O onItemCLick da MainActivity tem um exemplo disso:
**/
public class Tarefa implements Serializable {

    private int id;
    private String nomeTarefa;

    public Tarefa(int id,String nome) {
        this.id = id;
        this.nomeTarefa = nome;
    }

    public Tarefa(String nome) {
        this.nomeTarefa = nome;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeTarefa() {
        return nomeTarefa;
    }

    public void setNomeTarefa(String nomeTarefa) {
        this.nomeTarefa = nomeTarefa;
    }
}
