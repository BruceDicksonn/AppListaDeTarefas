package com.example.applistadetarefas.helper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.applistadetarefas.model.Tarefa;

import java.util.ArrayList;
import java.util.List;

public class TarefaDAO implements iTarefaDAO {

    SQLiteDatabase write;
    SQLiteDatabase read;

    public TarefaDAO(Context context) {

        DbHelper db = new DbHelper(context);

        write = db.getWritableDatabase(); // retorna um objeto que permite executar somente ações de escrita no banco
        read = db.getReadableDatabase(); // retorna um objeto que permite executar somente ações de leitura no banco
    }

    @Override
    public boolean salvar(Tarefa tarefa) {

        try {

            /**
            *    Argumentos do metodo insert:
             *
             *   table: nome da tabela que iremos inserir o novo registro
             *
             *   nullColumnHack: nome da coluna que receberá null como default caso não tenha um valor atribuído
             *
             *   contentValues: um objeto chave/valor similar a um Bundle ao qual atribuiremos os valores que serão
             *   inseridos na tabela. A key desse objeto representa o nome da coluna, e o value representa o conteúdo
             *   que será inserido naquela determinada coluna.
             *
            **/

            ContentValues cv = new ContentValues();
            cv.put("nome",tarefa.getNomeTarefa());

            write.insert("tarefas", null, cv);

            Log.i("Info_DB", "Tarefa salva com sucesso!");


        } catch (Exception e) {
            Log.e("Info_DB", "Erro ao salvar tarefa:    " + e.getMessage());
            return false;
        }

        return true;
    }

    /**
     * O contentValues quando passamos para o update() age como um set e concatena
     * nome da coluna e valor atual do mesmo.
     *
     * **/
    @Override
    public boolean atualizar(Tarefa tarefa) {

        String where = "id = ?";

        try {

            ContentValues values = new ContentValues();
            values.put("nome", tarefa.getNomeTarefa());

            write.update("tarefas", values, where, new String[]{String.valueOf(tarefa.getId())});
            return true;

        } catch (Exception e) {
            Log.e("Info_DB", e.getMessage());
        }

        return false;
    }

    @Override
    public boolean deletar(Tarefa tarefa) {

        String where = "id = ?";

        try {

            write.delete("tarefas", where, new String[]{String.valueOf(tarefa.getId())});
            return true;

        } catch (Exception e) {
            Log.e("Info_DB", e.getMessage());
        }

        return false;
    }


    /**
     *
     *  Como read é um objeto que se responsabilizará por ações que envolvem
     *  leitura em nosso banco de dados, poderíamos passar os comandos sql
     *  diretamente no execSQL() ou podemos usar seus métodos internos.
     *
     *  O método query internamente monta um select from onde seus argumentos
     *  são respectivamente:
     *
     *  table: nome da tabela que faremos a busca;
     *  columns: as colunas que queremos filtrar;
     *
     *  selection: equivale a cláusula where;
     *  selectionArgs: os valores que serão incluídos no where, por ex:
     *
     *  String where = "_id = ? and nome = ?";
     *  cursor.query("tarefas", null, where, new String[]{"5","Bruce Dickson"}, null, null,null);
     *
     *  o selectionArgs é um array com os valores que substituirão os placeholders do where;
     *
     *  groupBy: cláusula groupBy caso precise agrupar os dados;
     *  having: cláusula having caso precise impôr uma condição ao groupBy;
     *  orderBy: para ordernar como os dados serão montados;
     *
     *  Se quiséssemos montar nossa própria query, deveríamos fazer uso do método
     *  rawQuery(), por exemplo:
     *
     *  String sql = "SELECT * FROM pessoas where _id = ? and nome = ?";
     *  Cursor cursor = read.rawQuery(sql, new String[]{"5","Bruce Dickson"});
     *
     *
     * **/
    @SuppressLint("Range")
    @Override
    public List<Tarefa> listar() {

        List<Tarefa> tarefas = new ArrayList<>();

        try {

            Cursor cursor = read.query("tarefas", null, null, null, null, null, null);
            cursor.moveToFirst();

            while(!cursor.isAfterLast()) {

                int id = cursor.getInt( cursor.getColumnIndex("id") );
                String nome = cursor.getString( cursor.getColumnIndex("nome") );

                Tarefa tarefa = new Tarefa(id,nome);
                tarefas.add(tarefa);

                cursor.moveToNext();
            }

            cursor.close();

        } catch (Exception e) {
            Log.e("Info_DB", e.getMessage());
        }

        return tarefas;

    }
}
