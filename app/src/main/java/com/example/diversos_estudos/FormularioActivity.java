package com.example.diversos_estudos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.diversos_estudos.DAO.AlunoDAO;
import com.example.diversos_estudos.models.Aluno;

public class FormularioActivity extends AppCompatActivity {

    private FormularioHelper form;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        form = new FormularioHelper(this);

        /*        Button botaoSalvar = findViewById(R.id.formulario_salvar); // pega o botao de salvar e ouve ele
        botaoSalvar.setOnClickListener(new View.OnClickListener() { // listener é
            @Override
            public void onClick(View v) { // // navegando entre forms após ações
            }
        }); // ouvir um evento de click */

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater(); // instancia a inflação
        inflater.inflate(R.menu.form_menu, menu); // transforma o menu em view
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { // toda vez q eu clicar no menu, irá retornar esse metodo
        switch (item.getItemId()) {
            case R.id.menu_main_ok: // valida se é o botão ok, o metodo daqui vai pra qualquer item, tem q validar antes o item.
                Aluno aluno = form.pegaAluno(); // pega o aluno antes, para não precisar passar direto pro banco (podem haver tratativas nesse helper)
                AlunoDAO dao = new AlunoDAO(this);
                dao.insere(aluno);
                dao.close();
                Toast.makeText(FormularioActivity.this, aluno.getNome().toString()+" salvo!", Toast.LENGTH_LONG).show();
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
