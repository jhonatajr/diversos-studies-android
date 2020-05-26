package com.example.diversos_estudos.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.diversos_estudos.models.Aluno;

import java.util.ArrayList;
import java.util.List;

public class AlunoDAO extends SQLiteOpenHelper {
    public AlunoDAO(@Nullable Context context) {
        super(context, "Agenda", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Alunos (" +
                "id INTEGER PRIMARY KEY," +
                "nome TEXT NOT NULL," +
                "endereco TEXT," +
                "telefone TEXT," +
                "site TEXT," +
                "nota REAL" +
                ");";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Alunos";
        db.execSQL(sql); // atualizações de banco por versão, colunas atualizada e etc..
        onCreate(db);
    }

    public void insere(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();// referencia do banco
        ContentValues dados = new ContentValues();
        dados.put("nome", aluno.getNome());
        dados.put("endereco", aluno.getEndereco());
        dados.put("telefone", aluno.getTelefone());
        dados.put("site", aluno.getSite());
        dados.put("nota", aluno.getNota());

        db.insert("Alunos", null, dados);
    }

    public List<Aluno> buscaAlunos() {
        String sql = "SELECT * FROM Alunos;";
        SQLiteDatabase db = getWritableDatabase();
        Cursor ponteiro = db.rawQuery(sql, null); // ponteiro, meio q referenciando as tuplas q retornaram
        List<Aluno> alunos = new ArrayList<Aluno>(); // lista de alunos para retornar
        while (ponteiro.moveToNext()) { // faz o while para montar o objeto para passar para a lista
            Aluno aluno = new Aluno();
            aluno.setId(ponteiro.getLong(ponteiro.getColumnIndex("id")));
            aluno.setNome(ponteiro.getString(ponteiro.getColumnIndex("nome")));
            aluno.setEndereco(ponteiro.getString(ponteiro.getColumnIndex("endereco")));
            aluno.setTelefone(ponteiro.getString(ponteiro.getColumnIndex("telefone")));
            aluno.setSite(ponteiro.getString(ponteiro.getColumnIndex("site")));
            aluno.setNota(ponteiro.getDouble(ponteiro.getColumnIndex("nota")));

            alunos.add(aluno);
        }
        ponteiro.close();
        return alunos;

    }

    public void deletar(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();
        String [] params = {aluno.getId().toString()}; // faz o sqlite fazer o delete, e não na mão..
        db.delete("Alunos", "id = ?",params);
    }
}