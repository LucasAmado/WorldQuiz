package com.gonzaloandcompany.woldquiz.ui.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gonzaloandcompany.woldquiz.R;

import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class FilterAdapter extends ArrayAdapter<String> {

    Context ctx;
    int layoutPlantilla;
    List<String> listaDatos;

    public FilterAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        this.ctx = context;
        this.layoutPlantilla = resource;
        this.listaDatos = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = LayoutInflater.from(ctx).inflate(layoutPlantilla, parent, false);

        // Rescatar una referencia de cada elemento visual de la plantilla (template)
        TextView tvNombre = v.findViewById(android.R.id.text1);

        // Obtener los datos del alumno actual que debo dibujar
        String opc = listaDatos.get(position);

        // Insertar en los componentes de la plantilla
        // los datos del alumno actual
        tvNombre.setText(opc);

        Log.e(TAG, "ENTRA: "+opc);

        return v;
    }
}
