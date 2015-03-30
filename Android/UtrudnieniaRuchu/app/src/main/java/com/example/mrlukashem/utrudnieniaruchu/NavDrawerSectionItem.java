package com.example.mrlukashem.utrudnieniaruchu;

import android.content.Context;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by mrlukashem on 19.03.15.
 */

/*
    Klasa obrazująca element listy, który będzie nie tytułowym. Tzn będzie elementem listy
    pod zadanym tytułem.
 */
public class NavDrawerSectionItem implements NavDrawerItem {

    private int id;
    private int icon;
    private String label;
    private boolean hasIconField;

    public static final int TYPE = 1;

    /*
        Konstruktor ustawiający etykietę oraz id grafiki. Następnie przypisujący wartość
        false dla pola odpowiedzialnego za trzymanie wiadomości - czy element posiada grafikę.
        @param1 Ciąg znaków wyświetlany w TextView
        @param2 Id grafiki
     */
    public NavDrawerSectionItem(String __label, int __icon_id) {
        label = __label;
        icon = __icon_id;
        hasIconField = true;
    }


    /*
        Konstruktor ustawijaący tylko etykietę. Następnie przypisujący wartość false
        dla pola odpowiedzialnego za trzyanei wiadomości - czy element posiada grafikę.
        @param1 Ciąg znaków wyświetlany w TextView
     */
    public NavDrawerSectionItem(String __label) {
        label = __label;
        hasIconField = false;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getLabel() {
        return new String(label);
    }

    public void setLabel(String __label) {
        label = __label;
    }

    public void setId(int __id) {
        id = __id;
    }

    public int getIconId() {
        return icon;
    }

    @Override
    public boolean hasIcon() {
        return hasIconField;
    }

    public void setIconId(int __id) {
        icon = __id;
    }

    @Override
    public int getType() {
        return TYPE;
    }

}
