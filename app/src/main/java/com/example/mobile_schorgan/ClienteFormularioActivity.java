package com.example.mobile_schorgan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mobile_schorgan.DAO.ClienteDAO;
import com.example.mobile_schorgan.models.Cliente;

public class ClienteFormularioActivity extends AppCompatActivity {

    private ClienteHelper form;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        Cliente cliente = (Cliente) intent.getSerializableExtra("cliente");
        form = new ClienteHelper(this);
        if (cliente != null) {
            form.prencheformulario(cliente);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.form_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_main_ok:
                Cliente cliente = form.pegaCliente();
                ClienteDAO dao = new ClienteDAO(this);
                if (cliente.getNome().isEmpty()) {
                    String msg = "Favor insira um nome para o cliente!";
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                    break;
                }
                if (dao.buscaClientePorId(cliente.getId())) {
                    dao.altera(cliente);
                }
                else {
                    dao.insere(cliente);
                }

                dao.close();
                finish();
                break;
            case android.R.id.home:
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
