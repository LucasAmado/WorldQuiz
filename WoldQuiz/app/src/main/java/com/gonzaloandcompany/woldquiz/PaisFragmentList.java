package com.gonzaloandcompany.woldquiz;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
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

import com.gonzaloandcompany.woldquiz.ui.home.PaisFilterDialogFragment;
import com.gonzaloandcompany.woldquiz.models.Pais;
import com.gonzaloandcompany.woldquiz.service.PaisService;
import com.gonzaloandcompany.woldquiz.service.ServiceGeneratorPais;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class PaisFragmentList extends Fragment {

    private List<Pais> listaPaises = new ArrayList<>();
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private IPaisesListener paisesListener;
    private PaisService paisService;
    private MyPaisRecyclerViewAdapter myPaisRecyclerViewAdapter;

    RecyclerView recyclerView;

    public PaisFragmentList() {
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
                DialogFragment dialog = new PaisFilterDialogFragment();
                dialog.show(getFragmentManager(), "FilterPaisFragment");
                break;
            case R.id.action_idioma:
                DialogFragment dialogo = new PaisFilterDialogFragment();
                dialogo.show(getFragmentManager(), "FilterPaisFragment");
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
            Context context = view.getContext();
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

            }
            return listaPaises;
        }
    }

}
