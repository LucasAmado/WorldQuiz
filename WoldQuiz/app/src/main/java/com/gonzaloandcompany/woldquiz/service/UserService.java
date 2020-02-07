package com.gonzaloandcompany.woldquiz.service;

import android.util.Log;

import androidx.annotation.NonNull;

import com.gonzaloandcompany.woldquiz.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserService {
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static FirebaseUser currentUser = mAuth.getCurrentUser();

    public static void addPointsUser(int points) {
        db.collection("users")
                .document(currentUser.getUid()).update("puntos", FieldValue.increment(points));
    }

    public static void addGames() {
        int increment = 1;
        db.collection("users")
                .document(currentUser.getUid()).update("partidas", FieldValue.increment(increment));
    }

    public static void getUser() {
        db.collection("users").document(currentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        document.getData();
                        Log.d("DOCUMENT", document.getData().toString());
                    } else {

                    }
                } else {
                    Log.d("get failed with ", task.getException().toString());
                }

            }
        });
    }
}
