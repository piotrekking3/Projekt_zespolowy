package com.example.mrlukashem.utrudnieniaruchu;

/**
 * Created by mrlukashem on 19.03.15.
 */

/*
    Klasa obrazująca element listy, który będzie tytułowym.
 */
public class NavDrawerTitleItem implements NavDrawerItem {

    //etykieta
    private String label;
    //id konkretnego elementu listy
    private int id;
    //id obrazka
    private int icon;
    //czy ma zaladowany obrazek
    private boolean hasIconField;

    public static final int TYPE = 0;


    /*
        Konstruktor ustawijaący tylko etykietę. Następnie przypisujący wartość false
        dla pola odpowiedzialnego za trzyanei wiadomości - czy element posiada grafikę.
        @param1 Ciąg znaków wyświetlany w TextView
     */
    public NavDrawerTitleItem(String __label) {
        label = __label;
        id = 0;
    }

    /*
      Konstruktor ustawiający etykietę oraz id grafiki. Następnie przypisujący wartość
      false dla pola odpowiedzialnego za trzymanie wiadomości - czy element posiada grafikę.
      @param1 Ciąg znaków wyświetlany w TextView
      @param2 Id grafiki
   */
    public NavDrawerTitleItem(String __label, int __icon) {
        label = __label;
        icon = __icon;
        hasIconField = true;
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

    @Override
    public int getType() {
        return TYPE;
    }

    @Override
    public int getIconId() {
        return icon;
    }

    @Override
    public boolean hasIcon() {
        return hasIconField;
    }

    public void setIconId(int __icon) {
        id = __icon;
    }
}
