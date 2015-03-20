package com.example.mrlukashem.utrudnieniaruchu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by mrlukashem on 19.03.15.
 */
public class NavDrawerArrayAdapter extends ArrayAdapter<NavDrawerItem> {

    private LayoutInflater inflater;

    public NavDrawerArrayAdapter(Context __context, int __resource) {
        super(__context, __resource);
    }

    public void setAdapterItems(List<NavDrawerItem> __list) {
        this.setAdapterItems(__list);
    }

    public void setAdapterItems(NavDrawerItem[] __array) {
        this.setAdapterItems(__array);
    }

    @Override
    public View getView(int __position, View __convertView, ViewGroup __parent) {
        View _result_view = null;
        NavDrawerItem _item = this.getItem(__position);

        if(_item.getType() == NavDrawerTitleItem.TYPE) {
            _result_view = getTitleItem(__position, __convertView, __parent);
        }
        else
        if(_item.getType() == NavDrawerSectionItem.TYPE) {
            _result_view = getSectionItem(__position, __convertView, __parent);
        }

        return _result_view;
    }

    public View getTitleItem(int __position, View __convertVIew, ViewGroup __parent) {
        if(__convertVIew == null) {
            View _result_view = inflater.inflate(R.layout.drawer_title_item, __parent, false);
            TextView _text_view = (TextView)_result_view.findViewById(R.id.drawer_title_text_view);

            NavDrawerItem _item = this.getItem(__position);
            _text_view.setText(_item.getLabel());

            return _result_view;
        }

        return __convertVIew;
    }

    public View getSectionItem(int __position, View __convertVIew, ViewGroup __parent) {
        if(__convertVIew == null) {
            View _result_view = inflater.inflate(R.layout.drawer_list_item, __parent, false);
            TextView _text_view = (TextView)_result_view.findViewById(R.id.drawer_text_view_list_item);
            ImageView _img_view = (ImageView)_result_view.findViewById(R.id.drawer_image_view_list_item);

            NavDrawerItem _item = this.getItem(__position);
            _text_view.setText(_item.getLabel());

            return _result_view;
        }

        return __convertVIew;
    }
}

