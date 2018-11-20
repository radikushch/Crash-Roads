package com.studing.bd.crashroads.auth.registration;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.studing.bd.crashroads.MainActivity;
import com.studing.bd.crashroads.User;
import com.studing.bd.crashroads.Utils;

import java.io.ByteArrayOutputStream;

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
        if(Utils.isEmailCorrect(email) && Utils.isPasswordCorrect(password1, password2)) {
            createUser(email, password1);
        }else{
            registrationActivity.showError("Input correct data");
        }
    }

    private void createUser(String email, String password1) {
        fireBaseSignUp(email, password1);
    }

    private void fireBaseSignUp(String email, String password) {
        mFireBaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            onAuthSuccess();
                            sendEmailVerification();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        registrationActivity.showError(e.getMessage());
                    }
                });
    }

    private void sendEmailVerification() {
        mFireBaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    registrationActivity.showError("Verify your email address");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                registrationActivity.showError(e.getMessage());
            }
        });
    }

    private void onAuthSuccess() {
        Bitmap photoBitmap = registrationActivity.getUserPhotoBitmap();
        byte[] data = getByteArrayFromImage(photoBitmap);
        registrationModel.uploadPhoto(data, registrationActivity.getName() + Utils.getDateStamp(), this);
    }

    private byte[] getByteArrayFromImage(Bitmap userPhotoBitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        userPhotoBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    @Override
    public void handleUriCallback(Uri uri) {
        FirebaseUser firebaseUser = mFireBaseAuth.getCurrentUser();
        User user = new User();
        user.setUsername(registrationActivity.getName());
        user.setEmail(firebaseUser.getEmail());
        user.setImage(String.valueOf(uri));
        String userId = firebaseUser.getUid();
        saveUserToDatabase(user, userId);
        updateUI(firebaseUser);
    }

    private void saveUserToDatabase(User user, String userId) {
        registrationModel.addNewUser(user, userId);
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
        registrationActivity.stop();
    }

}
