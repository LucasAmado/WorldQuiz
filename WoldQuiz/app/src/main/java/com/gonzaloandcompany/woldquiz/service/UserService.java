package com.gonzaloandcompany.woldquiz.service;

import com.gonzaloandcompany.woldquiz.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserService {
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static FirebaseUser currentUser = mAuth.getCurrentUser();

    public static void addPointsUser(int points){
        db.collection("users")
                .document(currentUser.getUid()).update("puntos", FieldValue.increment(points));
    }

    public static void addGames(){
        int increment = 1;
        db.collection("users")
                .document(currentUser.getUid()).update("partidas", FieldValue.increment(increment));
    }
}
