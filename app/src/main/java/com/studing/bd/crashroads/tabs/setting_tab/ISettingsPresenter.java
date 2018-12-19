package com.studing.bd.crashroads.tabs.setting_tab;

import android.text.Editable;

public interface ISettingsPresenter {
    void signOutUser();

    void changeUserPassword();
    void updatePassword(String password);

    boolean validatePasswords(String text, String text1);
}
