package com.gonzaloandcompany.woldquiz;

import android.content.Context;
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
import com.gonzaloandcompany.woldquiz.models.Quiz;

import java.util.List;

public class QuizFragment extends Fragment {


    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private IQuizListener mListener;
    private List<Quiz> quizzes;

    public QuizFragment() {
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
        View view = inflater.inflate(R.layout.fragment_quiz_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            //question = holder.mItem.getType().getDescription().replace("x",holder.mItem.getQuestion().getCountry().name);


            recyclerView.setAdapter(new MyQuizRecyclerViewAdapter(quizzes,context, mListener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IQuizListener) {
            mListener = (IQuizListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement IQuizListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
