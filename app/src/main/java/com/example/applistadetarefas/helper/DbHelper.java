package com.example.applistadetarefas.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import androidx.annotation.Nullable;

/**
 *  Essa classe é muito importante, pois, tem o controle e a ciência
 *  se o app está sendo instalado pela primeira vez ou se o mesmo está
 *  apenas sendo atualizado. Sabendo disso, ela consegue tomar uma decisão
 *  para chamar o método necessário seja para criar ou apenas atualizar
 *  o banco de dados.
 *
 *  O que gera esse controle e ciência para a classe são justamente as constantes
 *  que passamos para o construtor de sua classe mãe, sendo elas: Nome do banco de dados,
 *  CursorFactory e a versão atual do db. O factory como padrão podemos passar como null.
 *
 * **/
public class DbHelper extends SQLiteOpenHelper {

    public static String DB_NAME = "DB_TAREFAS";
    public static int DB_VERSION = 1;

    public DbHelper(@Nullable Context context) {
        super(context, DB_NAME, null , DB_VERSION);
    }

    /**
     * Esse método é chamado sempre que seu app for instalado pela primeira vez.
     * Sua responsabilidade é basicamente criar o banco de dados juntamente com
     * toda sua estrutura.
     *
     * */
    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE IF NOT EXISTS tarefas("
                +"id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT NOT NULL)";

        try {

            db.execSQL(sql);
            Log.i("Info_DB", "Tabela criada com sucesso!");

        } catch (Exception e) {
            Log.e("Info_DB", "Erro ao criar a tabela: " + e.getMessage());
        }

    }


    /**
     * Esse método é chamado quando já o banco já estiver criado.
     * Sua responsabilidade é atualizar algumas estruturas ou dados no banco de dados
     * caso a versão do app mude. É importante fazermos isso para que não tenhamos que
     * reinstalar o banco de dados inteiro a cada atualização lançada.
     *
     * */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "ALTER TABLE tarefas ADD atualizado BOOLEAN DEFAULT(true)";

        try {
            db.execSQL(sql);
            //onCreate(db); usamos quando queremos executar uma acao e após isso criar o banco novamente
        } catch (Exception e) {
            Log.e("Info_DB", "Erro ao adicionar coluna nova na tabela: " + e.getMessage());
        }

    }

}
