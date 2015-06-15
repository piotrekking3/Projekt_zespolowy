package com.example.mrlukashem.utrudnieniaruchu;

import android.app.ActionBar;
import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by mrlukashem on 09.04.15.
 */
public class PreferenceScreen extends PreferenceFragment {

    private ActionBar actionBar;

    @Override
    public void onCreate(Bundle __savedInstanceState) {
        super.onCreate(__savedInstanceState);
        addPreferencesFromResource(R.xml.preference_screen);
    }

    private void setActionBar() {
        actionBar = getActivity().getActionBar();
    }
}
