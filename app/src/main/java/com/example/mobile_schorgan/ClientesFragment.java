package com.example.mobile_schorgan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mobile_schorgan.DAO.AlunoDAO;
import com.example.mobile_schorgan.models.Aluno;

import java.util.List;

public class ClientesFragment extends Fragment   {

    private ListView listaCli;
    private Context mContext;

    public ClientesFragment(Context context) {
        this.mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_clientes, container, false);
        listaCli = view.findViewById(R.id.list_clientes);
        carregaClientes();
        return view;
    }

    private void carregaClientes() {
        AlunoDAO dao = new AlunoDAO(mContext);
        List<Aluno> alunos = dao.buscaAlunos();
        dao.close();

        ArrayAdapter<Aluno> adapter = new ArrayAdapter<Aluno>(mContext, android.R.layout.simple_list_item_1, alunos);
        listaCli.setAdapter(adapter);
    }

}