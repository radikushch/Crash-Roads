package com.studing.bd.crashroads;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    private String username;
    private String email;
    private String image;
    private int drivingExperience;
    private String country;
    private int age;

    protected User() {

    }

    private User(String username, String email, String image, int drivingExperience, String country, int age) {
        this.username = username;
        this.email = email;
        this.image = image;
        this.drivingExperience = drivingExperience;
        this.country = country;
        this.age = age;
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getDrivingExperience() {
        return drivingExperience;
    }

    public void setDrivingExperience(int drivingExperience) {
        this.drivingExperience = drivingExperience;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public static class UserBuilder {

        private String username;
        private String email;
        private String image;
        private int drivingExperience;
        private String country;
        private int age;

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

        public UserBuilder age(int age) {
            this.age = age;
            return this;
        }
    }
}
