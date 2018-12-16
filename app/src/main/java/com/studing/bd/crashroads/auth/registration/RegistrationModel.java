package com.studing.bd.crashroads.auth.registration;

import com.studing.bd.crashroads.ErrorHandler;
import com.studing.bd.crashroads.database.local_database.services.SaveLocalUserService;
import com.studing.bd.crashroads.database.remote_database.FirebaseInstant;
import com.studing.bd.crashroads.model.User;

public class RegistrationModel {

    public void saveUserToLocalDatabase(User user, ErrorHandler errorHandler) {
        SaveLocalUserService save = new SaveLocalUserService(errorHandler);
        save.checkNext(user);
        FirebaseInstant.auth().signOut();

    }
}
