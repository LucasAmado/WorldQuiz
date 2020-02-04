package com.gonzaloandcompany.woldquiz;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gonzaloandcompany.woldquiz.dummy.DummyContent;
import com.gonzaloandcompany.woldquiz.dummy.DummyContent.DummyItem;
import com.gonzaloandcompany.woldquiz.models.Pais;

import java.util.ArrayList;
import java.util.List;

public class PaisFragmentList extends Fragment {

    private List<Pais> listaPaises = new ArrayList<>();
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private IPaisesListener paisesListener;
    private PaisService paisService;
    private MyPaisRecyclerViewAdapter myPaisRecyclerViewAdapter;
    private ServiceGeneratorPais serviceGeneratorPais;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PaisFragmentList() {
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
            serviceGeneratorPais = ServiceGeneratorPais.createService(ServiceGeneratorPais.class);

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

    private class llamadaAsincTask extends AsyncTask<Void, Void ,List<Pais>>{

        @Override
        protected List<Pais> doInBackground(Void... voids) {
            return null;
        }
    }
}
