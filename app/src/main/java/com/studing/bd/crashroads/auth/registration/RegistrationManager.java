package com.studing.bd.crashroads.auth.registration;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;
import com.studing.bd.crashroads.MainActivity;
import com.studing.bd.crashroads.Utils;

import java.io.ByteArrayOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.internal.Util;

public class RegistrationManager implements IRegistrationManager, RegistrationModel.UriCallback {

    private IRegistrationActivity registrationActivity;
    private RegistrationModel registrationModel;
    private FirebaseAuth mFireBaseAuth;

    private static final String SELECT_PICTURE_ACTION = "Select Picture";


    public RegistrationManager(){
        mFireBaseAuth = FirebaseAuth.getInstance();
        registrationModel = new RegistrationModel();
    }

    @Override
    public void attachRegistrationActivity(IRegistrationActivity registrationActivity) {
        this.registrationActivity = registrationActivity;
    }

    @Override
    public void detachRegistrationActivity() {
        registrationActivity = null;
    }

    @Override
    public void emailSignUp() {
        String email = registrationActivity.getEmail();
        String password1 = registrationActivity.getPassword1();
        String password2 = registrationActivity.getPassword2();
        String name = registrationActivity.getName();
        if(Utils.isEmailCorrect(email) && Utils.isPasswordCorrect(password1, password2)) {
            createUser(email, password1, name);
        }else{
            registrationActivity.showError("Input correct data");
        }
    }

    private void createUser(String email, String password1, String name) {
        fireBaseSignUp(email, password1, name);
        byte[] imageData = getByteArrayFromImage(registrationActivity.getUserPhotoBitmap());
        registrationModel.uploadPhoto(imageData, name + Utils.getDateStamp(), this);
    }

    @Override
    public void apply(Uri uri) {
        String email = registrationActivity.getEmail();
        String password1 = registrationActivity.getPassword1();
        String name = registrationActivity.getName();
        addUserToDatabase(email, password1, name, uri);
    }

    private void addUserToDatabase(String email, String password1, String name, Uri uri) {
    }

    private void fireBaseSignUp(String email, String password, String name) {
        mFireBaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mFireBaseAuth.getCurrentUser();
                                updateUI(user);
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                         public void onFailure(@NonNull Exception e) {
                            registrationActivity.showError(e.getMessage());
                         }
                    });
    }

    private byte[] getByteArrayFromImage(Bitmap userPhotoBitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        userPhotoBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    @Override
    public void loadProfilePicture() {
        pickImage();
    }

    private void pickImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        registrationActivity.startNewActivityForResult(intent, SELECT_PICTURE_ACTION);
    }

    private void updateUI(FirebaseUser user) {
        if(user != null) {
            Intent intent = new Intent(registrationActivity.getContext(), MainActivity.class);
            registrationActivity.startNewActivity(intent);
        }
    }

   
}
