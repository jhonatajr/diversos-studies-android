package com.example.mobile_schorgan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobile_schorgan.DAO.ClienteDAO;
import com.example.mobile_schorgan.DAO.OcorrenciaDAO;
import com.example.mobile_schorgan.models.Cliente;
import com.example.mobile_schorgan.models.Ocorrencia;

import java.util.Calendar;
import java.util.List;

public class OcorrenciaFormularioActivity extends AppCompatActivity  {

    private String gravidade;
    private Object cliente;
    private OcorrenciaHelper form;
    private EditText edtTitulo;
    private EditText edtData;

    Calendar c;
    DatePickerDialog datePickerDialog;

    public OcorrenciaFormularioActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lancamento_ocorrencia);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarInfo);
        TextView textView = (TextView)toolbar.findViewById(R.id.proxOcorrencia);
        String toolbarOcorrencia = "Ocorrencia nº";

        Spinner spinnerCli = (Spinner) findViewById(R.id.spinnercliente);
        Spinner spinnerGrav = (Spinner) findViewById(R.id.spinnerGravidade);

         edtTitulo = (EditText) findViewById(R.id.formulario_titulo);
         edtData = (EditText)  findViewById(R.id.formulario_dataocorrencia);

        ClienteDAO dao = new ClienteDAO(this);
        List<Cliente> clientes = dao.buscaClientes();

        ImageButton datePickerButton = findViewById(R.id.datePickerButton);
        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                int day = c.get(Calendar.DAY_OF_MONTH);
                int month = c.get(Calendar.MONTH);
                int year = c.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(OcorrenciaFormularioActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                        String day = String.format("%02d",mDay);
                        int monthParam = mMonth +1;
                        String month = String.format("%02d",monthParam);
                        String year = Integer.toString(mYear);
                        edtData.setText(day+"/"+month+"/"+year);
                    }
                }, day, month, year);
                datePickerDialog.updateDate(year, month, day);
                datePickerDialog.show();
            }
        });

        String[] gravidades = {"Alta","Média","Baixa"};

        ArrayAdapter<Cliente> cliAdapter = new ArrayAdapter<Cliente>(this, android.R.layout.simple_spinner_item, clientes);
        ArrayAdapter<String> gravAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, gravidades);

        cliAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gravAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerGrav.setAdapter(gravAdapter);
        spinnerCli.setAdapter(cliAdapter);

        form = new OcorrenciaHelper(this);
        Intent intent = getIntent();
        Ocorrencia ocorrencia = (Ocorrencia) intent.getSerializableExtra("ocorrencia");
        if (ocorrencia != null) {
            form.setOcorrencia(ocorrencia);
            Cliente cliIndex = dao.montaClienteId(ocorrencia.getClienteid());
            int spinnerPosition = Helper.getIndexOf(clientes, cliIndex.getId());
            spinnerCli.setSelection(spinnerPosition);
            spinnerPosition = gravAdapter.getPosition(ocorrencia.getGravidade());
            spinnerGrav.setSelection(spinnerPosition);
            form.ocorrencia.setGravidade(ocorrencia.getGravidade());
            form.ocorrencia.setClienteid(cliIndex.getId());
            toolbarOcorrencia = toolbarOcorrencia + " " + ocorrencia.getId();
        }
        else {
            OcorrenciaDAO ocorrenciaDAO = new OcorrenciaDAO(this);
            int proxOcorrencia = ocorrenciaDAO.getLastId();
            toolbarOcorrencia = toolbarOcorrencia + " " + proxOcorrencia;
            ocorrenciaDAO.close();
         }
        textView.setText(toolbarOcorrencia);
        dao.close();


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
                Ocorrencia ocorrencia = form.getOcorrencia();
                OcorrenciaDAO dao = new OcorrenciaDAO(this);
                if (ocorrencia.getTitulo().isEmpty()) {
                    String msg = "Favor insira um titulo";
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                    edtTitulo.requestFocus();
                    break;
                }
                if (ocorrencia.getDataOcorrencia().isEmpty()) {
                    String msg = "Favor insira a data da ocorrência";
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                    edtData.requestFocus();
                    break;
                }
                if (Integer.toString(ocorrencia.getClienteid()).isEmpty()) {
                    String msg = "Escolha algum cliente para lançar a ocorrência";
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                    break;
                }
                if (dao.getById(ocorrencia.getId())) {
                    dao.altera(ocorrencia);
                }
                else {
                    dao.insere(ocorrencia);
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


