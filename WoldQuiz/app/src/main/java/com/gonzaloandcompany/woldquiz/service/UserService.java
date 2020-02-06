package com.gonzaloandcompany.woldquiz.service;

import android.util.Log;

import com.gonzaloandcompany.woldquiz.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserService  {
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static FirebaseUser currentUser = mAuth.getCurrentUser();
    private static User user =  new User();

    public UserService() {
    }

    public static void addPointsUser(int points) {
        db.collection("users")
                .document(currentUser.getUid()).update("puntos", FieldValue.increment(points));
    }

    public static void addGames() {
        int increment = 1;
        db.collection("users")
                .document(currentUser.getUid()).update("partidas", FieldValue.increment(increment));
    }

    public static Task<DocumentSnapshot> getUser() {
        return db.collection("users").document(currentUser.getUid()).get();
        /*return db.collection("users")
                .document(currentUser.getUid())
                .get()
                .getResult().toObject(User.class);     */
        /*db.collection("users").document(currentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            DocumentSnapshot document;

            @Override
            public void onComplete(Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    document = task.getResult();
                    if (document.exists()) {

                        user.setNombre(document.getString("nombre"));
                        user.setEmail(document.getString("email"));
                        user.setPartidas(Integer.parseInt(document.getString("partidas")));
                        user.setUrlFoto(document.getString("urlFoto"));
                        user.setPuntos(Integer.parseInt(document.getString("puntos")));

                    } else {
                        Log.d("get failed with ", task.getException().toString());
                        document = null;
                    }
                } else {

                    document = null;
                }

            }

        });
               return null;  */
    
    }
}
