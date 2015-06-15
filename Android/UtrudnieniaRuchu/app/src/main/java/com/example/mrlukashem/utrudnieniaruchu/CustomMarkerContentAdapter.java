package com.example.mrlukashem.utrudnieniaruchu;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.zip.Inflater;

/**
 * Created by mrlukashem on 10.05.15.
 */
public class CustomMarkerContentAdapter extends ArrayAdapter<Comment> {

    private Context context;
    private LayoutInflater inflater;

    public CustomMarkerContentAdapter(Context __context, int __resource) {
        super(__context, __resource);
        context = __context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int __pos, View __conv_view, ViewGroup __parent) {
        if(__conv_view == null) {
            try {
                __conv_view = inflater.inflate(R.layout.comment_list_element, null);

                Comment _comment = getItem(__pos);

                TextView _comment_field = (TextView) __conv_view.findViewById(R.id.commentTextView);
                TextView _author_field = (TextView) __conv_view.findViewById(R.id.commentAuthorTextView);
                TextView _date_field = (TextView) __conv_view.findViewById(R.id.commentDateTextView);
                _comment_field.setText(_comment.getContent());
                _author_field.setText(_comment.getAuthor());

                if(_comment.getDate().length() < 19) {
                    _date_field.setText(_comment.getDate());
                    return __conv_view;
                }

                _date_field.setText(_comment.getDate().substring(0, 18));
            } catch (Exception __e) {
                Log.e("CustomMCAdapterGetView", __e.toString());
            }
        }

        return __conv_view;
    }
}
