package com.studing.bd.crashroads.account_tab;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.studing.bd.crashroads.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class AccountFragment extends Fragment {

    @BindView(R.id.profile_photo) CircleImageView photoImageView;
    @BindView(R.id.profile_photo_edit) FloatingActionButton photoEditImageView;
    @BindView(R.id.profile_name) EditText nameEditText;
    @BindView(R.id.profile_age) EditText ageEditText;
    @BindView(R.id.profile_email) EditText emailEditText;
    @BindView(R.id.profile_location) EditText locationEditText;
    @BindView(R.id.profile_exp) EditText expEditText;
    @BindView(R.id.profile_edit) Button editButton;
    @BindView(R.id.profile_edit_save) Button saveButton;
    @BindView(R.id.profile_edit_cancel) Button cancelButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_account, null);
        ButterKnife.bind(this, v);
        return v;
    }

    @OnClick(R.id.profile_edit)
    public void setEditButton() {
        editButton.setVisibility(View.INVISIBLE);
        saveButton.setVisibility(View.VISIBLE);
        cancelButton.setVisibility(View.VISIBLE);
        photoEditImageView.setVisibility(View.VISIBLE);
        nameEditText.getBackground().setBounds(0,0,0,1);
        ageEditText.getBackground().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
        emailEditText.getBackground().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
        locationEditText.getBackground().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
        expEditText.getBackground().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
    }
}
