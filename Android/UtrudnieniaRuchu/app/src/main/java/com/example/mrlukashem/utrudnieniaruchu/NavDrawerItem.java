package com.example.mrlukashem.utrudnieniaruchu;

/**
 * Created by mrlukashem on 19.03.15.
 */

/*
    interfejs elementu listy w navDrawerze
 */
public interface NavDrawerItem {
    public int getId();
    public String getLabel();
    public int getType();
    public int getIconId();
    public boolean hasIcon();
}
