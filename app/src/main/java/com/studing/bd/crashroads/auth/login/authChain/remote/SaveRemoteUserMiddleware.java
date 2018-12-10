package com.studing.bd.crashroads.auth.login.authChain.remote;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.studing.bd.crashroads.auth.login.authChain.LoginMiddleware;
import com.studing.bd.crashroads.model.User;

public class SaveRemoteUserMiddleware extends LoginMiddleware {

    private DatabaseReference databaseReference;
    private User user;

    public SaveRemoteUserMiddleware(LoginErrorNotificator loginErrorNotificator) {
        super(loginErrorNotificator);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("users");
    }

    @Override
    public void execute(User user) {
        this.user = user;
        saveUserToDatabase();
    }

    @Override
    public boolean canExecute(User user) {
        return user != null;
    }

    private void saveUserToDatabase() {
        String userId = user.uid;
        databaseReference.child(userId).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    if(next != null)
                        next.checkNext(user);
                }
                else loginErrorNotificator.handleChainError(String.valueOf(task.getException()));
            }
        });
    }
}
