package com.gonzaloandcompany.woldquiz.ui.home;

import android.os.AsyncTask;
import android.view.View;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.gonzaloandcompany.woldquiz.R;
import com.gonzaloandcompany.woldquiz.models.Pais;
import com.gonzaloandcompany.woldquiz.service.PaisService;
import com.gonzaloandcompany.woldquiz.service.ServiceGeneratorPais;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class PaisFilterDialogFragment extends DialogFragment {

    View v;
    List<Pais> paises = new ArrayList<>();
    List<String> listaMostrar = new ArrayList<>();
    PaisService paisService;
    ListView listView;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Configura el dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Configuración del diálogo

        builder.setTitle("Filtro de países");

        // Hacer que el diálogo sólo se pueda cerrar desde el botón
        // Cancelar o Guardar
        builder.setCancelable(true);

        // Cargar el layout del formulario
        v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_filtro_pais, null);
        builder.setView(v);

        listView = v.findViewById(R.id.listViewFilter);

        paisService = ServiceGeneratorPais.createService(PaisService.class);

        new LoadPaises().execute();


        builder.setPositiveButton(R.string.button_guardar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Guardar

            }
        });

        builder.setNegativeButton(R.string.button_cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Cancelar diálogo
                dialog.dismiss();
            }
        });

        // Create the AlertDialog object and return it

        return builder.create();
    }

    private class LoadPaises extends AsyncTask<Void, Void, List<Pais>> {

        @Override
        protected List<Pais> doInBackground(Void... voids) {
            Call<List<Pais>> getPaises = paisService.listPaises();
            Response<List<Pais>> responseGetAllPaises = null;
            try {
                responseGetAllPaises = getPaises.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (responseGetAllPaises.isSuccessful()) {
                paises.addAll(responseGetAllPaises.body());

            }
            return paises;
        }

        @Override
        protected void onPostExecute(List<Pais> pais) {

            listaMostrar.add("prueba");
            listaMostrar.add("prueba 2");
            listaMostrar.add("prueba 3");

            PaisFilterAdapter adapter = new PaisFilterAdapter(
                    getContext(),
                    android.R.layout.simple_list_item_1,
                    listaMostrar
            );

            // Conectar el adapter a la lista
            listView.setAdapter(adapter);
        }
    }

}
