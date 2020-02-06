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
import com.gonzaloandcompany.woldquiz.service.UserService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;

public class UserDetailActivity extends AppCompatActivity {
    ImageView avatar, problemImg;
    TextView name, email, score, games, problem;
    User user = new User();
    ProgressBar progressBar;

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
        new getUser().execute();





    }
    public class getUser extends AsyncTask<Void,Void, User>{
        @Override
        protected User doInBackground(Void... voids) {
            return UserService.getUser();
        }

        @Override
        protected void onPostExecute(User u) {
            user=u;
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
                games.setVisibility(View.VISIBLE);
                score.setVisibility(View.VISIBLE);

            } else {
                problemImg.setVisibility(View.VISIBLE);
                problem.setVisibility(View.VISIBLE);
            }
        }
    }
}
