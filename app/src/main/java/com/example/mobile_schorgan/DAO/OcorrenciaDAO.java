package com.example.mobile_schorgan.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.mobile_schorgan.models.Cliente;
import com.example.mobile_schorgan.models.Ocorrencia;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OcorrenciaDAO extends AbstractDAO {

    public OcorrenciaDAO(Context context) {
        super(context);
    }

    private ContentValues pegaDados(Ocorrencia ocorrencia, Boolean alteracao) {
        ContentValues dados = new ContentValues();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        dados.put("descricao", ocorrencia.getDescricao());
        dados.put("titulo", ocorrencia.getTitulo());
        dados.put("gravidade", ocorrencia.getGravidade());
        dados.put("dataocorrencia", parseDateSave(ocorrencia.getDataOcorrencia()));
        dados.put("clienteid"  , ocorrencia.getClienteid());
        if (!alteracao) {
            dados.put("datalancamento", dateFormat.format(date));

        }
        return dados;
    }

    public String parseDateSave(String data) {
        String inputPattern = "dd/MM/yyyy";
        String outputPattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(data);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public void deletar(Ocorrencia ocorrencia) {
        String[] params = {Integer.toString(ocorrencia.getId())};
        ContentValues dados = pegaDados(ocorrencia, false);
        dados.put("cancelado", "S");
        db.update("ocorrencia", dados, "ocrrenciaid = ?", params);
    }



    public List<Ocorrencia> montaPonteiro(Cursor ponteiro) {
        List<Ocorrencia> ocorrencias = new ArrayList<Ocorrencia>();
        while (ponteiro.moveToNext()) {
            Ocorrencia ocorrencia = new Ocorrencia();
            ocorrencia.setId(ponteiro.getInt(ponteiro.getColumnIndex("ocrrenciaid")));
            ocorrencia.setTitulo(ponteiro.getString(ponteiro.getColumnIndex("titulo")));
            ocorrencia.setDescricao(ponteiro.getString(ponteiro.getColumnIndex("descricao")));
            ocorrencia.setDataOcorrencia(ponteiro.getString(ponteiro.getColumnIndex("dataocorrencia")));
            ocorrencia.setDataLancamento(ponteiro.getString(ponteiro.getColumnIndex("datalancamento")));
            ocorrencia.setGravidade(ponteiro.getString(ponteiro.getColumnIndex("gravidade")));
            ocorrencia.setClienteid(ponteiro.getInt(ponteiro.getColumnIndex("clienteid")));
            ocorrencias.add(ocorrencia);
        }
        ponteiro.close();
        return ocorrencias;
    }
    public List<Ocorrencia> consultaOcorrencias() {
        String sql = "SELECT * FROM ocorrencia where cancelado is null order by dataocorrencia ASC;";
        Cursor ponteiro = db.rawQuery(sql, null);
        return montaPonteiro(ponteiro);
    }

    public List<Ocorrencia> consultaOcorrenciasPorData(String date) {
        String sql = "SELECT * FROM ocorrencia where cancelado is null and dataocorrencia = '" +  date+" 00:00:00'";
        Cursor ponteiro = db.rawQuery(sql, null);
        return montaPonteiro(ponteiro);
    }

    public boolean getById(int id) {
        String sql = "SELECT * FROM ocorrencia where ocrrenciaid = " +id;
        Cursor ponteiro = db.rawQuery(sql, null);
        return ponteiro.getCount() == 1 ? true : false;
    }

    public int getLastId() {
        String sql = "SELECT * FROM ocorrencia ORDER BY ocrrenciaid DESC;";
        Cursor ponteiro = db.rawQuery(sql, null);
        if (ponteiro.getCount() >= 1) {
            ponteiro.moveToFirst();
            return ponteiro.getInt(ponteiro.getColumnIndex("ocrrenciaid"))+1;
        }
        return 1;
    }

    public List<Ocorrencia> getOcorrenciaCli (int clienteid) {
        String sql = "SELECT * FROM ocorrencia where clienteid = clienteid;";
        Cursor ponteiro = db.rawQuery(sql, null);
        return montaPonteiro(ponteiro);
    }

    public void altera(Ocorrencia ocorrencia) {
        String[] params = {Integer.toString(ocorrencia.getId())};
        ContentValues dados = pegaDados(ocorrencia,true);
        db.update("ocorrencia", dados, "ocrrenciaid = ?", params);
    }

    public void insere(Ocorrencia ocorrencia) {
        ContentValues dados = pegaDados(ocorrencia,false);
        db.insert("ocorrencia", null, dados);
    }



}
