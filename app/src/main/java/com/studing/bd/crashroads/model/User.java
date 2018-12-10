package com.studing.bd.crashroads.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

@Entity
@IgnoreExtraProperties
public class User {

    @NonNull
    @PrimaryKey
    @Exclude
    public String uid;

    @ColumnInfo(name = "name")
    public String username;

    @ColumnInfo(name = "email")
    public String email;

    @Ignore
    public String imageUrl;

    @Exclude
    @ColumnInfo(name = "image_byte", typeAffinity = ColumnInfo.BLOB)
    public byte[] imageByte;

    @ColumnInfo(name = "driving_exp")
    public String drivingExperience;

    @ColumnInfo(name = "country")
    public String country;

    @ColumnInfo(name = "birthday_date")
    public String birthdayDate;

    @ColumnInfo(name = "gender")
    public String gender;

    public User() {

    }

    private User(UserBuilder builder) {
        this.uid = builder.uid;
        this.username = builder.username;
        this.email = builder.email;
        this.imageByte = builder.imageByte;
        this.imageUrl = builder.imageUrl;
        this.drivingExperience = builder.drivingExperience;
        this.country = builder.country;
        this.birthdayDate = builder.birthdayDate;
        if(builder.gender == Gender.MALE) this.gender = "m";
        if(builder.gender == Gender.FEMALE) this.gender = "f";
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public static class UserBuilder {

        private String uid;
        private String username;
        private String email;
        private byte[] imageByte;
        private String imageUrl;
        private String drivingExperience;
        private String country;
        private String birthdayDate;
        private Gender gender;


        public UserBuilder uid(String uid) {
            this.uid = uid;
            return this;
        }

        public UserBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder imageByte(byte[] imageByte) {
            this.imageByte = imageByte;
            return this;
        }

        public UserBuilder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public UserBuilder drivingExperience(String drivingExperience) {
            this.drivingExperience = drivingExperience;
            return this;
        }

        public UserBuilder country(String country) {
            this.country = country;
            return this;
        }

        public UserBuilder birthdayDate(String birthdayDate) {
            this.birthdayDate = birthdayDate;
            return this;
        }

        public UserBuilder gender(Gender gender) {
            this.gender = gender;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
