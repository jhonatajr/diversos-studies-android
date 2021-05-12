package com.example.mobile_schorgan.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class UsuarioDAO extends AbstractDAO {

    public UsuarioDAO(Context context) {
        super(context);
    }

   public boolean validaLogin(String email, String senha) {
       String sql = "SELECT * FROM usuarios where email = '" + email + "' and senha = '" + senha + "'";
       Cursor ponteiro = db.rawQuery(sql, null);

       return ponteiro.getCount() == 1 ? true : false;
   }

   public boolean validaCadastro(String email) {
       String sql = "SELECT * FROM usuarios where email = '" + email + "'";
       Cursor ponteiro = db.rawQuery(sql, null);

       return ponteiro.getCount() == 1 ? true : false;

   }

   public String devolveUsuario(String email) {
       String sql = "SELECT * FROM usuarios where email = '" + email + "'";
       Cursor ponteiro = db.rawQuery(sql, null);
       ponteiro.moveToFirst();
       return ponteiro.getString(ponteiro.getColumnIndex("nome"));
   }

   public void cadastraUsuario(String nome, String email, String senha) {
       ContentValues valores = new ContentValues();
       valores.put("nome", nome);
       valores.put("email", email);
       valores.put("senha", senha);
       db.insert("usuarios", null, valores);
   }

}
