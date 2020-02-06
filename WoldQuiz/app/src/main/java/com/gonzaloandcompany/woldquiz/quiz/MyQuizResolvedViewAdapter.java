package com.gonzaloandcompany.woldquiz.quiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gonzaloandcompany.woldquiz.R;
import com.gonzaloandcompany.woldquiz.models.QuestionType;
import com.gonzaloandcompany.woldquiz.models.Quiz;

import java.util.List;

public class MyQuizResolvedViewAdapter extends RecyclerView.Adapter<MyQuizResolvedViewAdapter.ViewHolder> {
    private final List<Quiz> quizzes;
    private final Context context;
    private final IQuizListener mListener;

    public MyQuizResolvedViewAdapter(List<Quiz> quizzes, Context context, IQuizListener mListener) {
        this.quizzes = quizzes;
        this.context = context;
        this.mListener = mListener;
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_quiz, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.mItem = quizzes.get(position);

        if (holder.mItem.getType().equals(QuestionType.FLAG)) {
            holder.question.setText(holder.mItem.getType().getDescription());
            Glide.
                    with(context)
                    .load("https://www.countryflags.io/" + holder.mItem.getQuestion().getQuestion() + "/flat/64.png")
                    .centerCrop()
                    .error(android.R.drawable.stat_notify_error)
                    .into(holder.flag);
            holder.flag.setVisibility(View.VISIBLE);
        } else {
            holder.flag.setVisibility(View.GONE);
            holder.question.setText(holder.mItem.getQuestion().getQuestion());
        }


        for (int i = 0; i < holder.mItem.getAnswers().size(); i++) {
            RadioButton radioButton =((RadioButton) holder.radioGroup.getChildAt(i));
            radioButton.setEnabled(false);
            //TEXTO
            if (holder.mItem.getAnswers().get(i).getAnswer().isEmpty() || holder.mItem.getAnswers().get(i).getAnswer() == null)
                radioButton.setText("Ninguna de las respuestas propuestas son correctas");
            else
                radioButton.setText(holder.mItem.getAnswers().get(i).getAnswer());

            //SELECCIONADOS
            if(holder.mItem.getSelected().equals(holder.mItem.getAnswers().get(i))){
                radioButton.setEnabled(true);
                radioButton.setChecked(true);
                if(holder.mItem.getSelected().equals(holder.mItem.getCorrect()))
                    radioButton.setBackgroundResource(R.drawable.radiobutton_quiz_green);
                    //radioButton.setTextColor(context.getResources().getColor(android.R.color.holo_green_dark));
                else {
                    radioButton.setBackgroundResource(R.drawable.radiobutton_quiz_red);
                    radioButton.setTextColor(context.getResources().getColor(android.R.color.white));
                    //radioButton.setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));
                }
            }else if(holder.mItem.getCorrect().equals(holder.mItem.getAnswers().get(i))){
                radioButton.setBackgroundResource(R.drawable.radiobutton_quiz_green);
                //radioButton.setTextColor(context.getResources().getColor(android.R.color.holo_green_dark));
            }
        }


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
