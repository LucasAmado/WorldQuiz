package com.gonzaloandcompany.woldquiz;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gonzaloandcompany.woldquiz.models.Currency;
import com.gonzaloandcompany.woldquiz.models.Language;
import com.gonzaloandcompany.woldquiz.models.Pais;
import com.gonzaloandcompany.woldquiz.service.PaisService;
import com.gonzaloandcompany.woldquiz.service.ServiceGeneratorPais;
import com.gonzaloandcompany.woldquiz.ui.countries.DialogPassData;
import com.gonzaloandcompany.woldquiz.ui.countries.FilterDialogFragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class PaisFragmentList extends Fragment implements DialogPassData {

    private List<Pais> listaPaises = new ArrayList<>();
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private IPaisesListener paisesListener;
    private PaisService paisService;
    private MyPaisRecyclerViewAdapter myPaisRecyclerViewAdapter;
    private DialogPassData dialogPassData;
    private List<Pais> byCoin;
    private List<Pais> byLang;
    private List<String> monedas;
    private List<String> idiomas;
    static String TYPE_COIN = "COIN";
    static String TYPE_LANG = "LANG";
    Context context;
    RecyclerView recyclerView;


    public PaisFragmentList() {

    }

    @Override
    public void filterByCoin(String coinName) {
        byCoin = new ArrayList<>();
        Log.d("COINNAME", coinName);
        Log.d("PAISES LISTA ", listaPaises.toString());
        for (Pais p : listaPaises) {
            if (p.getCurrencies() != null) {
                Currency c = p.getCurrencies().get(0);
                if (c != null) {
                    if (c.getName().equals(coinName)) {
                        byCoin.add(p);
                    }
                }
            }
        }

        myPaisRecyclerViewAdapter = new MyPaisRecyclerViewAdapter(context, byCoin, paisesListener);
        recyclerView.setAdapter(myPaisRecyclerViewAdapter);
    }

    @Override
    public void filterByLang(String language) {
        byLang = new ArrayList<>();

        for (Pais p : listaPaises) {
            if (p.getLanguages() != null) {
                Language l = p.getLanguages().get(0);
                if (l.getName() != null) {
                    if (l.getName().equals(language)) {
                        byLang.add(p);
                    }
                }
            }
        }

        myPaisRecyclerViewAdapter = new MyPaisRecyclerViewAdapter(context, byLang, paisesListener);
        recyclerView.setAdapter(myPaisRecyclerViewAdapter);
    }

    //Crear un menú en el fragment
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    //Seleccionar el menú y añadir las opciones
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_pais_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    //Tratamiento del menú al seleccionar un icono
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_coin:
                DialogFragment dialog = new FilterDialogFragment(dialogPassData, TYPE_COIN, monedas);
                dialog.setTargetFragment(this, 0);
                dialog.show(getFragmentManager(), "MonedasFilterDialogFragment");

                break;
            case R.id.action_idioma:
                DialogFragment dialogo = new FilterDialogFragment(dialogPassData, TYPE_LANG, idiomas);
                dialogo.setTargetFragment(this, 0);
                dialogo.show(getFragmentManager(), "LanguageFilterDialogFragment");
                break;

            case R.id.refresh_icon:
                myPaisRecyclerViewAdapter = new MyPaisRecyclerViewAdapter(context, listaPaises, paisesListener);
                recyclerView.setAdapter(myPaisRecyclerViewAdapter);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pais_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            listaPaises = new ArrayList<>();
            myPaisRecyclerViewAdapter = new MyPaisRecyclerViewAdapter(context, listaPaises, paisesListener);
            recyclerView.setAdapter(myPaisRecyclerViewAdapter);
            paisService = ServiceGeneratorPais.createService(PaisService.class);
            new LlamadaAsincTask().execute();

        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IPaisesListener) {
            paisesListener = (IPaisesListener) context;

        } else {
            throw new RuntimeException(context.toString()
                    + " must implement IPaisesListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        paisesListener = null;
        dialogPassData = null;
    }

    private class LlamadaAsincTask extends AsyncTask<Void, Void, List<Pais>> {

        @Override
        protected void onPostExecute(List<Pais> pais) {

            myPaisRecyclerViewAdapter.notifyDataSetChanged();
        }

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
                listaPaises.addAll(responseGetAllPaises.body());

                monedas = new ArrayList<>();
                idiomas = new ArrayList<>();

                for (int i = 0; i < listaPaises.size(); i++) {
                    for (int j = 0; j < listaPaises.get(i).getCurrencies().size(); j++) {
                        String nombre = listaPaises.get(i).getCurrencies().get(j).getName();
                        if (!monedas.contains(nombre) && nombre != null) {
                            monedas.add(nombre);
                        }
                    }
                }

                for (int i = 0; i < listaPaises.size(); i++) {
                    for (int j = 0; j < listaPaises.get(i).getLanguages().size(); j++) {
                        String nombre = listaPaises.get(i).getLanguages().get(j).getName();
                        if (!idiomas.contains(nombre) && nombre != null) {
                            idiomas.add(nombre);
                        }
                    }
                }

            }
            return listaPaises;
        }
    }

}
