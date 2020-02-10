package com.gonzaloandcompany.woldquiz.quiz;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.gonzaloandcompany.woldquiz.R;

public class QuizDialogFragment extends DialogFragment {
    View view;
    TextView message;
    LottieAnimationView animation;
    private int result;

    public QuizDialogFragment(int result) {
        this.result = result;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_quiz_dialog,null);

        message= view.findViewById(R.id.quizDialogMessage);
        animation = view.findViewById(R.id.quizAnimation);

        if(result==5) {
            animation.setAnimation("stars.json");
            message.setText("¡Enhorabuena, eres un máquina!");
        }else if(result==4) {
            animation.setAnimation("four_stars.json");
            message.setText("¡Bien! Estás más cerca de que te lleven a DisneyLand por tan buenas notas." +
                    "\n¡Vamos!");

        }else if (result==3) {
            animation.setAnimation("three_stars.json");
            message.setText("¡No está nada mal!" +
                    "\n Pero con estas notas no puedes pedir aún un poni a tus padres." +
                    "\n ¡No te rindas, sigue mejorando!");

        }else if (result==2) {
            animation.setAnimation("two_stars.json");
            message.setText("Aprieta un poco más." +
                    "\n¡Tú puedes!");

        } else if (result==1) {
            animation.setAnimation("one_star.json");
            message.setText("Vaya vaya ... parece que hay uno que se queda si playa este verano ... ");

        }else if (result==0) {
            animation.setAnimation("zero_stars.json");
            message.setText("Quizás deberías estudiar antes de jugar ...");

        }

        animation.playAnimation();

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());

        dialogBuilder.setView(view);
        dialogBuilder.setCancelable(true);
        dialogBuilder.setNeutralButton("Cerrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    return  dialogBuilder.create();
    }
}
