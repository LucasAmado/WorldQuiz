package com.gonzaloandcompany.woldquiz;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gonzaloandcompany.woldquiz.models.Pais;

import java.util.List;


public class MyPaisRecyclerViewAdapter extends RecyclerView.Adapter<MyPaisRecyclerViewAdapter.ViewHolder> {

    private Context ctx;
    private List<Pais> listaPaises;
    private IPaisesListener paisesListener;

    public MyPaisRecyclerViewAdapter(Context ctx, List<Pais> listaPaises, IPaisesListener paisesListener) {
        ctx = ctx;
        listaPaises = listaPaises;
        paisesListener = paisesListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_pais, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = listaPaises.get(position);
        holder.tvPais.setText(holder.mItem.getName());
        holder.tvCapital.setText(holder.mItem.getCapital());
        holder.tvmoneda.setText(holder.mItem.getCurrencies().getClass().getName());
        holder.tvidioma.setText(holder.mItem.getLanguages().getClass().getName());

        Glide.with(ctx)
                .load(holder.urlImagen)
                .into(holder.bandera);
       // holder.mView.setOnClickListener(new View.OnClickListener() {
       // });
    }

    @Override
    public int getItemCount() {
        return listaPaises.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public TextView tvPais;
        public TextView tvCapital;
        public TextView tvmoneda;
        public TextView tvidioma;
        public ImageView bandera;
        public ImageView urlImagen;
        public Pais mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvPais = (TextView) view.findViewById(R.id.textViewDetallePais);
            tvCapital = (TextView) view.findViewById(R.id.textViewCapital);
            tvmoneda = (TextView) view.findViewById(R.id.textViewMoneda);
            tvidioma = (TextView) view.findViewById(R.id.textViewIdioma);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tvPais.getText() + "'";
        }
    }
}
