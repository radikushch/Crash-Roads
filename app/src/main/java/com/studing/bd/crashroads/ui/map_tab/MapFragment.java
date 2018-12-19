package com.studing.bd.crashroads.ui.map_tab;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.studing.bd.crashroads.R;
import com.studing.bd.crashroads.tabs.map_tab.MapFragPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment {
    private MapFragPresenter presenter;

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        Button addDmmyDataBtn = (Button) view.findViewById(R.id.add_dummy_data_btn);
        addDmmyDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.save();
                Log.d("flag", "mapFRG");
            }
        });
        presenter = new MapFragPresenter(this);
        // Inflate the layout for this fragment
        return view;
    }

    public void handleError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
