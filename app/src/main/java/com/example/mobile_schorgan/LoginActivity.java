package com.example.mobile_schorgan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile_schorgan.DAO.UsuarioDAO;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnAcessar, btnCadastrese;
    EditText txtLogin, txtSenhaLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        btnAcessar = (Button) findViewById(R.id.btnAcessar);
        btnCadastrese = (Button) findViewById(R.id.btnCadastrar);
        txtLogin = (EditText) findViewById(R.id.txtLogin);
        txtSenhaLogin = (EditText) findViewById(R.id.txtSenhaLogin);

        btnAcessar.setOnClickListener(this);
        btnCadastrese.setOnClickListener(this);
    }

    public void onClick(View v)
    {
        if (v.getId() == R.id.btnCadastrar) {
            Intent telaCadastreSe = new Intent(this, CadastroUsuarioActivity.class);
            startActivity(telaCadastreSe);
            finish();
        }
        if (v.getId() == R.id.btnAcessar) {
            consultaUsuarioLogin();
        }
    }

    public void consultaUsuarioLogin()
    {
        String Login = txtLogin.getText().toString();
        String SenhaLogin = txtSenhaLogin.getText().toString();

        UsuarioDAO dao = new UsuarioDAO(this);
        boolean validaLogin = dao.validaLogin(Login, SenhaLogin);

        if(validaLogin){
            String usuario = dao.devolveUsuario(Login);
            SharedPreferences.Editor editor = getSharedPreferences("Login", MODE_PRIVATE).edit();
            editor.putString("login", usuario);
            editor.apply();
            Intent tela = new Intent(this, MenuNavActivity.class);
            startActivity(tela);
            finish();
        }else{
            String msg= "E-mail e senha não encontrados, digite um usuário válido";
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            limpar();
        }
    }
    public void limpar(){
        txtLogin.setText("");
        txtSenhaLogin.setText("");
        txtLogin.requestFocus();
    }

    @Override
    protected void onResume() {
        txtLogin.setText("");
        txtSenhaLogin.setText("");
        super.onResume();
    }
}
