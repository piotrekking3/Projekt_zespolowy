package com.example.mrlukashem.utrudnieniaruchu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrlukashem on 19.03.15.
 */
public class NavDrawerArrayAdapter extends ArrayAdapter<NavDrawerItem> {

    private LayoutInflater inflater;

    public NavDrawerArrayAdapter(Context __context, int __resource) {
        super(__context, __resource);
        inflater = LayoutInflater.from(__context);
    }

    public void setAdapterItems(List<NavDrawerItem> __list) {
        this.setAdapterItems(__list);
    }

    public void setAdapterItems(NavDrawerItem[] __array) {
        this.setAdapterItems(__array);
    }

 //   public void makeItemsFromRes() {

 //   }

    public void makeAndSetItems() {
        List<NavDrawerItem> _items_list =
                new ArrayList<>();

    //    _items_list.add(new NavDrawerTitleItem("Pomoc"));

        this.addAll(_items_list);
    }

    @Override
    public View getView(int __position, View __convertView, ViewGroup __parent) {
        NavDrawerItem _item = this.getItem(__position);

        if(__convertView == null) {
            if (_item.getType() == NavDrawerTitleItem.TYPE) {
                __convertView = getTitleItem(__position, __convertView, __parent);
            } else if (_item.getType() == NavDrawerSectionItem.TYPE) {
                __convertView = getSectionItem(__position, __convertView, __parent);
            }
        }

        return __convertView;
    }

    public View getTitleItem(int __position, View __convertVIew, ViewGroup __parent) {
        if(__convertVIew == null) {
            __convertVIew = inflater.inflate(R.layout.drawer_title_item, __parent, false);
            TextView _text_view = (TextView)__convertVIew.findViewById(R.id.drawer_title_text_view);

            NavDrawerItem _item = this.getItem(__position);
            _text_view.setText(_item.getLabel());

            return __convertVIew;
        }

        return __convertVIew;
    }

    public View getSectionItem(int __position, View __convertVIew, ViewGroup __parent) {
        if(__convertVIew == null) {
            __convertVIew = inflater.inflate(R.layout.drawer_list_item, __parent, false);
            TextView _text_view = (TextView)__convertVIew.findViewById(R.id.drawer_text_view_list_item);
            ImageView _img_view = (ImageView)__convertVIew.findViewById(R.id.drawer_image_view_list_item);

            NavDrawerSectionItem _item = (NavDrawerSectionItem)this.getItem(__position);
            _text_view.setText(_item.getLabel());
            _img_view.setImageResource(_item.getIconId());

            return __convertVIew;
        }

        return __convertVIew;
    }
}

