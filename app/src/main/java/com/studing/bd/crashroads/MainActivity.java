package com.studing.bd.crashroads;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.studing.bd.crashroads.account_tab.AccountFragment;


public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private FirebaseAuth mFireBaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fragment fragment = new AccountFragment();
        getFragmentManager().beginTransaction().add(R.id.fragmentCont, fragment).commit();
    }

}
