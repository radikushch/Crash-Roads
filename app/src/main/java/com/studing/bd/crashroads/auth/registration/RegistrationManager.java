package com.studing.bd.crashroads.auth.registration;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.studing.bd.crashroads.MainActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationManager implements IRegistrationManager {

    private IRegistrationActivity registrationActivity;
    private FirebaseAuth mFireBaseAuth;


    public RegistrationManager(){
        mFireBaseAuth = FirebaseAuth.getInstance();
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
        if(userInfoIsCorrect(email, password1, password2)) {
            fireBaseSignUp(email, password1, name);
        }else{
            registrationActivity.showError("Input correct data");
        }
    }

    private boolean userInfoIsCorrect(String email, String password1, String password2) {
        Pattern validEmailAddressRegex =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = validEmailAddressRegex.matcher(email);
        return password1.equals(password2) && matcher.matches();
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

    private void updateUI(FirebaseUser user) {
        if(user != null) {
            Intent intent = new Intent(registrationActivity.getContext(), MainActivity.class);
            registrationActivity.startNewActivity(intent);
        }
    }
}