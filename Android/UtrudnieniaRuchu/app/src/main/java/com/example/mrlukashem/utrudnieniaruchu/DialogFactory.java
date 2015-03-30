package com.example.mrlukashem.utrudnieniaruchu;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;

/**
 * Created by mrlukashem on 15.03.15.
 */

/*
    Klasa na bazie Faktory. Tworzy gotowe okienka dialogowe.
    Np. Activity w ogole nie interesuje sposob tworzenia, tylko otrzymuje
    referencje do Dialog.
    TODO: Czy ta klasa jest zbędna?...
 */
public class DialogFactory{


    public static enum DIALOG_TYPE {
        //okienko pojawiajace sie po nacisnieciu znacznika na mapie
        MARKER_CONTENT_DIALOG,
        //okienko z lista wszystkich kategorii
        CATEGORIES_DIALOG,
        //okienko z checklista kategorii
        CAT_CHECK_LIST_DIALOG
    }

    public static Dialog newInstance(DIALOG_TYPE __dialog_type, Context __con) {

        switch(__dialog_type) {
            case MARKER_CONTENT_DIALOG:
                AlertDialog.Builder _builder = new AlertDialog.Builder(__con);
                _builder.setMessage("testowy wpis sialalalal!");

                return _builder.create();
            case CATEGORIES_DIALOG:
                AlertDialog.Builder _builder_2 = new AlertDialog.Builder(__con);
                _builder_2.setMessage("testowy wpis catiegories!");

                return _builder_2.create();
            default:
                return null;
        }
    }
}
