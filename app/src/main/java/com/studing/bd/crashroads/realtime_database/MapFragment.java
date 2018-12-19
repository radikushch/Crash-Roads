package com.studing.bd.crashroads.realtime_database;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.studing.bd.crashroads.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment{

    static final LatLng HAMBURG = new LatLng(53.558, 9.927);
    private MapFragPresenter presenter;
    private MapView mapView;
    private Location currentUserLocation;

    public Button getDoneBtn() {
        return doneBtn;
    }

    private Button doneBtn;

    public Button getCancelBtn() {
        return cancelBtn;
    }

    private Button cancelBtn;


    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        presenter = new MapFragPresenter(MapFragment.this);
        presenter.init();
        FloatingActionButton fab = view.findViewById(R.id.add_responce_fab);
        doneBtn = view.findViewById(R.id.action_done);
        cancelBtn = view.findViewById(R.id.action_cancel);
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.buttonHandle(v);
            }
        };
        fab.setOnClickListener(clickListener);
        doneBtn.setOnClickListener(clickListener);
        cancelBtn.setOnClickListener(clickListener);
        //setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.initMap();
    }


}
