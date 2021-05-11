package com.example.mobile_schorgan.ui.ocorrencias;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mobile_schorgan.DAO.ClienteDAO;
import com.example.mobile_schorgan.Mask;
import com.example.mobile_schorgan.OcorrenciaFormularioActivity;
import com.example.mobile_schorgan.R;
import com.example.mobile_schorgan.models.Cliente;
import com.example.mobile_schorgan.models.Ocorrencia;
import com.example.mobile_schorgan.ui.clientes.ClienteDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class OcorrenciasDetailActivity extends AppCompatActivity {

    TextView textDataOcorrencia;
    TextView textCliente;
    TextView textLancamento;
    TextView textId;
    TextView textGravidade;
    TextView textTitulo;
    TextView textDescricao;
    Ocorrencia ocorrencia;
    Cliente cliente;
    Context mContext;
    public static Activity self_intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocorrencias_detail);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        mContext = this;

        textDataOcorrencia = findViewById(R.id.ocorrenciaDetail);
        textCliente = findViewById(R.id.clienteDetail);
        textLancamento = findViewById(R.id.lancDetail);
        textId = findViewById(R.id.ocorrenciaIdDetail);
        textDescricao = findViewById(R.id.descricaoDetail);
        textTitulo = findViewById(R.id.tituloDetail);
        textGravidade = findViewById(R.id.gravidadeDetail);

        Intent intent = getIntent();
         ocorrencia = (Ocorrencia) intent.getSerializableExtra("ocorrencia");


        ImageButton infoCliente = findViewById(R.id.infoCliente);
        infoCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClienteDialog dialog = new ClienteDialog(mContext, R.style.Theme_AppCompat_Light_Dialog_Alert, cliente);
                dialog.show();
            }
        });
        textId.setText(Integer.toString(ocorrencia.getId()));
        textTitulo.setText(ocorrencia.getTitulo());
        textDescricao.setText(ocorrencia.getDescricao());
        textGravidade.setText(ocorrencia.getGravidade());
        ClienteDAO dao = new ClienteDAO(this);
         cliente = dao.montaClienteId(ocorrencia.getClienteid());
        if (cliente != null) {
            textCliente.setText(cliente.getNome());
        }
        textLancamento.setText(Mask.parseDateGet(ocorrencia.getDataLanc()));
        textDataOcorrencia.setText(Mask.parseDateGet(ocorrencia.getDataOcorrencia()));
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