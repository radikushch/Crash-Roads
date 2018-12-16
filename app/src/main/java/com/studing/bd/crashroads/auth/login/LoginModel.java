package com.studing.bd.crashroads.auth.login;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.studing.bd.crashroads.ErrorHandler;
import com.studing.bd.crashroads.database.local_database.services.DeleteLocalUserService;
import com.studing.bd.crashroads.database.local_database.services.QueryLocalUserService;
import com.studing.bd.crashroads.database.DatabaseMiddleware;
import com.studing.bd.crashroads.database.remote_database.FirebaseInstant;
import com.studing.bd.crashroads.database.remote_database.services.UploadPhotoMiddleware;
import com.studing.bd.crashroads.database.remote_database.services.DownloadPhotoUriMiddleware;
import com.studing.bd.crashroads.database.remote_database.services.SaveRemoteUserMiddleware;
import com.studing.bd.crashroads.model.User;

public class LoginModel {

    public void createUserWithEmail(final ErrorHandler loginErrorNotificator) {
        DatabaseReference ref = FirebaseInstant.userReference()
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() == null) {
                    DatabaseMiddleware chain = buildChain(loginErrorNotificator);
                    chain.checkNext(null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loginErrorNotificator.handleError(databaseError.getMessage());
            }
        });
    }

    private DatabaseMiddleware buildChain(ErrorHandler errorHandler) {
        DatabaseMiddleware root = new QueryLocalUserService(errorHandler, null);
        DatabaseMiddleware upload = new UploadPhotoMiddleware(errorHandler);
        DatabaseMiddleware download = new DownloadPhotoUriMiddleware(errorHandler);
        DatabaseMiddleware save = new SaveRemoteUserMiddleware(errorHandler);
        DatabaseMiddleware delete = new DeleteLocalUserService(errorHandler);
        root.linkWith(upload);
        upload.linkWith(download);
        download.linkWith(save);
        save.linkWith(delete);
        return root;
    }

    public void createUserWithGoogle(final User user, final ErrorHandler loginErrorNotificator) {
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference()
                .child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() == null) {
                    new SaveRemoteUserMiddleware(loginErrorNotificator).checkNext(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loginErrorNotificator.handleError(databaseError.getMessage());
            }
        });
    }
}
