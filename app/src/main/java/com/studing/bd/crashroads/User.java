package com.studing.bd.crashroads;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.firebase.database.IgnoreExtraProperties;

@Entity
@IgnoreExtraProperties
public class User {

    @PrimaryKey
    public int uid;
    @ColumnInfo(name = "name")
    public String username;
    @ColumnInfo(name = "email")
    public String email;
    @ColumnInfo(name = "image_url")
    public String image;
    @ColumnInfo(name = "driving_exp")
    public int drivingExperience;
    @ColumnInfo(name = "country")
    public String country;
    @ColumnInfo(name = "birthday_date")
    public String birthdayDate;
    @ColumnInfo(name = "gender")
    public Gender gender;

    protected User() {

    }

    private User(UserBuilder builder) {
        this.username = builder.username;
        this.email = builder.email;
        this.image = builder.image;
        this.drivingExperience = builder.drivingExperience;
        this.country = builder.country;
        this.birthdayDate = builder.birthdayDate;
        this.gender = builder.gender;
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public static class UserBuilder {

        private String username;
        private String email;
        private String image;
        private int drivingExperience;
        private String country;
        private String birthdayDate;
        private Gender gender;

        public UserBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder image(String image) {
            this.image = image;
            return this;
        }

        public UserBuilder drivingExperience(int drivingExperience) {
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
