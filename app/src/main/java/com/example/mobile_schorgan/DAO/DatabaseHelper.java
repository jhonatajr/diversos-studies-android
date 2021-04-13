package com.example.mobile_schorgan.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String dbName = "schorgan";
    private static final int dbVersion = '1';



    public DatabaseHelper(@Nullable Context context) {
        super(context,dbName,null,dbVersion);
    }


    private static final String TABELA_CLIENTES = "create table IF NOT EXISTS clientes"
            + "(id INTEGER, nome text, endereco text, telefone text , site text, nota REAL,"
            + "PRIMARY KEY (id))";

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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

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
