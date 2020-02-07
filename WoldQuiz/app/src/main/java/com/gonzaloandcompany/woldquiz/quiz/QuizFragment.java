package com.gonzaloandcompany.woldquiz.quiz;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gonzaloandcompany.woldquiz.MainActivity;
import com.gonzaloandcompany.woldquiz.R;
import com.gonzaloandcompany.woldquiz.models.Answer;
import com.gonzaloandcompany.woldquiz.models.Pais;
import com.gonzaloandcompany.woldquiz.models.Question;
import com.gonzaloandcompany.woldquiz.models.QuestionType;
import com.gonzaloandcompany.woldquiz.models.Quiz;
import com.gonzaloandcompany.woldquiz.service.PaisService;
import com.gonzaloandcompany.woldquiz.service.ServiceGeneratorPais;
import com.gonzaloandcompany.woldquiz.service.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class QuizFragment extends Fragment {


    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private IQuizListener mListener;
    private List<Quiz> quizzes;
    private MyQuizRecyclerViewAdapter quizRecyclerViewAdapter;
    private PaisService paisService;
    private MyQuizResolvedViewAdapter quizResolvedViewAdapter;
    private boolean isFirstClick = true;


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
                             final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz_list, container, false);
        Button save = view.findViewById(R.id.quizButtonSave);


        if (view.findViewById(R.id.recyclerQuiz) != null) {
            Context context = view.getContext();
            RecyclerView recyclerView = view.findViewById(R.id.recyclerQuiz);
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            quizzes = new ArrayList<>();

            quizRecyclerViewAdapter = new MyQuizRecyclerViewAdapter(quizzes, context, mListener);
            recyclerView.setAdapter(quizRecyclerViewAdapter);

            paisService = ServiceGeneratorPais.createService(PaisService.class);

            new loadQuizzes().execute();


            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (quizRecyclerViewAdapter.checkAllRadioButtonAreAnswered() == true) {

                        if (isFirstClick == true) {
                            int result = 0;
                            for (Quiz q : quizzes) {
                                result += q.getPoints();
                            }

                            Log.d("RESULTADO DEL TEST", String.valueOf(result));
                            Log.d("TEST RESUELTO",quizzes.toString());
                            UserService.addGames();
                            UserService.addPointsUser(result);


                            quizResolvedViewAdapter = new MyQuizResolvedViewAdapter(quizzes, context, mListener);
                            recyclerView.setAdapter(quizResolvedViewAdapter);

                            save.setText("Aceptar");

                            isFirstClick = false;
                        } else {
                            isFirstClick = true;
                            Intent goToRanking = new Intent(context, MainActivity.class);
                            startActivity(goToRanking);
                        }

                    } else {
                        Toast.makeText(context, "Debes contestar a todas las preguntas", Toast.LENGTH_LONG).show();
                    }
                }
            });


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

    public class loadQuizzes extends AsyncTask<Void, Void, List<Pais>> {
        @Override
        protected void onPreExecute() {
            //aquí un progress bar
        }

        @Override
        protected void onPostExecute(List<Pais> paises) {
            int numberOfCountries = 5;
            List<Answer> answers = new ArrayList<>();
            Pais p;
            QuestionType type = null;
            String question = "";

            for (int i = 0; i < numberOfCountries; i++) {
                p = getCountryRandom(paises, null);

                //TYPE 0
                if (i == 0) {
                    type = QuestionType.CAPITAL;
                    question = type.getDescription().replace("x", p.getName());
                    answers = Arrays.asList(
                            new Answer(p.getCapital()),
                            new Answer(checkAnswerNotDuplicated(p, paises,type).getCapital()),
                            new Answer(checkAnswerNotDuplicated(p, paises,type).getCapital())
                    );

                } else if (i == 1) {
                    type = QuestionType.CURRENCY;
                    question = type.getDescription().replace("x", (p.getCurrencies().get(0).getName() + " (" + p.getCurrencies().get(0).getSymbol()) + ")");
                    answers = Arrays.asList(
                            new Answer(p.getName()),
                            new Answer(checkAnswerNotDuplicated(p, paises,type).getCurrencies().get(0).getName()),
                            new Answer(checkAnswerNotDuplicated(p, paises,type).getCurrencies().get(0).getName())
                    );

                } else if (i == 2) {
                    type = QuestionType.BORDERS;

                    question = type.getDescription().replace("x", p.getName());
                    answers = Arrays.asList(
                            new Answer(getBorders(p, paises)),
                            new Answer(getBorders(checkAnswerNotDuplicated(p, paises,type), paises)),
                            new Answer(getBorders(checkAnswerNotDuplicated(p, paises,type), paises))
                    );

                } else if (i == 3) {
                    type = QuestionType.FLAG;
                    question = p.alpha2Code;
                    answers = Arrays.asList(
                            new Answer(p.getName()),
                            new Answer(getCountryRandom(paises, p).getName()),
                            new Answer(getCountryRandom(paises, p).getName())
                    );

                } else if (i == 4) {
                    type = QuestionType.LANGUAGE;
                    question = type.getDescription().replace("x", p.getName());
                    answers = Arrays.asList(
                            new Answer(p.getLanguages().get(0).getName()),
                            new Answer(checkAnswerNotDuplicated(p, paises,type).getLanguages().get(0).getName()),
                            new Answer(checkAnswerNotDuplicated(p, paises,type).getLanguages().get(0).getName())
                    );
                }

                quizzes.add(new Quiz(new Question(question), answers, null, answers.get(0), 0, type));

            }

            for(Quiz q: quizzes){
                Collections.shuffle(q.getAnswers());
            }
            Collections.shuffle(quizzes);

            quizRecyclerViewAdapter.notifyDataSetChanged();
        }

        @Override
        protected List<Pais> doInBackground(Void... voids) {
            List<Pais> result = new ArrayList<>();

            Call<List<Pais>> allCountries = paisService.listPaises();
            Response<List<Pais>> responseListPaises = null;

            try {
                responseListPaises = allCountries.execute();
            } catch (IOException exception) {
                exception.printStackTrace();
            }

            if (responseListPaises.isSuccessful()) {
                result.addAll(responseListPaises.body());
            }
            return result;
        }
    }

    public Pais getCountryRandom(List<Pais> paises, Pais pais) {
        Pais otherCountry;
        int max = paises.size() - 1;
        int min = 0;
        int range = max - min + 1;
        int index = (int) (Math.random() * range) + min;

        if (pais == null) {
            pais = paises.get(index);
            return pais;
        } else {
            otherCountry = paises.get(index);
            if (otherCountry.equals(pais)) {
                otherCountry = getCountryRandom(paises, pais);
            }

            return otherCountry;
        }
    }

    public Pais checkAnswerNotDuplicated(Pais pais1, List<Pais> paises, QuestionType type){
        Pais pais2 = getCountryRandom(paises, pais1);
        switch (type){
            case CURRENCY:
                do{
                    if(pais1.getCurrencies().get(0).equals(pais2.getCurrencies().get(0)))
                        pais2= getCountryRandom(paises, pais1);

                }while(pais1.getCurrencies().get(0).equals(pais2.getCurrencies().get(0)));

                break;
            case CAPITAL:
                do{
                    if(pais1.getCapital().equals(pais2.getCapital()))
                        pais2= getCountryRandom(paises, pais1);

                }while(pais1.getCapital().equals(pais2.getCapital()));
                break;
            case LANGUAGE:
                do{
                    if(pais1.getLanguages().get(0).equals(pais2.getLanguages().get(0)))
                        pais2= getCountryRandom(paises, pais1);

                }while(pais1.getLanguages().get(0).equals(pais2.getLanguages().get(0)));
                break;
            case BORDERS:
                do{
                    if(pais1.getBorders().equals(pais2.getBorders()))
                        pais2= getCountryRandom(paises, pais1);
                }while(pais1.getBorders().equals(pais2.getBorders()));
                break;
        }

        return pais2;
    }

    public String getBorders(Pais pais, List<Pais> paises) {
        String result = "";
        if (pais.getBorders().isEmpty()) {
            result = "No tiene países limítrofes";
        } else {
            for (String border : pais.getBorders()) {

                for (Pais p : paises) {
                    if (p.getAlpha3Code().contains(border)) {
                        result += p.getName() + ", ";
                    }
                }
            }
            if (!result.isEmpty()) {
                result = result.substring(0, result.lastIndexOf(","));
                result += ".";
            }

        }

        return result;
    }

}

