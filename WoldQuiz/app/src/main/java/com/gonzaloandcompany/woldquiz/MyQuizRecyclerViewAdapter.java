package com.gonzaloandcompany.woldquiz;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gonzaloandcompany.woldquiz.models.Answer;
import com.gonzaloandcompany.woldquiz.models.QuestionType;
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

        int correctAnswerIndex = -1;
        int pointCorrectAnswer = 1;
        String question;
        holder.mItem = quizzes.get(position);

        if (holder.mItem.getType().equals(QuestionType.FLAG)) {
            Glide.
                    with(context)
                    .load(holder.mItem.getQuestion())
                    .centerCrop()
                    .error(android.R.drawable.stat_notify_error)
                    .into(holder.flag);
            holder.flag.setVisibility(View.VISIBLE);
        } else {
            holder.flag.setVisibility(View.GONE);
        }

        holder.question.setText(holder.mItem.getQuestion().getQuestion());

        for (int i = 0; i < holder.mItem.getAnswers().size(); i++) {
            ((RadioButton) holder.radioGroup.getChildAt(i)).setText(holder.mItem.getAnswers().get(i).getAnswer());

        }
        holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectedRadioButtonId;
                selectedRadioButtonId = holder.radioGroup.getCheckedRadioButtonId();
                holder.mItem.setSelected(holder.mItem.getAnswers().get(selectedRadioButtonId));
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
        private TextView question;
        private ImageView flag;
        private RadioGroup radioGroup;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            question = view.findViewById(R.id.question);
            radioGroup = view.findViewById(R.id.radioGroup);
            flag = view.findViewById(R.id.flag);

        }

    }
}
