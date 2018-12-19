package com.studing.bd.crashroads.ui.map_tab;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.studing.bd.crashroads.R;
import com.studing.bd.crashroads.model.Route;
import com.studing.bd.crashroads.tabs.map_tab.MapPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements IMapFragment {

    @BindView(R.id.action_done) Button saveRouteButton;
    @BindView(R.id.action_cancel) Button cancelRouteButton;
    @BindView(R.id.map) MapView mapView;

    private MapPresenter mapPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_map, container, false);
        ButterKnife.bind(this, view);
        mapPresenter = new MapPresenter(this);
        return view;
    }

    @OnClick(R.id.add_route_fab)
    public void addRouteButtonClicked() {
        saveRouteButton.setVisibility(View.VISIBLE);
        cancelRouteButton.setVisibility(View.VISIBLE);
        mapPresenter.enableRouteMarking();
    }

    @OnClick(R.id.action_done)
    public void saveRouteButtonClicked() {
        saveRouteButton.setVisibility(View.GONE);
        cancelRouteButton.setVisibility(View.GONE);
        mapPresenter.disableRouteMarking();
    }

    @Override
    public void handleError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public MapView getMapView() {
        return this.mapView;
    }

    @Override
    public Context context() {
        return getActivity();
    }

    @Override
    public void createRateDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.rating_dialog, null);
        dialogBuilder.setView(dialogView);
        final RatingBar ratingBar = dialogView.findViewById(R.id.rating);
        ratingBar.setRating(3);
        dialogBuilder.setPositiveButton("Save", (dialog, which) -> {
            mapPresenter.saveRoute((int) ratingBar.getRating());
            dialog.dismiss();
        });
        dialogBuilder.setTitle("Rate road");
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }

}