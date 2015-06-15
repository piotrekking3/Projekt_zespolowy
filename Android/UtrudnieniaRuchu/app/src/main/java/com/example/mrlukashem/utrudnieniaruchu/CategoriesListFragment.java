package com.example.mrlukashem.utrudnieniaruchu;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

public class CategoriesListFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter<String> adapter;

    public static CategoriesListFragment newInstance() {
        CategoriesListFragment _fragment = new CategoriesListFragment();
        return _fragment;
    }

    @Override
    public void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);

        adapter = new ArrayAdapter<>(
                getActivity(),
                R.layout.categories_list_item,
                R.id.list_item,
                getResources().getStringArray(R.array.categories)
        );
    }

    @Override
    public View onCreateView(LayoutInflater _inflater, ViewGroup _container,
                             Bundle _savedInstanceState) {
        View view = _inflater.inflate(R.layout.fragment_item_list, _container, false);

        listView = (ListView) view.findViewById(android.R.id.list);
        listView.setAdapter(adapter);

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem __item) {
        int id = __item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(__item);
    }
}

