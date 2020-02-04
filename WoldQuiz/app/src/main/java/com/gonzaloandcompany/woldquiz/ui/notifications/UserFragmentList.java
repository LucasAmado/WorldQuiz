package com.gonzaloandcompany.woldquiz.ui.notifications;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gonzaloandcompany.woldquiz.R;
import com.gonzaloandcompany.woldquiz.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class UserFragmentList extends Fragment {

    private int mColumnCount = 1;
    private IUserListener mListener;
    private List<User> userList;
    MyUserRecyclerViewAdapter adapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView recyclerView;

    public UserFragmentList() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            userList = new ArrayList<>();

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
                                            Integer.parseInt(d.getData().get("partidas").toString()),
                                            Integer.parseInt(d.getData().get("puntos").toString())
                                    ));

                                    Collections.sort(userList, new Comparator<User>() {
                                        @Override
                                        public int compare(User u1, User u2) {
                                            return -(u1.getPuntos() - u2.getPuntos());
                                        }
                                    });

                                    adapter = new MyUserRecyclerViewAdapter(
                                            getActivity(),
                                            userList,
                                            mListener
                                    );

                                    recyclerView.setAdapter(adapter);
                                }

                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                            }
                        }
                    });

        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IUserListener) {
            mListener = (IUserListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement IUserListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
