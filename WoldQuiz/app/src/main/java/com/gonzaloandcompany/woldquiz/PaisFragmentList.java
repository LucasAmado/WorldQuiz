package com.gonzaloandcompany.woldquiz;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.gonzaloandcompany.woldquiz.dummy.DummyContent;
import com.gonzaloandcompany.woldquiz.dummy.DummyContent.DummyItem;
import com.gonzaloandcompany.woldquiz.models.Pais;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class PaisFragmentList extends Fragment {


    private static final String ARG_COLUMN_COUNT = "column-count";

    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private List<String> listaMonedas = new ArrayList<>();
    //TODO borrar
    private PaisService service;
    private List<Pais> listaPaises = new ArrayList<>();
    RecyclerView recyclerView;
    MyPaisRecyclerViewAdapter adapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
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
            case R.id.filter_icon:
                Intent i = new Intent(getActivity(), FilterPaisActivity.class);
                i.putStringArrayListExtra("monedas", (ArrayList<String>) listaMonedas);
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static PaisFragmentList newInstance(int columnCount) {
        PaisFragmentList fragment = new PaisFragmentList();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
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
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyPaisRecyclerViewAdapter(DummyContent.ITEMS, mListener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }

    private class LoadPaisesByFiltro extends AsyncTask<String, Void, List<Pais>> {

        @Override
        protected List<Pais> doInBackground(String... strings) {

            Call<List<Pais>> callPaises = null;

            for (String moneda : listaMonedas) {
                if (listaMonedas.contains(moneda)) {
                    callPaises = service.listPaisesByMoneda(strings[0]);
                }
            }

            Response<List<Pais>> responsePaises = null;

            try {
                responsePaises = callPaises.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (responsePaises.isSuccessful()) {
                listaPaises = responsePaises.body();
            }
            return listaPaises;
        }

        @Override
        protected void onPostExecute(List<Pais> paises) {
            if (paises != null) {
                //adapter = new MyPaisRecyclerViewAdapter();
                recyclerView.setAdapter(adapter);
            }
        }
    }
}
