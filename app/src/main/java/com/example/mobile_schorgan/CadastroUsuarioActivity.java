package com.example.mobile_schorgan;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile_schorgan.DAO.UsuarioDAO;

public class CadastroUsuarioActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSalvar;
    EditText txtNomeCad, txtEmailCad, txtSenhaCad, txtConfSenhaCad;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastrese);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        txtNomeCad = (EditText) findViewById(R.id.txtNomeCad);
        txtEmailCad = (EditText) findViewById(R.id.txtEmailCad);
        txtSenhaCad = (EditText) findViewById(R.id.txtSenhaCad);
        txtConfSenhaCad = (EditText) findViewById(R.id.txtConfSenhaCad);

        btnSalvar.setOnClickListener(this);
    }

    public void onClick(View v) {
        String NomeCad = txtNomeCad.getText().toString();
        String EmailCad = txtEmailCad.getText().toString();
        String SenhaCad = txtSenhaCad.getText().toString();
        String ConfSenhaCad = txtConfSenhaCad.getText().toString();
        UsuarioDAO dao = new UsuarioDAO(this);
        if (!validateEmailFormat(EmailCad)) {
            String msg = "Favor digite um e-mail válido";
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        } else if (SenhaCad.length() <= 3) {
            String msg = "A senha deve ter mais de 3 caracteres";
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        } else if (!SenhaCad.equals(ConfSenhaCad)) {
            String msg = "As senhas digitadas não são iguais, digite novamente!";
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        } else if (dao.validaCadastro(EmailCad)) {
            String msg = "Email já existente, favor insira outro!!";
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        } else {
            dao.cadastraUsuario(NomeCad, EmailCad, SenhaCad);
            String msg = "Usuario cadastrado, favor insira sua senha e e-mail para logar!";
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            limpar();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void limpar() {
        txtNomeCad.setText("");
        txtEmailCad.setText("");
        txtSenhaCad.setText("");
        txtConfSenhaCad.setText("");
        txtNomeCad.requestFocus();
    }

    private boolean validateEmailFormat(final String email) {
        if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
