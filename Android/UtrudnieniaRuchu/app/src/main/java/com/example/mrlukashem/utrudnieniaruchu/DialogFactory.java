package com.example.mrlukashem.utrudnieniaruchu;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;

/**
 * Created by mrlukashem on 15.03.15.
 */
public class DialogFactory{

    public static enum DIALOG_TYPE {
        MARKER_CONTENT_DIALOG, CATEGORIES_DIALOG
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
