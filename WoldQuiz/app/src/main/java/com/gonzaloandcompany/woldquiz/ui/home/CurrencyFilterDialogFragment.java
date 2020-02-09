package com.gonzaloandcompany.woldquiz.ui.home;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.gonzaloandcompany.woldquiz.PaisFragmentList;
import com.gonzaloandcompany.woldquiz.R;
import com.gonzaloandcompany.woldquiz.models.Pais;
import com.gonzaloandcompany.woldquiz.service.PaisService;
import com.gonzaloandcompany.woldquiz.service.ServiceGeneratorPais;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class CurrencyFilterDialogFragment extends DialogFragment {

    View v;
    ListView lvFiltro;
    PaisService paisService;
    List<Pais> paises = new ArrayList<>();
    List<String> listaMostrar = new ArrayList<>();
    DialogPassData dialogPassData;

    public CurrencyFilterDialogFragment(DialogPassData dialogPassData) {
        this.dialogPassData = dialogPassData;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Configura el dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Configuración del diálogo

        builder.setTitle("Filtro de países");
        builder.setMessage("Selecciona una moneda");

        // Hacer que el diálogo sólo se pueda cerrar desde el botón
        // Cancelar o Guardar
        builder.setCancelable(true);

        // Cargar el layout del formulario
        v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_filtro_moneda, null);
        builder.setView(v);

        lvFiltro = v.findViewById(R.id.lvFiltro);

        paisService = ServiceGeneratorPais.createService(PaisService.class);
        new LoadMonedas().execute();

        builder.setPositiveButton(R.string.button_guardar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Guardar alumno

            }
        });

        builder.setNegativeButton(R.string.button_cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Cancelar diálogo
                dialog.dismiss();
            }
        });
         Dialog dialog = builder.create();
        lvFiltro.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = (String)lvFiltro.getItemAtPosition(position);
                Log.d("ITEM", item);
                dialogPassData = (DialogPassData)getTargetFragment();
                dialogPassData.filterByCoin(item);
                dialog.dismiss();
            }

        });

                // Create the AlertDialog object and return it

        return dialog;
    }


    private class LoadMonedas extends AsyncTask<Void, Void, List<String>> {

        @Override
        protected List<String> doInBackground(Void... voids) {

            Call<List<Pais>> getPaises = paisService.listPaises();
            Response<List<Pais>> responseGetAllPaises = null;
            try {
                responseGetAllPaises = getPaises.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (responseGetAllPaises.isSuccessful()) {
                paises.addAll(responseGetAllPaises.body());

                for (int i = 0; i < paises.size(); i++) {
                    for (int j = 0; j < paises.get(i).getCurrencies().size(); j++) {
                        String nombre = paises.get(i).getCurrencies().get(j).getName();
                        if (!listaMostrar.contains(nombre) && nombre != null) {
                            listaMostrar.add(nombre);
                        }
                    }
                }

            }
            return listaMostrar;
        }

        @Override
        protected void onPostExecute(List<String> list) {

            PaisFilterAdapter adapter = new PaisFilterAdapter(
                    getContext(),
                    android.R.layout.simple_list_item_1,
                    listaMostrar
            );


            lvFiltro.setAdapter(adapter);
        }
    }
}
