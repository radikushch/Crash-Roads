package com.studing.bd.crashroads.ui.settings_tab;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.studing.bd.crashroads.R;
import com.studing.bd.crashroads.tabs.setting_tab.ISettingsPresenter;
import com.studing.bd.crashroads.tabs.setting_tab.SettingsPresenter;
import com.studing.bd.crashroads.ui.login.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsFragment extends Fragment implements ISettingsFragment{

    @BindView(R.id.settings_sign_out_button)
    Button signOutButton;

    private ISettingsPresenter settingsPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, null);
        ButterKnife.bind(this, v);
        settingsPresenter = new SettingsPresenter(this);
        return v;
    }

    @OnClick(R.id.settings_sign_out_button)
    public void signOutButtonClicked() {
        settingsPresenter.signOutUser();
    }

    @OnClick(R.id.settings_change_password_button)
    public void changePasswordButtonClicked() {
        settingsPresenter.changeUserPassword();
    }

    @Override
    public void stop() {
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }

    @Override
    public void handleError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_new_password, null);
        dialogBuilder.setView(dialogView);
        final EditText passw1 = dialogView.findViewById(R.id.dialog_password1);
        final EditText passw2 = dialogView.findViewById(R.id.dialog_password2);
        dialogBuilder.setTitle("Enter your password");
        dialogBuilder.setPositiveButton("Ok", (dialog, which) -> {
            if(settingsPresenter.validatePasswords(String.valueOf(passw1.getText()), String.valueOf(passw2.getText()))) {
                settingsPresenter.updatePassword(String.valueOf(passw1.getText()));
            }else {
                handleError("Password are different");
            }
            dialog.dismiss();
        });
        dialogBuilder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = dialogBuilder.create();
        dialog.show();

    }
}
