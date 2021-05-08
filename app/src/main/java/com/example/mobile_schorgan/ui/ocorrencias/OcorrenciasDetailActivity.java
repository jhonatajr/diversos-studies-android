package com.example.mobile_schorgan.ui.ocorrencias;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.mobile_schorgan.DAO.ClienteDAO;
import com.example.mobile_schorgan.Mask;
import com.example.mobile_schorgan.R;
import com.example.mobile_schorgan.models.Cliente;
import com.example.mobile_schorgan.models.Ocorrencia;

public class OcorrenciasDetailActivity extends AppCompatActivity {

    TextView textDataOcorrencia;
    TextView textCliente;
    TextView textLancamento;
    TextView textId;
    TextView textGravidade;
    TextView textTitulo;
    TextView textDescricao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocorrencias_detail);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        textDataOcorrencia = findViewById(R.id.ocorrenciaDetail);
        textCliente = findViewById(R.id.clienteDetail);
        textLancamento = findViewById(R.id.lancDetail);
        textId = findViewById(R.id.ocorrenciaIdDetail);
        textDescricao = findViewById(R.id.descricaoDetail);
        textTitulo = findViewById(R.id.tituloDetail);
        textGravidade = findViewById(R.id.gravidadeDetail);

        Intent intent = getIntent();
        Ocorrencia ocorrencia = (Ocorrencia) intent.getSerializableExtra("ocorrencia");


        textId.setText(Integer.toString(ocorrencia.getId()));
        textTitulo.setText(ocorrencia.getTitulo());
        textDescricao.setText(ocorrencia.getDescricao());
        textGravidade.setText(ocorrencia.getGravidade());
        ClienteDAO dao = new ClienteDAO(this);
        Cliente cliente = dao.montaClienteId(ocorrencia.getClienteid());
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