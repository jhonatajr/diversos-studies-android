package com.example.mobile_schorgan.ui.clientes;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mobile_schorgan.DAO.ClienteDAO;
import com.example.mobile_schorgan.ClienteFormularioActivity;
import com.example.mobile_schorgan.OcorrenciaFormularioActivity;
import com.example.mobile_schorgan.R;
import com.example.mobile_schorgan.models.Cliente;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ClientesFragment extends Fragment implements TextWatcher {

    private ListView listaminha;
    private int positionCliente;
    private EditText edtBusca;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_clientes, container, false);
        listaminha = (ListView) root.findViewById(R.id.my_list);

        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        listaminha.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View TextView, int position, long id) {
                Cliente cliente = (Cliente) listaminha.getItemAtPosition(position);
                ClienteDialog clienteDialog = new ClienteDialog(getContext(), R.style.Theme_AppCompat_Light_Dialog_Alert,cliente);
                clienteDialog.show();
            }
        });

        edtBusca = root.findViewById(R.id.edtBusca);

        edtBusca.addTextChangedListener(this);

        listaminha.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> listaminha, View item, int position, long id) {
                 positionCliente = position;
                return false;
            }
        });

        carregaPessoas();
        FloatingActionButton newItem = root.findViewById(R.id.main_salvar);
        newItem.setOnClickListener(new View.OnClickListener() {
            // abaixo segue uma referencia para ele navegar entre as telas
            @Override
            public void onClick(View v) {
                Intent intentGoForm = new Intent(getActivity(), ClienteFormularioActivity.class);
                startActivity(intentGoForm);
            }
        });
        registerForContextMenu(listaminha);
        return root;
    }

    private void carregaPessoas() {
        ClienteDAO dao = new ClienteDAO(getContext());
        List<Cliente> clientes = dao.buscaClientes();
        dao.close();

        ClienteAdapter clienteAdapter = new ClienteAdapter(getContext(), clientes);
        //ArrayAdapter<Cliente> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, clientes);
        listaminha.setAdapter(clienteAdapter);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        menu.add("Alterar");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public void onResume() {
        edtBusca.setText("");
        carregaPessoas();
        super.onResume();
    }




    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getTitle() == "Alterar") {
            Cliente cliente = (Cliente) listaminha.getItemAtPosition(positionCliente);
            Intent gotoForm = new Intent(getActivity(), ClienteFormularioActivity.class);
            gotoForm.putExtra("cliente", cliente);
            startActivity(gotoForm);
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 2) {
            efetuarPesquisa(s);
        }
        if (count == 0) {
            carregaPessoas();
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    private void efetuarPesquisa(CharSequence s) {
        ClienteDAO dao = new ClienteDAO(getContext());
        List<Cliente> clientes = dao.montaClientePorNome(s);
        dao.close();
        ClienteAdapter clienteAdapter = new ClienteAdapter(getContext(), clientes);
        //ArrayAdapter<Cliente> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, clientes);
        listaminha.setAdapter(clienteAdapter);

    }
}