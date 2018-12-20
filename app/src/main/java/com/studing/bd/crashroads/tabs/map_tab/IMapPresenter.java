package com.studing.bd.crashroads.tabs.map_tab;

public interface IMapPresenter {

    void enableRouteMarking();

    void disableRouteMarking();

    void saveRoute(int rating);

    void removeAllPolylines();
}
