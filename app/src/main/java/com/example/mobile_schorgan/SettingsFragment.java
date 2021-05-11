package com.example.mobile_schorgan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static android.content.Context.MODE_PRIVATE;


public class SettingsFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        ViewGroup exportaBase = root.findViewById(R.id.sair);
        exportaBase.setOnClickListener(this);

        return root;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sair:
                sair();
        }
    }

    public void sair() {
        AlertDialog.Builder b = new AlertDialog.Builder(getContext(),android.R.style.ThemeOverlay_Material_Dialog_Alert);
        b.setTitle("Sair");
        b.setMessage("Deseja sair do aplicativo?");
        b.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("Login", MODE_PRIVATE).edit();
                editor.putString("login", "");
                editor.apply();
                getActivity().onBackPressed();
            }
        });
        b.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int p) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alert = b.create();
        alert.show();
        Button nbutton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        nbutton.setBackgroundColor(Color.TRANSPARENT);
        nbutton.setTextColor(Color.BLACK);
        Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setBackgroundColor(Color.TRANSPARENT);
        pbutton.setTextColor(Color.BLACK);
    }

}