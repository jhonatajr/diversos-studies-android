package com.example.mobile_schorgan.ui.clientes;

import android.widget.EditText;

import com.example.mobile_schorgan.Mask;
import com.example.mobile_schorgan.R;
import com.example.mobile_schorgan.models.Cliente;


public class ClienteHelper {
    private final EditText campoNome;
    private final EditText campoEmail;
    private final EditText campoTelefone;
    private final EditText campoLogradouro;
    private final EditText campoNumero;
    private final EditText campoBairro;
    private final EditText campoCidade;
    private final EditText campoUF;
    private final EditText campoComplemento;
    private final EditText campoCep;



    private Cliente cliente;

    public ClienteHelper(ClienteFormularioActivity activity) {

        campoNome = (EditText) activity.findViewById(R.id.formulario_nome);
        campoEmail = (EditText) activity.findViewById(R.id.formulario_email);
        campoTelefone = (EditText) activity.findViewById(R.id.formulario_tel);
        campoLogradouro = (EditText) activity.findViewById(R.id.formulario_logradouro);
        campoBairro = (EditText) activity.findViewById(R.id.formulario_bairro);
        campoComplemento = (EditText) activity.findViewById(R.id.formulario_complemento);
        campoNumero = (EditText) activity.findViewById(R.id.formulario_numero_endereco);
        campoCidade = (EditText) activity.findViewById(R.id.formulario_cidade);
        campoCep = (EditText) activity.findViewById(R.id.formulario_cep);
        campoUF = (EditText) activity.findViewById(R.id.formulario_uf);

        cliente = new Cliente();
        montaMascara();
    }

    public Cliente pegaCliente() {
        cliente.setNome(campoNome.getText().toString());
        cliente.setTelefone(campoTelefone.getText().toString());
        cliente.setEmail(campoEmail.getText().toString());
        cliente.setLogradouro(campoLogradouro.getText().toString());
        cliente.setComplemento(campoComplemento.getText().toString());
        cliente.setCep(campoCep.getText().toString());
        cliente.setBairro(campoBairro.getText().toString());
        if (!campoNumero.getText().toString().isEmpty()) {
            cliente.setNumero(Integer.parseInt(campoNumero.getText().toString()));
        }
        cliente.setCidade(campoCidade.getText().toString());
        cliente.setUf(campoUF.getText().toString());
        return cliente;
    }

    public void prencheformulario(Cliente cliente) {
        campoNome.setText(cliente.getNome());
        campoEmail.setText(cliente.getEmail());
        campoTelefone.setText(cliente.getTelefone());
        campoLogradouro.setText(cliente.getLogradouro());
        campoComplemento.setText(cliente.getComplemento());
        campoNumero.setText(Integer.toString(cliente.getNumero()));
        campoCep.setText(cliente.getCep());
        campoBairro.setText(cliente.getBairro());
        campoCidade.setText(cliente.getCidade());
        campoUF.setText(cliente.getUf());
        this.cliente = cliente;
    }

    private void montaMascara() {
        campoTelefone.addTextChangedListener(Mask.insert("(##)####-####", campoTelefone));
        campoCep.addTextChangedListener(Mask.insert("#####-###", campoCep));
    }

}
