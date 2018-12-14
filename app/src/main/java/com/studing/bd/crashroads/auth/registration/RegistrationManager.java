package com.studing.bd.crashroads.auth.registration;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.studing.bd.crashroads.auth.registration.ui.IRegistrationActivity;
import com.studing.bd.crashroads.auth.registration.ui.RegistrationActivity;
import com.studing.bd.crashroads.model.User;
import com.studing.bd.crashroads.Utils;

import java.util.Objects;

public class RegistrationManager implements IRegistrationManager {

    private IRegistrationActivity registrationActivity;
    private RegistrationModel registrationModel;
    private FirebaseAuth mFireBaseAuth;


    private static final String SELECT_PICTURE_ACTION = "Select Picture";


    public RegistrationManager(RegistrationActivity registrationActivity){
        mFireBaseAuth = FirebaseAuth.getInstance();
        registrationModel = new RegistrationModel();
        this.registrationActivity = registrationActivity;
    }

    @Override
    public void emailSignUp() {
        String email = registrationActivity.getEmail();
        String password1 = registrationActivity.getPassword1();
        String password2 = registrationActivity.getPassword2();
        if(Utils.isEmailCorrect(email) && Utils.isPasswordCorrect(password1, password2)) {
            fireBaseSignUp(email, password1);
        }else{
            registrationActivity.showError("Input correct data");
        }
    }


    private void fireBaseSignUp(String email, String password) {
        mFireBaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            sendEmailVerification();
                            User user = createUser(mFireBaseAuth.getCurrentUser());
                            registrationModel.saveUserToLocalDatabase(user);
                            updateUI();
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(mFireBaseAuth.getCurrentUser()).sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
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
    }

    private User createUser(FirebaseUser currentUser) {
        return User.builder()
                .uid(currentUser.getUid())
                .email(registrationActivity.getEmail())
                .name(registrationActivity.getName())
                .surname(registrationActivity.getSurname())
                .birthdayDate(registrationActivity.getBirthDayDate())
                .drivingExperience(registrationActivity.getDrivingExperience())
                .country(Utils.getUserCountry(registrationActivity.getContext()))
                .imageByte(Utils.bitmapToArray(registrationActivity.getUserPhotoBitmap()))
                .build();
    }

    @Override
    public void loadProfilePicture() {
        openGallery();
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        registrationActivity.startNewActivityForResult(intent, SELECT_PICTURE_ACTION);
    }

    private void updateUI() {
        registrationActivity.stop();
    }

}
