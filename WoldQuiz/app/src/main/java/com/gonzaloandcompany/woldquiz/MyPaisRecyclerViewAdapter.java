package com.gonzaloandcompany.woldquiz;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gonzaloandcompany.woldquiz.dummy.DummyContent.DummyItem;
import com.gonzaloandcompany.woldquiz.models.Pais;

import java.util.List;


public class MyPaisRecyclerViewAdapter extends RecyclerView.Adapter<MyPaisRecyclerViewAdapter.ViewHolder> {

    private List<Pais> listaPaises;
    private IPaisesListener paisesListener;

    public MyPaisRecyclerViewAdapter(List<Pais> listaPaises, IPaisesListener paisesListener) {
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
        //holder.mIdView.setText(listaPaises.get(position).id);
        //holder.mContentView.setText(listaPaises.get(position).content);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != paisesListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    paisesListener.onClickPais(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaPaises.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Pais mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
