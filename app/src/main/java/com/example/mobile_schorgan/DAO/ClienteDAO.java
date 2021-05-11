package com.example.mobile_schorgan.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;


import com.example.mobile_schorgan.models.Cliente;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ClienteDAO extends AbstractDAO {


    public ClienteDAO(Context context) {
        super(context);
    }


    public void insere(Cliente cliente) {
        ContentValues dados = pegaDados(cliente,false);
        db.insert("clientes", null, dados);
    }

    private ContentValues pegaDados(Cliente cliente, Boolean alteracao) {
        ContentValues dados = new ContentValues();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        dados.put("nome", cliente.getNome());
        dados.put("telefone", cliente.getTelefone());
        dados.put("cpf", cliente.getCpfCnpj());
        dados.put("email", cliente.getEmail());
        dados.put("uf"  , cliente.getUf());
        dados.put("cep", cliente.getCep());
        dados.put("numero", cliente.getNumero());
        dados.put("logradouro", cliente.getLogradouro());
        dados.put("complemento", cliente.getComplemento());
        dados.put("cidade", cliente.getCidade());
        dados.put("bairro", cliente.getBairro());
        if (!alteracao) {
            dados.put("datacadastro", dateFormat.format(date));
        }
        return dados;
    }


    public boolean buscaClientePorId(int id) {
        String sql = "SELECT * FROM clientes where clienteid = " +id;
        Cursor ponteiro = db.rawQuery(sql, null);
        return ponteiro.getCount() == 1 ? true : false;
    }

    public Cliente montaClienteId(int id) {
        String sql = "SELECT * FROM clientes where clienteid = " +id;
        Cursor ponteiro = db.rawQuery(sql, null);
        Cliente cliente = new Cliente();
        ponteiro.moveToFirst();
        cliente.setId(ponteiro.getInt(ponteiro.getColumnIndex("clienteid")));
        cliente.setNome(ponteiro.getString(ponteiro.getColumnIndex("nome")));
        cliente.setEmail(ponteiro.getString(ponteiro.getColumnIndex("email")));
        cliente.setTelefone(ponteiro.getString(ponteiro.getColumnIndex("telefone")));
        cliente.setBairro(ponteiro.getString(ponteiro.getColumnIndex("bairro")));
        cliente.setCidade(ponteiro.getString(ponteiro.getColumnIndex("cidade")));
        cliente.setLogradouro(ponteiro.getString(ponteiro.getColumnIndex("logradouro")));
        cliente.setNumero(ponteiro.getInt(ponteiro.getColumnIndex("numero")));
        cliente.setUf(ponteiro.getString(ponteiro.getColumnIndex("uf")));
        cliente.setCep(ponteiro.getString(ponteiro.getColumnIndex("cep")));
        cliente.setComplemento(ponteiro.getString(ponteiro.getColumnIndex("complemento")));
        ponteiro.close();
        return cliente;
    }

    public List<Cliente> montaClientePorNome(CharSequence s) {
        String sql = "SELECT * FROM clientes where nome LIKE '" + s + "%'";
        Cursor ponteiro = db.rawQuery(sql, null);
        List<Cliente> clientes = new ArrayList<Cliente>();
        while (ponteiro.moveToNext()) {
            Cliente cliente = new Cliente();
            cliente.setId(ponteiro.getInt(ponteiro.getColumnIndex("clienteid")));
            cliente.setNome(ponteiro.getString(ponteiro.getColumnIndex("nome")));
            cliente.setEmail(ponteiro.getString(ponteiro.getColumnIndex("email")));
            cliente.setTelefone(ponteiro.getString(ponteiro.getColumnIndex("telefone")));
            cliente.setBairro(ponteiro.getString(ponteiro.getColumnIndex("bairro")));
            cliente.setCidade(ponteiro.getString(ponteiro.getColumnIndex("cidade")));
            cliente.setLogradouro(ponteiro.getString(ponteiro.getColumnIndex("logradouro")));
            cliente.setNumero(ponteiro.getInt(ponteiro.getColumnIndex("numero")));
            cliente.setUf(ponteiro.getString(ponteiro.getColumnIndex("uf")));
            cliente.setCep(ponteiro.getString(ponteiro.getColumnIndex("cep")));
            cliente.setComplemento(ponteiro.getString(ponteiro.getColumnIndex("complemento")));
            clientes.add(cliente);
        }
        ponteiro.close();
        return clientes;
    }


    public List<Cliente> buscaClientes() {
        String sql = "SELECT * FROM clientes where cancelado is NULL;";
        Cursor ponteiro = db.rawQuery(sql, null);
        List<Cliente> clientes = new ArrayList<Cliente>();
        while (ponteiro.moveToNext()) {
            Cliente cliente = new Cliente();
            cliente.setId(ponteiro.getInt(ponteiro.getColumnIndex("clienteid")));
            cliente.setNome(ponteiro.getString(ponteiro.getColumnIndex("nome")));
            cliente.setEmail(ponteiro.getString(ponteiro.getColumnIndex("email")));
            cliente.setTelefone(ponteiro.getString(ponteiro.getColumnIndex("telefone")));
            cliente.setBairro(ponteiro.getString(ponteiro.getColumnIndex("bairro")));
            cliente.setCidade(ponteiro.getString(ponteiro.getColumnIndex("cidade")));
            cliente.setLogradouro(ponteiro.getString(ponteiro.getColumnIndex("logradouro")));
            cliente.setNumero(ponteiro.getInt(ponteiro.getColumnIndex("numero")));
            cliente.setUf(ponteiro.getString(ponteiro.getColumnIndex("uf")));
            cliente.setCep(ponteiro.getString(ponteiro.getColumnIndex("cep")));
            cliente.setComplemento(ponteiro.getString(ponteiro.getColumnIndex("complemento")));
            clientes.add(cliente);
        }
        ponteiro.close();
        return clientes;

    }

    public void deletar(Cliente cliente) {
        String[] params = {Integer.toString(cliente.getId())};
        ContentValues dados = pegaDados(cliente, false);
        dados.put("cancelado", "S");
        db.update("clientes", dados, "clienteid = ?", params);
    }

    public void altera(Cliente cliente) {
        String[] params = {Integer.toString(cliente.getId())};
        ContentValues dados = pegaDados(cliente,true);
        db.update("clientes", dados, "clienteid = ?", params);

    }
}