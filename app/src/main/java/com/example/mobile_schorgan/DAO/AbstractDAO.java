package com.example.mobile_schorgan.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public abstract class AbstractDAO {

    protected SQLiteDatabase db;
    protected Context context;

    protected AbstractDAO(Context context) {
        this.context = context;
        DatabaseHelper helper = DatabaseHelper.getInstance(context);
        db = helper.getWritableDatabase();
    }

    public void close() {
        if (db != null)
            db.close();
    }
}
