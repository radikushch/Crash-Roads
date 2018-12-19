package com.studing.bd.crashroads.ui;

import android.accounts.AccountManager;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.studing.bd.crashroads.R;
import com.studing.bd.crashroads.ui.account_tab.AccountFragment;
import com.studing.bd.crashroads.ui.map_tab.MapFragment;
import com.studing.bd.crashroads.ui.settings_tab.SettingsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity_debug";
    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        FragmentTransaction  transaction = getFragmentManager().beginTransaction();
        Fragment fragment = new AccountFragment();
        transaction.add(R.id.fragmentCont, fragment).commit();
        navigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.navigation_profile:
                    switchToProfileFragment();
                    return true;
                case R.id.navigation_settings:
                    switchToSettingsFragment();
                    return true;
                case R.id.navigation_map:
                    switchToMapFragment();
                    return true;
            }
            return false;
        });
    }

    public void switchToProfileFragment() {
        FragmentTransaction  transaction = getFragmentManager().beginTransaction();
        Fragment fragment = new AccountFragment();
        transaction.replace(R.id.fragmentCont, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void switchToSettingsFragment() {
        FragmentTransaction  transaction = getFragmentManager().beginTransaction();
        Fragment fragment = new SettingsFragment();
        transaction.replace(R.id.fragmentCont, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void switchToMapFragment() {
        FragmentTransaction  transaction = getFragmentManager().beginTransaction();
        Fragment fragment = new MapFragment();
        transaction.replace(R.id.fragmentCont, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
