package com.gonzaloandcompany.woldquiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gonzaloandcompany.woldquiz.models.Pais;

import java.util.List;


public class MyPaisRecyclerViewAdapter extends RecyclerView.Adapter<MyPaisRecyclerViewAdapter.ViewHolder> {

    private Context ctx;
    private List<Pais> listaPaises;
    private IPaisesListener paisesListener;

    public MyPaisRecyclerViewAdapter(Context ctx, List<Pais> listaPaises, IPaisesListener paisesListener) {
        this.ctx = ctx;
        this.listaPaises = listaPaises;
        this.paisesListener = paisesListener;
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
        holder.tvMoneda.setText(holder.mItem.getCurrencies().get(0).getName());
        holder.tvIdioma.setText(holder.mItem.getLanguages().get(0).getName());

        Glide.with(ctx)
                .load("https://www.countryflags.io/" + holder.mItem.getAlpha2Code() + "/shiny/64.png")
                .into(holder.ivBandera);
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
        public TextView tvMoneda;
        public TextView tvIdioma;
        public ImageView ivBandera;
        public Pais mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvPais = (TextView) view.findViewById(R.id.textViewDetallePais);
            tvCapital = (TextView) view.findViewById(R.id.textViewCapital);
            tvMoneda = (TextView) view.findViewById(R.id.textViewMoneda);
            tvIdioma = (TextView) view.findViewById(R.id.textViewIdioma);
            ivBandera = view.findViewById(R.id.imageViewBandera);
        }
    }
}
