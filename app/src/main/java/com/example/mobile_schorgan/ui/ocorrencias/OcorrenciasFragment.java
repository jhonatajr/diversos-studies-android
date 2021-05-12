package com.example.mobile_schorgan.ui.ocorrencias;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.example.mobile_schorgan.DAO.ClienteDAO;
import com.example.mobile_schorgan.DAO.OcorrenciaDAO;
import com.example.mobile_schorgan.Mask;
import com.example.mobile_schorgan.R;
import com.example.mobile_schorgan.models.Cliente;
import com.example.mobile_schorgan.models.Ocorrencia;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class OcorrenciasFragment extends Fragment {

    private ListView listOcorrencia;
    private DatePickerDialog datePickerDialog;
    private Calendar c;
    private TextView textInfo;
    private int positionOcorrencia;
    private SwitchCompat switchData;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_ocorrencias, container, false);
        listOcorrencia = (ListView) root.findViewById(R.id.list_lancamentos);
        listOcorrencia.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View TextView, int position, long id) {
                Ocorrencia ocorrencia = (Ocorrencia) listOcorrencia.getItemAtPosition(position);
                Intent gotoForm = new Intent(getActivity(), OcorrenciasDetailActivity.class);
                gotoForm.putExtra("ocorrencia", ocorrencia);
                startActivity(gotoForm);
            }
        });

        listOcorrencia.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> listaminha, View item, int position, long id) {
                positionOcorrencia = position;
                return false;
            }
        });

        textInfo = root.findViewById(R.id.dataInfo);
        carregaOcorrencias();
         switchData = root.findViewById(R.id.switchData);
        switchData.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    c = Calendar.getInstance();
                    int day = c.get(Calendar.DAY_OF_MONTH);
                    int month = c.get(Calendar.MONTH);
                    int year = c.get(Calendar.YEAR);

                    datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                            String day = String.format("%02d",mDay);
                            int monthParam = mMonth +1;
                            String month = String.format("%02d",monthParam);
                            String year = Integer.toString(mYear);
                            textInfo.setText(day+"/"+month+"/"+year);
                            c.set(mYear,mMonth,mDay);
                            carregaOcorrenciasDia(c.getTime());
                        }
                    }, day, month, year);
                    datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == DialogInterface.BUTTON_NEGATIVE) {
                                textInfo.setText("Todos");
                                switchData.setChecked(false);
                                carregaOcorrencias();
                            }
                        }
                    });
                    datePickerDialog.updateDate(year, month, day);
                    datePickerDialog.show();
                }
                else {
                    textInfo.setText("Todos");
                    switchData.setChecked(false);
                    carregaOcorrencias();
                }
            }
        });
        FloatingActionButton newItem = root.findViewById(R.id.salvar);
        newItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClienteDAO dao = new ClienteDAO(getContext());
                List<Cliente> clientes = dao.buscaClientes();
                if (clientes.size() < 1) {
                    String msg = "Primeiro cadastre um cliente!";
                    Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
                }
                else {
                    Intent intentGoForm = new Intent(getActivity(), OcorrenciaFormularioActivity.class);
                    startActivity(intentGoForm);

                }
            }
        });

        registerForContextMenu(listOcorrencia);

        return root;
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        menu.add("Alterar");
        menu.add("Deletar");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    private void carregaOcorrencias() {
        OcorrenciaDAO dao = new OcorrenciaDAO(getContext());
        List<Ocorrencia> ocorrenciaList = dao.consultaOcorrencias();
        montaAdapter(ocorrenciaList);
    }

    private void carregaOcorrenciasDia(Date date) {
        OcorrenciaDAO dao = new OcorrenciaDAO(getContext());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        List<Ocorrencia> ocorrenciaList = dao.consultaOcorrenciasPorData(dateFormat.format(date));
        montaAdapter(ocorrenciaList);
    }

    private void montaAdapter(List<Ocorrencia> ocorrencias) {
        OcorrenciasAdapter adapterOcorrencias = new OcorrenciasAdapter(getContext(), ocorrencias);
        listOcorrencia.setAdapter(adapterOcorrencias);

    }

    @Override
    public void onResume() {
        carregaOcorrencias();
        switchData.setChecked(false);
        textInfo.setText("Todos");
        super.onResume();
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getTitle() == "Alterar") {
            Ocorrencia ocorrencia = (Ocorrencia) listOcorrencia.getItemAtPosition(positionOcorrencia);
            Intent gotoForm = new Intent(getActivity(), OcorrenciaFormularioActivity.class);
            gotoForm.putExtra("ocorrencia", ocorrencia);
            startActivity(gotoForm);
            return super.onContextItemSelected(item);

        }
        else if (item.getTitle() == "Deletar") {
            AlertDialog.Builder b = new AlertDialog.Builder(getContext(),android.R.style.ThemeOverlay_Material_Dialog_Alert);
            b.setTitle("Deletar");
            b.setMessage("Deseja deletar mesmo a ocorrencia?");
            b.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Ocorrencia ocorrencia = (Ocorrencia) listOcorrencia.getItemAtPosition(positionOcorrencia);
                    OcorrenciaDAO dao = new OcorrenciaDAO(getContext());
                    dao.deletar(ocorrencia);
                    String msg = "Ocorrencia deletada com sucesso";
                    Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
                    if (textInfo.getText().toString() != "Todos") {
                        switchData.setChecked(false);
                    }
                    carregaOcorrencias();
                }
            });
            b.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int p) {
                    dialogInterface.dismiss();
                }
            });
            AlertDialog alert = b.create();
            Mask.configuraAlert(alert);
        }
        if (textInfo.getText().toString() != "Todos") {
            switchData.setChecked(false);
        }
        carregaOcorrencias();
        return super.onContextItemSelected(item);
    }
}