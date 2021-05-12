package com.example.mobile_schorgan.ui.clientes;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.mobile_schorgan.R;
import com.example.mobile_schorgan.models.Cliente;

public class ClienteDialog extends Dialog implements View.OnClickListener {

    TextView textNome;
    TextView textEmail;
    TextView textTelefone;
    TextView textRua;
    TextView textBairro;
    TextView textCidade;
    TextView textCEP;

    public Cliente cliente;

    public ClienteDialog(@NonNull Context context, int themeResId, Cliente cliente) {
        super(context, themeResId);
        this.cliente = cliente;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_cliente);

        textNome = findViewById(R.id.nomeCliente);
        textEmail = findViewById(R.id.emailCliente);
        textTelefone = findViewById(R.id.telefoneCliente);
        textRua = findViewById(R.id.logradouroCliente);
        textCEP = findViewById(R.id.cepCliente);
        textBairro = findViewById(R.id.bairroCliente);
        textCidade = findViewById(R.id.cidadeCliente);


        ImageButton buttonCancel = findViewById(R.id.fechar);
        buttonCancel.setOnClickListener(this);
        populaCampos();

    }

    public void populaCampos() {
        textNome.setText(cliente.getNome());
        if (cliente.getEmail() != null)
         textEmail.setText(cliente.getEmail());

        if (cliente.getTelefone() != null )
            textTelefone.setText(cliente.getTelefone());

        if (cliente.getCep() != null)
            textCEP.setText(cliente.getCep());

        if (cliente.getBairro() != null)
            textBairro.setText(cliente.getBairro());


        String logradouro = cliente.getLogradouro();

        if (cliente.getNumero() != 0)
            logradouro += " - " + cliente.getNumero();


        if (logradouro != null)
            textRua.setText(logradouro);

       if (cliente.getCidade() != null)
            textCidade.setText(cliente.getCidade());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fechar:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
