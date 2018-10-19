package com.studing.bd.crashroads.realtime_database;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.studing.bd.crashroads.R;

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
        presenter = new MapFragPresenter();
        // Inflate the layout for this fragment
        return view;
    }
}
