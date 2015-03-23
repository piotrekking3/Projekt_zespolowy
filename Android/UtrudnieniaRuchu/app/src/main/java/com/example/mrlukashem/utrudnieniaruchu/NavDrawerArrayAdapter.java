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

/*
    Customowy adapter dla NavigationDrawera służący do przechowywania róznych layoutów elementów
    listy drawera.
 */
public class NavDrawerArrayAdapter extends ArrayAdapter<NavDrawerItem> {

    private LayoutInflater inflater;
    private Context context;

    /*
        Konstruktor wywołujący defaultowy konstruktor ArrayAdaptera.
        Dodatkowo przypisujący polom argumenty.
        @param1 Context aplikacji
        @param2 layout
     */
    public NavDrawerArrayAdapter(Context __context, int __resource) {
        super(__context, __resource);
        inflater = LayoutInflater.from(__context);
        context = __context;
    }


    /*
        Możliwość ustawiania nowych elementów za pomocą przekazanej listy
     */
    public void setAdapterItems(List<NavDrawerItem> __list) {
        this.setAdapterItems(__list);
    }

    /*
        Możliwośc ustawienia nowych elementów za pomocą przekazanej tablicy
     */
    public void setAdapterItems(NavDrawerItem[] __array) {
        this.setAdapterItems(__array);
    }

    /*
        Metoda ustawia adapter na defaultową wartość w celu ograniczenia kodu w głównej aktywności.
        Elementy dodawane do listy są tworzone za pomocą fabryki tworzącej abstrakcyjne interfejsy.
     */
    public void makeAndSetItems() {
        List<NavDrawerItem> _items_list =
                new ArrayList<>();

        _items_list.add(NavDrawerItemFactory.newInstanceOfSection(context.getResources().getString(R.string.new_marker), R.drawable.ic_action_place));
        _items_list.add(NavDrawerItemFactory.newInstanceOfSection(context.getResources().getString(R.string.terrein), R.drawable.ic_action_map));
        _items_list.add(NavDrawerItemFactory.newInstanceOfSection(context.getResources().getString(R.string.satelite), R.drawable.ic_action_web_site));
        _items_list.add(NavDrawerItemFactory.newInstanceOfSection(context.getResources().getString(R.string.filtr), R.drawable.ic_format_paint_grey600_18dp));
        _items_list.add(NavDrawerItemFactory.newInstanceOfSection(context.getResources().getString(R.string.categories), R.drawable.ic_action_view_as_list));
        _items_list.add(NavDrawerItemFactory.newInstanceOfSection(context.getResources().getString(R.string.setting), R.drawable.ic_action_settings));
        _items_list.add(NavDrawerItemFactory.newInstanceOfSection(context.getResources().getString(R.string.help), R.drawable.ic_action_help));

        this.addAll(_items_list);
    }

    /*
        Nadpisanie metody ArrayAdaptera w celu stworzenia róznych layoutów dla róznych typów elemtntów.
        @param1 pozycja elementu na liście
        @param2 widok jaki mamy na elemencie
        @param3 rodzic
        @return gotowy widok dla elementu
        Jeżeli element jeszcze nie posiada widoku następuje sprawdzenie jakiego jest typu i przypisanie mu właściwego widoku.
     */
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
        }

        return __convertView;
    }

    /*
        Metoda odpowiedzialna za ustawienie widoku dla obiektów typu TitleItem
        @params... Takie same jak dla metody GetView.
     */
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

    /*
        Metoda odpowiedzialna za ustawienie widoku dla obiektów typu Sectionitem
        @params... Takie same jak dla metody getView.
     */
    public View getSectionItem(int __position, View __convertVIew, ViewGroup __parent) {
        if(__convertVIew == null) {
            __convertVIew = inflater.inflate(R.layout.drawer_list_item, __parent, false);
            TextView _text_view = (TextView)__convertVIew.findViewById(R.id.drawer_text_view_list_item);
            ImageView _img_view = (ImageView)__convertVIew.findViewById(R.id.drawer_image_view_list_item);

            NavDrawerSectionItem _item = (NavDrawerSectionItem)this.getItem(__position);
            _text_view.setText(_item.getLabel());

            if(_item.hasIcon()) {
                _img_view.setImageResource(_item.getIconId());
            }

            return __convertVIew;
        }

        return __convertVIew;
    }
}

