package com.studing.bd.crashroads.auth.registration;

import android.content.Intent;
import android.os.Build;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.studing.bd.crashroads.database.local_database.LocalDatabaseAPI;
import com.studing.bd.crashroads.database.remote_database.FirebaseInstant;
import com.studing.bd.crashroads.ui.registration.IRegistrationActivity;
import com.studing.bd.crashroads.ui.registration.RegistrationActivity;
import com.studing.bd.crashroads.model.User;
import com.studing.bd.crashroads.Utils;

import java.util.Objects;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;

public class RegistrationManager implements IRegistrationManager, RegistrationModel.OnResponseCallback {

    private IRegistrationActivity registrationActivity;
    private RegistrationModel registrationModel;
    private FirebaseAuth mFireBaseAuth;

    private static final String SELECT_PICTURE_ACTION = "Select Picture";

    public RegistrationManager(RegistrationActivity registrationActivity){
        mFireBaseAuth = FirebaseAuth.getInstance();
        this.registrationActivity = registrationActivity;
        registrationModel = new RegistrationModel(this);
    }

    @Override
    public void emailSignUp() {
        String email = registrationActivity.getEmail();
        String password1 = registrationActivity.getPassword1();
        String password2 = registrationActivity.getPassword2();
        if(Utils.isEmailCorrect(email) && Utils.isPasswordCorrect(password1, password2)) {
            registrationActivity.showProgressBar();
            fireBaseSignUp(email, password1);
        }else{
            registrationActivity.handleError("Input correct data");
        }
    }

    private void fireBaseSignUp(final String email, final String password) {
        mFireBaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        sendEmailVerification();
                        User user = createUser(mFireBaseAuth.getCurrentUser());
                        registrationModel.saveUser(user);
                    }
                }).addOnFailureListener(e -> registrationActivity.handleError(e.getMessage()));
    }

    private void sendEmailVerification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(mFireBaseAuth.getCurrentUser()).sendEmailVerification().addOnCompleteListener(task -> {
                if(task.isSuccessful()) {
                    registrationActivity.handleError("Verify your email address");
                }
            }).addOnFailureListener(e -> registrationActivity.handleError(e.getMessage()));
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

    @Override
    public void onSave() {
        registrationActivity.hideProgressBar();
        updateUi();
    }

    @Override
    public void onFail(String message) {
        registrationActivity.hideProgressBar();
        registrationActivity.handleError(message);
    }

    private void updateUi() {
        FirebaseInstant.auth().signOut();
        registrationActivity.stop();
    }
}
