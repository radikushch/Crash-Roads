package com.studing.bd.crashroads;

import android.accounts.AccountManager;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.studing.bd.crashroads.auth.login.LoginManager;
import com.studing.bd.crashroads.ui.account_tab.AccountFragment;
import com.studing.bd.crashroads.model.User;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity_debug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fragment fragment = new AccountFragment();
        getFragmentManager().beginTransaction().add(R.id.fragmentCont, fragment).commit();
    }

}
