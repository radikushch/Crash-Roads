package com.studing.bd.crashroads.auth.registration;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.studing.bd.crashroads.R;
import com.studing.bd.crashroads.User;
import com.studing.bd.crashroads.auth.login.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class RegistrationActivity extends AppCompatActivity implements IRegistrationActivity {

    private IRegistrationManager registrationManager;
    @BindView(R.id.registration_email_input) EditText emailEditText;
    @BindView(R.id.registration_password_1_input) EditText password1EditText;
    @BindView(R.id.registration_password_2_input) EditText password2EditText;
    @BindView(R.id.registration_name_input) EditText nameEditText;
    @BindView(R.id.registration_male_rb) RadioButton maleRadioButton;
    @BindView(R.id.registration_female_rb) RadioButton femaleRadioButton;
    @BindView(R.id.registration_driving_exp_input) EditText drivingExpEditText;
    @BindView(R.id.registration_profile_photo) CircleImageView profilePhotoImageView;
    private static final int RC_SELECT_PICTURE = 0;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        registrationManager.detachRegistrationActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        registrationManager = new RegistrationManager();
        registrationManager.attachRegistrationActivity(this);
        initViews();
    }

    private void initViews() {
        Button signUpButton = findViewById(R.id.registration_sign_up);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrationManager.emailSignUp();
            }
        });
        profilePhotoImageView.setDrawingCacheEnabled(true);
        profilePhotoImageView.buildDrawingCache();
        FloatingActionButton addProfilePictureButton = findViewById(R.id.registration_add_picture);
        addProfilePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrationManager.loadProfilePicture();
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
    public boolean isMale() {
        return maleRadioButton.isChecked();
    }

    @Override
    public boolean isFemale() {
        return femaleRadioButton.isChecked();
    }

    @Override
    public int getDrivingExperience() {
        return Integer.parseInt(String.valueOf(drivingExpEditText.getText()));
    }

    @Override
    public void showError(String message) {
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
}
