package com.gonzaloandcompany.woldquiz;

import android.content.Context;
import android.os.AsyncTask;
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
import com.gonzaloandcompany.woldquiz.models.Answer;
import com.gonzaloandcompany.woldquiz.models.Currency;
import com.gonzaloandcompany.woldquiz.models.Language;
import com.gonzaloandcompany.woldquiz.models.Pais;
import com.gonzaloandcompany.woldquiz.models.Question;
import com.gonzaloandcompany.woldquiz.models.QuestionType;
import com.gonzaloandcompany.woldquiz.models.Quiz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
            /*Pais p1 = new Pais();
            p1.setName("España");
            p1.setCapital("Madrid");
            p1.getCurrencies().add(new Currency(null, "Euro", "€"));
            p1.getLanguages().add(new Language("ES", "es", "Spanish", "Español"));
            p1.setFlag("https://www.comprarbanderas.es/images/banderas/400/60-espana-sin-escudo_400px.jpg");

            Pais p2 = new Pais();
            p2.setName("Reino Unido");
            p2.setCapital("London");
            p2.getCurrencies().add(new Currency(null, "Libra", "£"));
            p2.getLanguages().add(new Language("EN", "en", "English", "English"));
            p2.setFlag("https://images-na.ssl-images-amazon.com/images/I/51tBiLKn9aL._SX522_.jpg");

            Pais p3 = new Pais();
            p3.setName("México");
            p3.setCapital("Ciudad de México");
            p3.getCurrencies().add(new Currency(null, "Peso", "$"));
            p3.getLanguages().add(new Language("MX", "mx", "Spanish", "Español"));
            p3.setFlag("https://upload.wikimedia.org/wikipedia/commons/thumb/f/fc/Flag_of_Mexico.svg/1024px-Flag_of_Mexico.svg.png");


            Pais p4 = new Pais();
            p4.setName("Francia");
            p4.setCapital("Paris");
            p4.getCurrencies().add(new Currency(null, "Euro", "€"));
            p4.getLanguages().add(new Language("FR", "fr", "French", "Français"));
            p4.setFlag("https://es.wikipedia.org/wiki/Bandera_de_Francia#/media/Archivo:Flag_of_France.svg");


            Pais p5 = new Pais();
            p5.setName("Italia");
            p5.setCapital("Roma");
            p4.getCurrencies().add(new Currency(null, "Euro", "€"));
            p5.getLanguages().add(new Language("IT", "it", "Italian", "Italian"));
            p5.setFlag("https://upload.wikimedia.org/wikipedia/commons/thumb/0/03/Flag_of_Italy.svg/1024px-Flag_of_Italy.svg.png");

            List<Pais> paises = new ArrayList<>();
            paises.add(p1);
            paises.add(p2);
            paises.add(p3);
          */
            quizzes = new ArrayList<>();
            quizRecyclerViewAdapter = new MyQuizRecyclerViewAdapter(quizzes, context, mListener);
            recyclerView.setAdapter(quizRecyclerViewAdapter);
            paisService = ServiceGeneratorPais.createService(PaisService.class);
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

    public class loadRandomCountries extends AsyncTask<Void, Void, List<Pais>> {
        @Override
        protected void onPreExecute() {
            //aquí un progress bar
        }

        @Override
        protected void onPostExecute(List<Pais> paises) {

            int numberOfCountries = 10;
            List<Answer> answers = new ArrayList<>();
            Pais p;
            QuestionType type=null;
            String question = "";
            List<Pais> randomCountries = new ArrayList<>();

            for (int i = 0; i < numberOfCountries; i++) {
                p = getCountryRandom(paises, null);

                //TYPE 0
                if (i < 2) {
                    type= QuestionType.CAPITAL;
                    question = type.getDescription().replace("x", p.getName());
                    answers = Arrays.asList(
                            new Answer(p.getCapital()),
                            new Answer(getCountryRandom(paises, p).getCapital()),
                            new Answer(getCountryRandom(paises, p).getCapital())
                    );

                } else if (i > 1 && i < 4) {
                    type =  QuestionType.CURRENCY;
                    question =type.getDescription().replace("x", p.getCurrencies().get(0).getName() + " (" + p.getCurrencies().get(0).getSymbol()) + ")";
                    answers = Arrays.asList(
                            new Answer(p.getName()),
                            new Answer(getCountryRandom(paises, p).getName()),
                            new Answer(getCountryRandom(paises, p).getName())
                    );

                } else if (i > 3 && i < 6) {
                    type= QuestionType.BORDERS;
                    question = type.getDescription().replace("x", p.getName());
                    answers = Arrays.asList(
                            new Answer(getBorders(p, paises)),
                            new Answer(getBorders(getCountryRandom(paises, p), paises)),
                            new Answer(getBorders(getCountryRandom(paises, p), paises))
                    );

                } else if (i > 5 && i < 8) {
                    type =  QuestionType.FLAG;
                    question =type.getDescription();
                    answers = Arrays.asList(
                            new Answer(p.getFlag()),
                            new Answer(getCountryRandom(paises, p).getFlag()),
                            new Answer(getCountryRandom(paises, p).getFlag())
                    );

                } else if (i > 7 && i < 10) {
                    type = QuestionType.LANGUAGE;
                    question = type.getDescription().replace("x", p.getName());
                    answers = Arrays.asList(
                            new Answer(p.getLanguages().get(0).getName()),
                            new Answer(getCountryRandom(paises, p).getLanguages().get(0).getName()),
                            new Answer(getCountryRandom(paises, p).getLanguages().get(0).getName())
                    );
                }

                quizzes.add(new Quiz(new Question(question), answers, null, answers.get(0), 0,type));

            }

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

    public String getBorders(Pais pais, List<Pais> paises) {
        String result = "";
        for (String border : pais.getBorders()) {
            for (Pais p : paises) {
                if (p.getAlpha3Code().contains(border)) {
                    result = p.getName() + " ";
                }
            }
        }
        return result;
    }

}

