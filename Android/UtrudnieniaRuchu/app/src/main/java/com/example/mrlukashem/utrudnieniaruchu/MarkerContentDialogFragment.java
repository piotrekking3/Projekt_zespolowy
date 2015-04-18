package com.example.mrlukashem.utrudnieniaruchu;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.Objects;

/**
 * Created by mrlukashem on 08.04.15.
 */

/*
    Okno dialogowe służące jako dokładny opis wybranego problemu.
    Nie tak skrótowy jak zwykłe windowInfo dla markera.
 */
public class MarkerContentDialogFragment extends DialogFragment {
    private View customView;
    private ProblemInstance problem;

    /*
        Metoda służaca do konstrukcji obiektu.
        @param1 konkretny obiekt ProblemInstance jaki ma zostać wyświetlony
        @return instancja MarkerContentDialogFragment
     */
    public static MarkerContentDialogFragment newInstance(ProblemInstance __problem) {
        MarkerContentDialogFragment _instance =
                new MarkerContentDialogFragment();
        _instance.problem = Objects.requireNonNull(__problem);

        return _instance;
    }

    /*
        Metoda wywoływana przy tworzeniu.
        Przypisywanie elementów z instancji ProblemInstance do konkretnych pól tekstowych.
     */
    @Override
    public Dialog onCreateDialog(Bundle __saved_instance_state) {
        AlertDialog.Builder _builder = new AlertDialog.Builder(getActivity());
        LayoutInflater _inflater = getActivity().getLayoutInflater();

        customView = _inflater.inflate(R.layout.dialog_problem_info, null);
        TextView _title = (TextView)customView.findViewById(R.id.categoryNameTextView);
        TextView _content = (TextView)customView.findViewById(R.id.categoryContentTextView);
        TextView _email = (TextView)customView.findViewById(R.id.emailLabelWindowInfo);
        _title.setText(problem.getCategoryString());
        _content.setText(problem.getContent());
        _email.setTag(problem.getEmail());

        _builder
                .setView(customView)
                .setTitle(R.string.dialog_problem_info_title);

        return _builder.create();
    }
}
