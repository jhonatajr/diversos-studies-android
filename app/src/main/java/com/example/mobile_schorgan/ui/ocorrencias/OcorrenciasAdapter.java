package com.example.mobile_schorgan.ui.ocorrencias;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mobile_schorgan.DAO.ClienteDAO;
import com.example.mobile_schorgan.DAO.OcorrenciaDAO;
import com.example.mobile_schorgan.Mask;
import com.example.mobile_schorgan.R;
import com.example.mobile_schorgan.models.Cliente;
import com.example.mobile_schorgan.models.Ocorrencia;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

public class  OcorrenciasAdapter extends BaseAdapter {

    private Context context;
    private List<Ocorrencia> ocorrencias;
    private LayoutInflater mInflater;

    public OcorrenciasAdapter(Context context, List<Ocorrencia> ocorrencias) {
        this.context = context;
        this.ocorrencias = ocorrencias;
        this.mInflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return ocorrencias.size();
    }

    @Override
    public Object getItem(int position) {
        
        return ocorrencias.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Ocorrencia ocorrencia = ocorrencias.get(position);
        if (convertView == null) {
            convertView =  mInflater.inflate(R.layout.adapter_ocorrencia, parent, false);
        }

        TextView textTitulo;
        TextView textData;
        TextView textCli;

        textTitulo = convertView.findViewById(android.R.id.title);
        textData = convertView.findViewById(android.R.id.text2);
        textCli = convertView.findViewById(R.id.cliente);

        ClienteDAO dao = new ClienteDAO(context);
        Cliente cliente = dao.montaClienteId(ocorrencia.getClienteid());
        if (cliente == null) {
            textCli.setText("Não encontrado");
        }
        else {
            textCli.setText(cliente.getNome());
        }
        textTitulo.setText(ocorrencia.getTitulo());
        textData.setText(Mask.parseDateGet(ocorrencia.getDataOcorrencia()));


        return convertView;
    }
}
