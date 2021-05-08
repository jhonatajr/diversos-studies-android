package com.example.mobile_schorgan.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String dbName = "schorgan";
    private static final int DATABASE_VERSION  = 58;



    public DatabaseHelper(@Nullable Context context) {
        super(context,dbName,null,DATABASE_VERSION );
    }


    private static final String TABELA_CLIENTES = "create table IF NOT EXISTS clientes"
            + "(clienteid INTEGER PRIMARY KEY AUTOINCREMENT, datacadastro date, nome text, telefone text , cpf text, cnpj text," +
            "email text, logradouro text, cep text, complemento text , bairro text, cidade text," +
            "uf CHARACTER(2), numero int)";

    private static final String TABELA_OCORRENCIAS = "create table IF NOT EXISTS ocorrencia"
            + "(ocrrenciaid INTEGER PRIMARY KEY AUTOINCREMENT, datalancamento date, dataocorrencia date," +
            " descricao text, titulo text , gravidade text, clienteid int, tipoid int)";


    private static final String TABELA_TIPOS = "create table IF NOT EXISTS tipo"
            + "(tipoid INTEGER PRIMARY KEY AUTOINCREMENT, descricao text)";

    private static final String TABELA_USUARIO = "create table IF NOT EXISTS usuarios"
            + "(usuarioid INTEGER PRIMARY KEY AUTOINCREMENT, nome text, email text, senha text)";

    private static DatabaseHelper instance;

    @Override
    public synchronized SQLiteDatabase getWritableDatabase() {
        SQLiteDatabase database = super.getWritableDatabase();
        database.acquireReference();
        return database;
    }

    @Override
    public synchronized SQLiteDatabase getReadableDatabase() {
        SQLiteDatabase database = super.getReadableDatabase();
        database.acquireReference();
        return database;
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABELA_CLIENTES);
        db.execSQL(TABELA_OCORRENCIAS);
        db.execSQL(TABELA_TIPOS);
        db.execSQL(TABELA_USUARIO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("Chama","Atualizado banco, sua antiga versão é: " + oldVersion);
        if (oldVersion < 59) {
            db.execSQL(TABELA_USUARIO);
        }
    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public synchronized void close () {
        super.close();
    }

}
