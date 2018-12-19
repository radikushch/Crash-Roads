package com.studing.bd.crashroads.tabs.setting_tab;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.studing.bd.crashroads.Utils;
import com.studing.bd.crashroads.database.remote_database.FirebaseInstant;
import com.studing.bd.crashroads.model.CurrentUser;
import com.studing.bd.crashroads.ui.settings_tab.ISettingsFragment;

public class SettingsPresenter implements ISettingsPresenter {

    private ISettingsFragment settingsFragment;

    public SettingsPresenter(ISettingsFragment settingsFragment) {
        this.settingsFragment = settingsFragment;
    }

    @Override
    public void signOutUser() {
        CurrentUser.delete();
        FirebaseInstant.auth().signOut();
        settingsFragment.stop();
    }

    @Override
    public void changeUserPassword() {
       settingsFragment.showDialog();
    }

    @Override
    public void updatePassword(String password) {
        FirebaseInstant.user().updatePassword(password)
                .addOnSuccessListener(aVoid -> settingsFragment.handleError("Password was updated"))
                .addOnFailureListener(e -> settingsFragment.handleError(e.getMessage()));
    }

    @Override
    public boolean validatePasswords(String text, String text1) {
        return Utils.isPasswordCorrect(text, text1);
    }
}
