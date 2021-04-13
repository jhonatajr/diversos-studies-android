package com.example.mobile_schorgan.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.mobile_schorgan.models.Aluno;

import java.util.ArrayList;
import java.util.List;


public class AlunoDAO extends AbstractDAO {


    public AlunoDAO(Context context) {
        super(context);
    }


    public final String NOME_TABELA = "clientes";
    public final String[] COLUNAS_TABELA = {"id", "nome", "endereco", "telefone",
            "site", "nota"};


    public void insere(Aluno aluno) {
        ContentValues dados = pegaDados(aluno);
        db.insert("clientes", null, dados);
    }

    private ContentValues pegaDados(Aluno aluno) {
        ContentValues dados = new ContentValues();
        dados.put("nome", aluno.getNome());
        dados.put("endereco", aluno.getEndereco());
        dados.put("telefone", aluno.getTelefone());
        dados.put("site", aluno.getSite());
        dados.put("nota", aluno.getNota());
        return dados;
    }

    public List<Aluno> buscaAlunos() {
        String sql = "SELECT * FROM clientes;";
        Cursor ponteiro = db.rawQuery(sql, null);
        List<Aluno> alunos = new ArrayList<Aluno>();
        while (ponteiro.moveToNext()) {
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
        String[] params = {aluno.getId().toString()};
        db.delete("clientes", "id = ?", params);
    }

    public void altera(Aluno aluno) {
        String[] params = {aluno.getId().toString()};
        ContentValues dados = pegaDados(aluno);
        db.update("clientes", dados, "id = ?", params);

    }
}