package com.studing.bd.crashroads.tabs.map_tab;

import com.studing.bd.crashroads.database.remote_database.RemoteDatabaseAPI;
import com.studing.bd.crashroads.model.Response;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;

public class Model {

    interface OnSaveRouteCallback {
        void onRouteSave();
        void onError(String message);
    }

    private OnSaveRouteCallback callback;

    public Model(OnSaveRouteCallback callback){
        this.callback = callback;
    }
    
    public void saveRoute(Response response){
        RemoteDatabaseAPI.insert(response)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        callback.onRouteSave();
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e.getMessage());
                    }
                });
    }

}
