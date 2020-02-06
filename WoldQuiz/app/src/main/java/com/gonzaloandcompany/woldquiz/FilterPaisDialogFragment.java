package com.gonzaloandcompany.woldquiz;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.DialogFragment;

//TODO implementación de la interfaz para pasar los datos
public class FilterPaisDialogFragment extends DialogFragment{
    View v;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Filtrado de Países");

        builder.setCancelable(false);

        v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_filtrado_paises, null);
        builder.setView(v);



        //Guardar
        builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //TODO guardar opción filtro

                    dialog.dismiss();
            }

            public void DatosFiltro(){

            }
        });

        //Cancelar
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return builder.create();

    }
}
