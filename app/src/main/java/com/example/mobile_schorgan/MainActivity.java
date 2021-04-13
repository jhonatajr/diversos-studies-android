package com.example.mobile_schorgan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.example.mobile_schorgan.DAO.AlunoDAO;
import com.example.mobile_schorgan.models.Aluno;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listaminha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaminha = (ListView) findViewById(R.id.my_list);

        listaminha.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View TextView, int position, long id) {
                Aluno aluno = (Aluno) listaminha.getItemAtPosition(position);
                Toast.makeText(MainActivity.this, "Aluno " + aluno.getNome() + " clicado", Toast.LENGTH_SHORT).show();
                Intent gotoForm = new Intent(MainActivity.this,FormularioActivity.class);
                gotoForm.putExtra("aluno",aluno); // passa o objeto para a outra activity com uma identificação.
                startActivity(gotoForm); // starta a activity com a intetion
            }
        });

        listaminha.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> listaminha, View item, int position, long id) {
                Toast.makeText(MainActivity.this,"Clique longo", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        Button newItem = findViewById(R.id.main_salvar);
        newItem.setOnClickListener(new View.OnClickListener() {
            // abaixo segue uma referencia para ele navegar entre as telas
            @Override
            public void onClick(View v) {         // (form source, form to)
                Intent intentGoForm = new Intent(MainActivity.this, FormularioActivity.class); // referencia a classe, pq não pode referenciar a q está de fato VIEW V
                startActivity(intentGoForm);
            }
        });
        registerForContextMenu(listaminha);
    }

    private void carregaPessoas() {
        AlunoDAO dao = new AlunoDAO(this);
        List<Aluno> alunos = dao.buscaAlunos();
        dao.close();

        ArrayAdapter<Aluno> adapter = new ArrayAdapter<Aluno>(this, android.R.layout.simple_list_item_1, alunos);
        listaminha.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaPessoas();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

                Aluno aluno = (Aluno) listaminha.getItemAtPosition(info.position);
                AlunoDAO dao = new AlunoDAO(MainActivity.this);

                dao.deletar(aluno); // excluir aluno especifico
                dao.close();

                carregaPessoas();

                Toast.makeText(MainActivity.this, "Deletar o item: " + aluno.getNome(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
}
