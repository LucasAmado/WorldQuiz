package com.gonzaloandcompany.woldquiz.ui.home;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.fragment.app.DialogFragment;

import com.gonzaloandcompany.woldquiz.R;
import com.gonzaloandcompany.woldquiz.models.Currency;
import com.gonzaloandcompany.woldquiz.models.Pais;
import com.gonzaloandcompany.woldquiz.service.PaisService;
import com.gonzaloandcompany.woldquiz.service.ServiceGeneratorPais;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Response;

public class FilterPaisFragment extends DialogFragment {

    View v;
    ListView lvFiltro;
    PaisService paisService;
    List<Pais> listaPaises = new ArrayList<>();
    List<Currency>listaMonedas = new ArrayList<>();
    HashMap<String, Currency> currencyMap = new HashMap<>();
    List<String> listaMostrar = new ArrayList<>();
    ImageView money, language;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Filtro de países");

        // Hacer que el diálogo sólo se pueda cerrar desde el botón
        // Cancelar o Guardar
        builder.setCancelable(true);

        // Cargar el layout del formulario
        v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_filtro_pais, null);
        builder.setView(v);

        lvFiltro = v.findViewById(R.id.lvFiltroPaises);
        /*money = v.findViewById(R.id.imageViewMoney);
        language = v.findViewById(R.id.imageViewLanguage);*/

        paisService = ServiceGeneratorPais.createService(PaisService.class);

        new LoadPaises().execute();


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

    private class LoadPaises extends AsyncTask<Void, Void ,List<Pais>> {

        @Override
        protected List<Pais> doInBackground(Void... voids) {
            Call<List<Pais>> getPaises = paisService.listPaises();
            Response<List<Pais>> responseGetAllPaises = null;
            try {
                responseGetAllPaises = getPaises.execute();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            if (responseGetAllPaises.isSuccessful()) {
                listaPaises.addAll(responseGetAllPaises.body());

                for (int i=0; i<listaPaises.size();i++){
                    for(int j=0;j<listaPaises.get(i).getCurrencies().size();j++) {
                        listaMonedas.add(listaPaises.get(i).getCurrencies().get(j)) ;
                    }
                }

                for(Currency c: listaMonedas){
                    currencyMap.put(c.getName(),c);
                }

                Set<Map.Entry<String,Currency>> set = currencyMap.entrySet();
                listaMonedas.clear();
                for (Map.Entry<String,Currency> entry:set){
                    listaMonedas.add(entry.getValue());
                }
            }
            return listaPaises;
        }

        @Override
        protected void onPostExecute(List<Pais> pais) {

            for(Currency c: listaMonedas){
                listaMostrar.add(c.getName());
            }

       /*for(Pais p: paises){
        for(int i=0; i<=listaPaises.size();i++){
            List<String> listaSinDuplicados = listaConDuplicados.stream()
                    .map(item->item.getNegociador())
                    .distinct()
                    .collect(Collectors.toList());
        }
       }*/


            FilterAdapter adapter = new FilterAdapter(
                    getContext(),
                    android.R.layout.simple_list_item_1,
                    listaMostrar
            );

            // Conectar el adapter a la lista
            lvFiltro.setAdapter(adapter);
        }
    }

}
