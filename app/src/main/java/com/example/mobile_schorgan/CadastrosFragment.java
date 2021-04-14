package com.example.mobile_schorgan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_schorgan.models.Aluno;

public class CadastrosFragment extends Fragment {

    private CardView cardTipo;
    private CardView cardCli;
    private Context mContext;

    @Override
    public void onAttach(Context context) {
        this.mContext = context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_cadastro, container, false);
        cardTipo = view.findViewById(R.id.card_tipo);
        cardCli = view.findViewById(R.id.card_cli);
        cardCli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClientesFragment clientesFragment = new ClientesFragment(mContext);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(((ViewGroup)getView().getParent()).getId(), clientesFragment, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }




}