package com.example.mrlukashem.utrudnieniaruchu;

import android.provider.ContactsContract;

/**
 * Created by mrlukashem on 29.03.15.
 */

/*
    Interfejs służacy aktywności do ustawienia niezbędnych elementów w celu
    dodania nowego markera na mapie.
    Poprzez zawężenie aktywności do poniższego interfejsu będzie korzystał
    formularz służący do pobrania danych od użytkownika.
 */
public interface NewMarkerOnMap {
    void enableLongClickListener();
    void showToastMarkerInfo();
    void createMarkerFromFormData(boolean __is_from_current_position, int __category_id, String __content);
}
