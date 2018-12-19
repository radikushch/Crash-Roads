package com.studing.bd.crashroads.ui.settings_tab;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
}
