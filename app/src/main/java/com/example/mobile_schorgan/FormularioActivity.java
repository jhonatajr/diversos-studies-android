package com.example.mobile_schorgan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mobile_schorgan.DAO.AlunoDAO;
import com.example.mobile_schorgan.models.Aluno;

public class FormularioActivity extends AppCompatActivity {

    private FormularioHelper form;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        Intent intent = getIntent();
        Aluno aluno = (Aluno) intent.getSerializableExtra("aluno");
        form = new FormularioHelper(this);
        if (aluno != null) {
            form.prencheformulario(aluno);
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
                Aluno aluno = form.pegaAluno();
                AlunoDAO dao = new AlunoDAO(this);
                if (aluno.getId() != null) {
                    dao.altera(aluno);
                }
                else {
                    dao.insere(aluno);
                }

                dao.close();
                Toast.makeText(FormularioActivity.this, aluno.getNome().toString() + " salvo!", Toast.LENGTH_LONG).show();
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
