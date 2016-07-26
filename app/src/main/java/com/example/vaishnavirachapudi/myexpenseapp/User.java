package com.example.vaishnavirachapudi.myexpenseapp;

import java.io.Serializable;

/**
 * Created by vaishnavirachapudi on 02/24/16.
 */
public class User implements Serializable {

    String userName;
    String email;
    String password;

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public User() {
    }

    public User(String userName, String email, String password) {

        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
