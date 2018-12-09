package com.studing.bd.crashroads.auth.login;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.studing.bd.crashroads.auth.login.authChain.local.DeleteLocalUserMiddleware;
import com.studing.bd.crashroads.auth.login.authChain.local.LoadLocalUserMiddleware;
import com.studing.bd.crashroads.auth.login.authChain.LoginMiddleware;
import com.studing.bd.crashroads.auth.login.authChain.image.UploadPhotoMiddleware;
import com.studing.bd.crashroads.auth.login.authChain.image.DownloadPhotoUriMiddleware;
import com.studing.bd.crashroads.auth.login.authChain.remote.SaveRemoteUserMiddleware;

public class LoginModel {

    public void createUserWithEmail(final LoginMiddleware.LoginErrorNotificator loginErrorNotificator) {

        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference()
                .child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() == null) {
                    LoginMiddleware chain = buildChain(loginErrorNotificator);
                    chain.checkNext(null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loginErrorNotificator.handleChainError(databaseError.getMessage());
            }
        });
    }

    private LoginMiddleware buildChain(LoginMiddleware.LoginErrorNotificator loginErrorNotificator) {
        LoginMiddleware root = new LoadLocalUserMiddleware(loginErrorNotificator);
        LoginMiddleware upload = new UploadPhotoMiddleware(loginErrorNotificator);
        LoginMiddleware download = new DownloadPhotoUriMiddleware(loginErrorNotificator);
        LoginMiddleware save = new SaveRemoteUserMiddleware(loginErrorNotificator);
        LoginMiddleware delete = new DeleteLocalUserMiddleware(loginErrorNotificator);
        root.linkWith(upload);
        upload.linkWith(download);
        download.linkWith(save);
        save.linkWith(delete);
        return root;
    }
}
