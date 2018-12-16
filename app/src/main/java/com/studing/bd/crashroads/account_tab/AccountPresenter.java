package com.studing.bd.crashroads.account_tab;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.studing.bd.crashroads.Utils;
import com.studing.bd.crashroads.ui.account_tab.IAccountFragment;
import com.studing.bd.crashroads.database.remote_database.services.SaveRemoteUserMiddleware;
import com.studing.bd.crashroads.model.User;

public class AccountPresenter implements IAccountPresenter{

    private IAccountFragment accountFragment;
    private User currentUser;
    private DatabaseReference databaseReference;

    public AccountPresenter(IAccountFragment accountFragment) {
        this.accountFragment = accountFragment;
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
    }

    @Override
    public void loadUserData() {
        loadFromFirebase();
    }

    @Override
    public void setBirthDate(String date) {
        currentUser.birthdayDate = date;
    }

    private void loadFromFirebase() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentUser = dataSnapshot.getValue(User.class);
                accountFragment.setUserInfo(currentUser);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void updateUserData() {
        currentUser.name = accountFragment.getName();
        currentUser.imageByte = Utils.bitmapToArray(accountFragment.getUserPhotoBitmap());
        currentUser.country = accountFragment.getLocation();
        currentUser.drivingExperience = accountFragment.getExp();
        (new SaveRemoteUserMiddleware(accountFragment)).checkNext(currentUser);
    }
}
