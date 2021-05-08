package com.example.mobile_schorgan.models;

import java.io.Serializable;
import java.util.Date;

public class Ocorrencia implements Serializable {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setGravidade(String gravidade) {
        this.gravidade = gravidade;
    }

    public String getGravidade() {
        return gravidade;
    }

    public void setClienteid(int clienteid) {
        this.clienteid = clienteid;
    }

    public int getClienteid() {
        return clienteid;
    }

    public void setDataOcorrencia(String data) {
        this.dataOcorrencia = data;
    }

    public String getDataOcorrencia() {
        return dataOcorrencia;
    }

    public void setDataLancamento(String data) {
        this.dataLanc = data;
    }

    public String getDataLanc() {
        return dataLanc;
    }

    public Date getDataLancamento() {
        return dataLancamento;
    }

    private void setDataLancamento(Date dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    private int id;
    private String descricao;
    private String titulo;
    private String gravidade;
    private String dataOcorrencia;
    private int clienteid;
    private Date dataLancamento;
    private String dataLanc;

    @Override
    public String toString() {
        return getId()  + " - " + getTitulo();
    }
}
