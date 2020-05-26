package com.example.diversos_estudos;

import androidx.annotation.NonNull;
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

import com.example.diversos_estudos.DAO.AlunoDAO;
import com.example.diversos_estudos.models.Aluno;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listaminha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaminha = (ListView) findViewById(R.id.my_list); //ac!ha a view pelo id, referencia o componente

        listaminha.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View TextView, int position, long id) {
                Aluno aluno = (Aluno) listaminha.getItemAtPosition(position);
                Toast.makeText(MainActivity.this, "Aluno " + aluno.getNome() + "clicado", Toast.LENGTH_SHORT).show();
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

        ArrayAdapter<Aluno> adapter = new ArrayAdapter<Aluno>(this, android.R.layout.simple_list_item_1, alunos); // instancia um array adaptado para a view
        listaminha.setAdapter(adapter); // faz o vinculo entre o que está sendo passado array adapter e manda para a view, junção do texto q tá sendo montado com a view
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaPessoas();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) { // menu de contexto para a lista
        MenuItem deletar = menu.add("Deletar"); // adiciona um elemento na lista como menu
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // contextmenuinfo será de um adapter
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

                Aluno aluno = (Aluno) listaminha.getItemAtPosition(info.position); // devolve a posição que o usuario clicou para o objeto
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
