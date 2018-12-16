package com.studing.bd.crashroads.database.remote_database.services;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.studing.bd.crashroads.ErrorHandler;
import com.studing.bd.crashroads.database.DatabaseMiddleware;
import com.studing.bd.crashroads.model.User;

public class SaveRemoteUserMiddleware extends DatabaseMiddleware {

    private DatabaseReference databaseReference;
    private User user;
    private final static String TAG = "login Error";


    public SaveRemoteUserMiddleware(ErrorHandler errorHandler) {
        super(errorHandler);
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
        Log.e(TAG, "saveUserToDatabase: " );
        databaseReference.child(userId).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    if(next != null)
                        next.checkNext(user);
                }
                else errorHandler.handleError(String.valueOf(task.getException()));
            }
        });
    }
}
