package com.example.mobile_schorgan.ui.ocorrencias;

import android.app.Activity;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.mobile_schorgan.Mask;
import com.example.mobile_schorgan.R;
import com.example.mobile_schorgan.models.Cliente;
import com.example.mobile_schorgan.models.Ocorrencia;
import com.example.mobile_schorgan.ui.ocorrencias.OcorrenciaFormularioActivity;

public class OcorrenciaHelper {

    private final EditText campoTitulo;
    private final EditText campoData;
    private final EditText campoDescricao;
    private final Spinner spinnerCliente;
    private final Spinner spinnerGravidade;
    public Ocorrencia ocorrencia;
    private Activity activity;


    public OcorrenciaHelper(OcorrenciaFormularioActivity activity) {
        this.activity = activity;
        campoTitulo = (EditText) activity.findViewById(R.id.formulario_titulo);
        campoData = (EditText) activity.findViewById(R.id.formulario_dataocorrencia);
        campoData.setInputType(InputType.TYPE_NULL);
        campoDescricao = (EditText) activity.findViewById(R.id.formulario_descricao);
        spinnerCliente = (Spinner) activity.findViewById(R.id.spinnercliente);
        spinnerGravidade = (Spinner) activity.findViewById(R.id.spinnerGravidade);
        ocorrencia = new Ocorrencia();
    }

    public Ocorrencia getOcorrencia() {
        ocorrencia.setDescricao(campoDescricao.getText().toString());
        ocorrencia.setTitulo(campoTitulo.getText().toString());
        ocorrencia.setDataOcorrencia(campoData.getText().toString());
        Cliente cliente = (Cliente) spinnerCliente.getSelectedItem();
        ocorrencia.setClienteid(cliente.getId());
        ocorrencia.setGravidade(spinnerGravidade.getSelectedItem().toString());
        return ocorrencia;
    }

    public void setOcorrencia(Ocorrencia ocorrencia) {
        campoTitulo.setText(ocorrencia.getTitulo());
        campoDescricao.setText(ocorrencia.getDescricao());
        campoData.setText(Mask.parseDateGet(ocorrencia.getDataOcorrencia()));
        this.ocorrencia = ocorrencia;
    }



}
