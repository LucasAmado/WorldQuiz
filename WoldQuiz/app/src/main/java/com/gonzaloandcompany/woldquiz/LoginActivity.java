package com.gonzaloandcompany.woldquiz;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    static final int GOOGLE_SIGN_IN = 123;
    ImageView logo;
    View background, footer;
    FirebaseAuth mAuth;
    SignInButton btn_login;
    FirebaseUser user;
    ProgressBar progressBar;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = findViewById(R.id.sign_in_button);
        progressBar = findViewById(R.id.progressBarLogin);
        logo = findViewById(R.id.imageViewLogo);
        background = findViewById(R.id.loginHalfBackground);
        footer = findViewById(R.id.loginFooter);

        Glide.with(this).load(R.drawable.logo).into(logo);

        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        btn_login.setOnClickListener(v -> SignInGoogle());

        if (mAuth.getCurrentUser() != null) {
            FirebaseUser user = mAuth.getCurrentUser();
            updateUI(user);
        }
    }

    public void SignInGoogle() {
        progressBar.setVisibility(View.VISIBLE);
        btn_login.setVisibility(View.GONE);
        logo.setVisibility(View.GONE);
        background.setVisibility(View.GONE);
        footer.setVisibility(View.GONE);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOOGLE_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null){
                    firebaseAuthWithGoogle(account);
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    this.finish();
                }
            } catch (ApiException e) {
                Log.w("TAG", "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        progressBar.setVisibility(View.INVISIBLE);

                        user = mAuth.getCurrentUser();
                        updateUI(user);


                    } else {
                        progressBar.setVisibility(View.INVISIBLE);

                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }


                });
    }

    private void updateUI(FirebaseUser user) {


        if (user != null) {
            Map<String, Object> data = new HashMap<>();
            data.put("nombre", user.getDisplayName());
            data.put("email", user.getEmail());
            data.put("urlFoto", String.valueOf(user.getPhotoUrl()));
            data.put("partidas", 0);
            data.put("puntos", 0);

            db.collection("users")
                    .document(user.getUid())
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();

                            if (!document.exists()) {
                                db.collection("users").document(user.getUid()).set(data);
                            }
                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    });





        }


    }
}
