package com.gonzaloandcompany.woldquiz.ui.users;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.gonzaloandcompany.woldquiz.R;
import com.gonzaloandcompany.woldquiz.models.UserEntity;

import java.util.List;


public class MyUserRecyclerViewAdapter extends RecyclerView.Adapter<MyUserRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private final List<UserEntity> mValues;
    private final IUserListener mListener;

    public MyUserRecyclerViewAdapter(Context context, List<UserEntity> mValues, IUserListener mListener) {
        this.context = context;
        this.mValues = mValues;
        this.mListener = mListener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.tvNombre.setText(holder.mItem.getNombre());
        holder.tvPuntos.setText(String.valueOf(holder.mItem.getPuntos()));
        holder.tvPartidas.setText(String.valueOf(holder.mItem.getPartidas())+" partidas");

        Glide
                .with(context)
                .load(holder.mItem.getUrlFoto())
                .centerCrop()
                .into(holder.ivFoto);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onUserClick(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tvNombre, tvPuntos, tvPartidas;
        public final ImageView ivFoto;
        public UserEntity mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvNombre = view.findViewById(R.id.textViewNombre);
            tvPuntos = view.findViewById(R.id.textViewPntos);
            tvPartidas = view.findViewById(R.id.textViewPartidas);
            ivFoto = view.findViewById(R.id.imageViewFoto);
        }

    }
}
