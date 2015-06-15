package com.example.mrlukashem.utrudnieniaruchu;

import android.os.Bundle;
import android.app.Fragment;
import android.view.*;
import android.widget.TextView;
import com.google.android.gms.maps.MapFragment;

public class HelpFragment extends Fragment {

    private MapFragment mapFragment;

    public static HelpFragment newInstance() {
        HelpFragment _fragment = new HelpFragment();

        return _fragment;
    }

    public HelpFragment() {
    }

    public HelpFragment setMapFragment(MapFragment __map_fragment) {
        mapFragment = __map_fragment;

        return this;
    }

    @Override
    public void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater _inflater, ViewGroup _container,
                             Bundle _savedInstanceState) {
        View _result = _inflater.inflate(R.layout.fragment_help, _container, false);

        TextView _content = (TextView)_result.findViewById(R.id.HelpFragmentContent);
        _content.setText(getResources().getString(R.string.aboutProject));

        return _result;
    }
}
