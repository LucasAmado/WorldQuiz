package com.gonzaloandcompany.woldquiz.ui.notifications;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.gonzaloandcompany.woldquiz.MyPaisRecyclerViewAdapter;
import com.gonzaloandcompany.woldquiz.R;
import com.gonzaloandcompany.woldquiz.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    List<User> userList = new ArrayList<>();
    FirebaseFirestore db;
    ListView lvUsers;
    ArrayAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        lvUsers = root.findViewById(R.id.lvUsers);

        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot d : task.getResult()) {
                                userList.add(new User(
                                        d.getData().get("nombre").toString(),
                                        d.getData().get("urlFoto").toString(),
                                        d.getData().get("email").toString(),
                                        Integer.parseInt(d.getData().get("partidas").toString()),
                                        Integer.parseInt(d.getData().get("puntos").toString())
                                ));
                            }

                            userList.add(new User("User 1", "img", "correo@gmail.com", 10, 2));
                            userList.add(new User("User 2", "img", "correo2@gmail.com", 32, 9));
                            userList.add(new User("User 3", "img", "correo3@gmail.com", 14, 4));



                            Collections.sort(userList, new Comparator<User>() {
                                @Override
                                public int compare(User u1, User u2) {
                                    return u1.getPuntos() - u2.getPuntos();
                                }
                            });

                            //adapter
                            lvUsers.setAdapter(adapter);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });


        final TextView textView = root.findViewById(R.id.textViewLista);

        notificationsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}