package com.example.mobile_schorgan.ui.clientes;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mobile_schorgan.R;
import com.example.mobile_schorgan.models.Cliente;

import java.util.List;

public class ClienteAdapter extends BaseAdapter {

    private Context mContext;
    private List<Cliente> listaCli;
    private LayoutInflater mInflater;

   public ClienteAdapter(Context context, List<Cliente> clientes) {
       this.listaCli = clientes;
       this.mContext = context;
       this.mInflater = LayoutInflater.from(mContext);
   }

    @Override
    public int getCount() {
        return listaCli.size();
    }

    @Override
    public Object getItem(int position) {
        return listaCli.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        Cliente cliente = listaCli.get(position);
        if (view == null) {
            view =  mInflater.inflate(R.layout.list_clientes, null);
        }

        TextView textViewNome =  view.findViewById(android.R.id.title);
        textViewNome.setText(cliente.getNome());

        return view;
    }
}
