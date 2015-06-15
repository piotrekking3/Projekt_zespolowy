package com.example.mrlukashem.utrudnieniaruchu;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.*;

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
    private View commentsView;
    private ProblemInstance problem;
    private CustomMarkerContentAdapter adapter;


    /*
        Metoda służaca do konstrukcji obiektu.
        @param1 konkretny obiekt ProblemInstance jaki ma zostać wyświetlony
        @return instancja MarkerContentDialogFragment
     */
    public static MarkerContentDialogFragment newInstance(ProblemInstance __problem) {
        MarkerContentDialogFragment _instance =
                new MarkerContentDialogFragment();
        if(__problem == null) {
            throw new NullPointerException();
        }
        _instance.problem = __problem;

        return _instance;
    }

    public void changeLayoutOnCommentLay() {
        LayoutInflater _inflater = getActivity().getLayoutInflater();
        commentsView = _inflater.inflate(R.layout.dialog_comments, null);

        adapter = new CustomMarkerContentAdapter(getActivity().getApplicationContext(), R.id.commentsContainter);
        adapter.addAll(Comment.getInstance("dupa", "10/05/15", "chuja mi zrobicie!"),
                Comment.getInstance("dupa", "10/05/15", "chuja mi zrobicie!"), Comment.getInstance("dupa", "10/05/15", "chuja mi zrobicie!"),
                Comment.getInstance("dupa", "10/05/15", "chuja mi zrobicie!"), Comment.getInstance("dupa", "10/05/15", "chuja mi zrobicie!"));

        ListView _list = (ListView)commentsView.findViewById(R.id.commentsContainter);
        _list.setAdapter(adapter);

        getDialog()
                .setContentView(commentsView);
    }

    /*
        Metoda wywoływana przy tworzeniu.
        Przypisywanie elementów z instancji ProblemInstance do konkretnych pól tekstowych.
     */
    @Override
    public Dialog onCreateDialog(Bundle __saved_instance_state) {
        super.onCreate(__saved_instance_state);
        AlertDialog.Builder _builder = new AlertDialog.Builder(getActivity());

        LayoutInflater _inflater = getActivity().getLayoutInflater();
        customView = _inflater.inflate(R.layout.dialog_problem_info, null);

        TextView _title = (TextView)customView.findViewById(R.id.categoryNameTextView);
        TextView _content = (TextView)customView.findViewById(R.id.categoryContentTextView);
        TextView _address = (TextView)customView.findViewById(R.id.addressDialog);
        TextView _calendar = (TextView)customView.findViewById(R.id.calendarDialog);

        String _address_value = problem.getAddress();
        String _calendar_value = problem.getDate();
        if(_address_value != null) {
            _address.setText(problem.getAddress());
        }
        if(_calendar_value != null) {
            _calendar.setText(problem.getDate());
        }

        _title.setText(problem.getCategoryString());
        _content.setText(problem.getContent());

        new CallAPI.GetImgFromHttp((ImageView)customView.findViewById(R.id.imgView)).execute(problem.getId());

        _builder.setTitle("")
                .setView(customView)
                .setNeutralButton("Pokaż komentarze", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent _intent = new Intent();
                        _intent.setClass(getActivity().getApplicationContext(), CommentsDialogFragment.class);
                        _intent.putExtra("id", problem.getId());

                        dismiss();
                        getActivity().startActivity(_intent);
                    }
                })
                .setNegativeButton("Wróc", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface __dialog, int __which) {
                        dismiss();
                    }
                });

        return _builder.create();
    }
}
