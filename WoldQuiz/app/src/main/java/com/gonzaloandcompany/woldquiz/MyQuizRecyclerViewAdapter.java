package com.gonzaloandcompany.woldquiz;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.gonzaloandcompany.woldquiz.models.Quiz;

import java.util.List;

public class MyQuizRecyclerViewAdapter extends RecyclerView.Adapter<MyQuizRecyclerViewAdapter.ViewHolder> {

    private final List<Quiz> quizzes;
    private final Context context;
    private final IQuizListener mListener;

    public MyQuizRecyclerViewAdapter(List<Quiz> quiz, Context context, IQuizListener mListener) {
        this.quizzes = quiz;
        this.context = context;
        this.mListener = mListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_quiz, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = quizzes.get(position);


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return quizzes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public Quiz mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;

        }

    }
}
