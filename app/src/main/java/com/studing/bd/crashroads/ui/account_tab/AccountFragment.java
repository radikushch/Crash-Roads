package com.studing.bd.crashroads.ui.account_tab;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.studing.bd.crashroads.ErrorHandler;
import com.studing.bd.crashroads.R;
import com.studing.bd.crashroads.Utils;
import com.studing.bd.crashroads.account_tab.AccountPresenter;
import com.studing.bd.crashroads.account_tab.IAccountPresenter;
import com.studing.bd.crashroads.model.User;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class AccountFragment extends Fragment implements IAccountFragment,
        DatePickerDialog.OnDateSetListener, ErrorHandler {

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
    private Drawable originalBackground;
    private Drawable underlineTitleBackground;
    private Drawable underlineTextBackground;
    private IAccountPresenter accountPresenter;
    private DatePickerDialog datePicker;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_account, null);
        ButterKnife.bind(this, v);
        originalBackground = (new EditText(getActivity())).getBackground();
        underlineTextBackground = emailEditText.getBackground();
        underlineTitleBackground = nameEditText.getBackground();
        accountPresenter = new AccountPresenter(this);
        accountPresenter.loadUserData();
        initDatePicker();
        return v;
    }

    private void initDatePicker() {
        Calendar calendar = Calendar.getInstance();
        datePicker = new DatePickerDialog(getActivity(),
                this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        ageEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    EditText edit = (EditText) v;
                    int len = edit.getText().toString().length();
                    if (len == 0) {
                        datePicker.show();
                    }
                    else {
                        edit.setSelection(0, len);
                    }
                }
            }
        });
    }


    @OnClick(R.id.profile_edit)
    public void edit() {
        setViewVisible();
        setViewEnabled();
        setOriginalDrawable();
    }

    @OnClick(R.id.profile_edit_cancel)
    public void cancel() {
        setViewInvisible();
        setEditDrawable();
        setViewUnenabled();
    }

    @OnClick(R.id.profile_edit_save)
    public void save() {
        setViewInvisible();
        setEditDrawable();
        setViewUnenabled();
    }

    private void setViewVisible() {
        editButton.setVisibility(View.INVISIBLE);
        saveButton.setVisibility(View.VISIBLE);
        cancelButton.setVisibility(View.VISIBLE);
        photoEditImageView.setVisibility(View.VISIBLE);
    }

    private void setViewInvisible() {
        editButton.setVisibility(View.VISIBLE);
        saveButton.setVisibility(View.INVISIBLE);
        cancelButton.setVisibility(View.INVISIBLE);
        photoEditImageView.setVisibility(View.INVISIBLE);
    }

    private void setViewEnabled() {
        nameEditText.setEnabled(true);
        ageEditText.setEnabled(true);
        locationEditText.setEnabled(true);
        expEditText.setEnabled(true);
    }

    private void setViewUnenabled() {
        nameEditText.setEnabled(false);
        ageEditText.setEnabled(false);
        locationEditText.setEnabled(false);
        expEditText.setEnabled(false);
    }

    private void setOriginalDrawable() {
        nameEditText.setBackground(originalBackground);
        ageEditText.setBackground(originalBackground);
        locationEditText.setBackground(originalBackground);
        expEditText.setBackground(originalBackground);
    }

    private void setEditDrawable() {
        nameEditText.setBackground(underlineTitleBackground);
        ageEditText.setBackground(underlineTitleBackground);
        locationEditText.setBackground(underlineTextBackground);
        expEditText.setBackground(underlineTextBackground);
    }

    @Override
    public void setUserInfo(User user) {
        String name = user.name + " " + user.surname + ",";
        nameEditText.setText(name);
        ageEditText.setText(Utils.getAge(user.birthdayDate));
        emailEditText.setText(user.email);
        locationEditText.setText(user.country);
        expEditText.setText(user.drivingExperience);
    }

    @Override
    public String getName() {
        return String.valueOf(nameEditText.getText());
    }

    @Override
    public String getAge() {
        return null;
    }

    @Override
    public String getEmail() {
        return String.valueOf(emailEditText.getText());
    }

    @Override
    public String getLocation() {
        return String.valueOf(locationEditText.getText());
    }

    @Override
    public String getExp() {
        return String.valueOf(expEditText.getText());
    }

    @Override
    public Bitmap getUserPhotoBitmap() {
        return ((BitmapDrawable) photoImageView.getDrawable()).getBitmap();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String cheapDate = (month + 1) + "/" + dayOfMonth + "/" + year;
        accountPresenter.setBirthDate(cheapDate);
        ageEditText.setText(Utils.getAge(cheapDate));
    }

    @Override
    public void handleError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
