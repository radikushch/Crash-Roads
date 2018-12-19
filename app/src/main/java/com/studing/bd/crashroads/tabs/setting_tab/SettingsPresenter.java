package com.studing.bd.crashroads.tabs.setting_tab;

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

    }
}
