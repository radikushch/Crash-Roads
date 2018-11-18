package com.studing.bd.crashroads.auth.registration;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.studing.bd.crashroads.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegistrationActivity extends AppCompatActivity implements IRegistrationActivity {

    private IRegistrationManager registrationManager;
    private EditText emailEditText, password1EditText, password2EditText, nameEditText;
    private CircleImageView profilePhotoImageView;
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
        registrationManager = new RegistrationManager();
        registrationManager.attachRegistrationActivity(this);
        initViews();
    }

    private void initViews() {
        emailEditText = findViewById(R.id.registration_email_input);
        password1EditText = findViewById(R.id.registration_password_1_input);
        password2EditText = findViewById(R.id.registration_password_2_input);
        nameEditText = findViewById(R.id.registration_name_input);
        Button signUpButton = findViewById(R.id.registration_sign_up);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrationManager.emailSignUp();
            }
        });

        profilePhotoImageView = findViewById(R.id.registration_profile_photo);
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
}
