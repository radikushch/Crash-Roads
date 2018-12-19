package com.studing.bd.crashroads.ui.settings_tab;

public interface ISettingsFragment {
    void stop();

    void handleError(String message);

    void showDialog();
}
