package com.gonzaloandcompany.woldquiz.quiz;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gonzaloandcompany.woldquiz.R;
import com.gonzaloandcompany.woldquiz.models.QuestionType;
import com.gonzaloandcompany.woldquiz.models.Quiz;

import java.util.List;

public class MyQuizRecyclerViewAdapter extends RecyclerView.Adapter<MyQuizRecyclerViewAdapter.ViewHolder> {

    private final List<Quiz> quizzes;
    private final Context context;
    private final IQuizListener mListener;
    private int lastChecked;

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
            RadioButton radioButton =((RadioButton)holder.radioGroup.getChildAt(i));
            if (holder.mItem.getAnswers().get(i).getAnswer().isEmpty() || holder.mItem.getAnswers().get(i).getAnswer() == null)
                radioButton.setText(radioButton.getText().toString()+" "+"Ninguna de las respuestas propuestas son correctas");
            else
                radioButton.setText(radioButton.getText().toString()+" "+holder.mItem.getAnswers().get(i).getAnswer());
        }

        holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String idRadioButton;
                RadioButton radioButton;
                RadioButton rb;
                int point = 1;
                int incorrect = 0;
                radioButton = (RadioButton) holder.mView.findViewById(checkedId);


                if(radioButton.isChecked()){

                    radioButton.setBackgroundResource(R.drawable.radiobutton_quiz_checked);

                    for( int i =0;i<group.getChildCount();i++){
                        rb = (RadioButton)holder.mView.findViewById(group.getChildAt(i).getId());
                        if(!rb.equals(radioButton)){
                            rb.setBackgroundResource(R.drawable.radiobutton_quiz);
                        }
                    }

                }

                idRadioButton = radioButton.getResources().getResourceEntryName(radioButton.getId());

                idRadioButton = idRadioButton.substring(idRadioButton.length() - 1);

                holder.mItem.setSelected(holder.mItem.getAnswers().get(Integer.parseInt(idRadioButton) - 1));

                if (holder.mItem.getSelected().equals(holder.mItem.getCorrect()))
                    holder.mItem.setPoints(point);
                else
                    holder.mItem.setPoints(incorrect);

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

    public boolean checkAllRadioButtonAreAnswered() {
        for (Quiz q : quizzes) {
            if (q.getSelected() == null || q.getSelected().getAnswer().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public List<Quiz> getResultOfQuiz() {
        return quizzes;
    }


}
