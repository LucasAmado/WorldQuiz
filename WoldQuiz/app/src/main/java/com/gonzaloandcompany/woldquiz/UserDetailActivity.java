package com.gonzaloandcompany.woldquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gonzaloandcompany.woldquiz.models.User;
import androidx.appcompat.app.AppCompatActivity;

import com.gonzaloandcompany.woldquiz.service.UserService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;

import java.io.IOException;

public class UserDetailActivity extends AppCompatActivity {
    ImageView avatar, problemImg;
    TextView name, email, score, games, problem, scoreTitle, gamesTitle;
    User user = new User();
    ProgressBar progressBar;
    View divider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        avatar = findViewById(R.id.userDetailAvatar);
        problemImg = findViewById(R.id.userDetailProblem);
        name = findViewById(R.id.userDetailName);
        email = findViewById(R.id.userDetailEmail);
        games = findViewById(R.id.userDetailNumberGames);
        score = findViewById(R.id.userDetailScore);
        problem = findViewById(R.id.problemMessage);
        progressBar = findViewById(R.id.userDetailProgressBar);
        divider = findViewById(R.id.userDetailDivider);
        scoreTitle = findViewById(R.id.userDetailTitleScore);
        gamesTitle = findViewById(R.id.userDetailGamesTitle);
        if (UserService.getUser() != null)
            UserService.getUser().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

                @Override
                public void onComplete(Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {

                            user.setNombre(document.getString("nombre"));
                            user.setEmail(document.getString("email"));
                            user.setPartidas(Integer.parseInt(document.get("partidas").toString()));
                            user.setUrlFoto(document.getString("urlFoto"));
                            user.setPuntos(Integer.parseInt(document.get("puntos").toString()));
                            Log.d("USER", user.toString());

                        } else {
                            Log.d("get failed with ", task.getException().toString());
                            user = null;
                        }
                    } else {

                        user = null;
                    }


                    progressBar.setVisibility(View.GONE);

                    if (user != null) {

                        name.setText(user.getNombre());
                        email.setText(user.getEmail());
                        games.setText(String.valueOf(user.getPartidas()));
                        score.setText(String.valueOf(user.getPuntos()));

                        if (user.getUrlFoto() != null)
                            Glide.with(UserDetailActivity.this).load(user.getUrlFoto()).circleCrop().into(avatar);
                        else
                            Glide.with(UserDetailActivity.this).load(R.drawable.default_avatar).circleCrop().into(avatar);

                        avatar.setVisibility(View.VISIBLE);
                        name.setVisibility(View.VISIBLE);
                        email.setVisibility(View.VISIBLE);
                        divider.setVisibility(View.VISIBLE);
                        games.setVisibility(View.VISIBLE);
                        score.setVisibility(View.VISIBLE);
                        scoreTitle.setVisibility(View.VISIBLE);
                        gamesTitle.setVisibility(View.VISIBLE);

                    } else {
                        showTechProblems();
                    }

                }

            });

        else
            showTechProblems();
    }

    public void showTechProblems(){
        problemImg.setVisibility(View.VISIBLE);
        problem.setVisibility(View.VISIBLE);
    }

}
