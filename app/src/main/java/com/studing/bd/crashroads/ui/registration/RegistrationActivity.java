package com.studing.bd.crashroads.ui.registration;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.studing.bd.crashroads.auth.registration.IRegistrationManager;
import com.studing.bd.crashroads.auth.registration.RegistrationManager;
import com.studing.bd.crashroads.R;
import com.studing.bd.crashroads.ui.login.LoginActivity;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class RegistrationActivity extends AppCompatActivity implements IRegistrationActivity,
        DatePickerDialog.OnDateSetListener {

    private IRegistrationManager registrationManager;
    @BindView(R.id.registration_email_input) EditText emailEditText;
    @BindView(R.id.registration_password_1_input) EditText password1EditText;
    @BindView(R.id.registration_password_2_input) EditText password2EditText;
    @BindView(R.id.registration_name_input) EditText nameEditText;
    @BindView(R.id.registration_surname_input) EditText surnameEditText;
    @BindView(R.id.registration_driving_exp_input) EditText drivingExpEditText;
    @BindView(R.id.registration_date_input) EditText dateEditText;
    @BindView(R.id.registration_profile_photo) CircleImageView profilePhotoImageView;
    @BindView(R.id.registration_loading) ProgressBar loadProgressBar;
    @BindView(R.id.registration_container) ScrollView layoutContainer;
    private DatePickerDialog datePicker;
    private static final int RC_SELECT_PICTURE = 0;

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        registrationManager = new RegistrationManager(this);
        initViews();

    }

    private void initViews() {
        Button signUpButton = findViewById(R.id.registration_sign_up);
        signUpButton.setOnClickListener(v -> registrationManager.emailSignUp());
        profilePhotoImageView.setDrawingCacheEnabled(true);
        profilePhotoImageView.buildDrawingCache();
        FloatingActionButton addProfilePictureButton = findViewById(R.id.registration_add_picture);
        addProfilePictureButton.setOnClickListener(v -> registrationManager.loadProfilePicture());
        initDatePicker();
    }

    private void initDatePicker() {
        Calendar calendar = Calendar.getInstance();
        datePicker = new DatePickerDialog(this,
                this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dateEditText.setOnFocusChangeListener((v, hasFocus) -> {
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
        });
    }

    @Override
    public String getEmail() {
        return String.valueOf(emailEditText.getText());
    }

    @Override
    public String getPassword1() {
        return String.valueOf(password1EditText.getText());
    }

    @Override
    public String getPassword2() {
        return String.valueOf(password2EditText.getText());
    }

    @Override
    public String getName() {
        return String.valueOf(nameEditText.getText());
    }

    @Override
    public String getSurname() {
        return String.valueOf(surnameEditText.getText());
    }

    @Override
    public String getDrivingExperience() {
        return String.valueOf(drivingExpEditText.getText());
    }

    @Override
    public String getBirthDayDate() {
        return String.valueOf(dateEditText.getText());
    }

    @Override
    public void showProgressBar() {
        layoutContainer.setVisibility(View.GONE);
        loadProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        layoutContainer.setVisibility(View.VISIBLE);
        loadProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void handleError(String message) {
        layoutContainer.setVisibility(View.VISIBLE);
        loadProgressBar.setVisibility(View.GONE);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void startNewActivity(Intent intent) {
        startActivity(intent);
        finish();
    }

    @Override
    public void startNewActivityForResult(Intent intent, String action) {
        startActivityForResult(Intent.createChooser(intent, action), RC_SELECT_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            if(requestCode == RC_SELECT_PICTURE) {
                profilePhotoImageView.setImageURI(data.getData());
            }
        }
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public Bitmap getUserPhotoBitmap() {
        return ((BitmapDrawable) profilePhotoImageView.getDrawable()).getBitmap();
    }

    @Override
    public void stop() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String cheapDate = (month + 1) + "/" + dayOfMonth + "/" + year;
        dateEditText.setText(cheapDate);
    }


}
