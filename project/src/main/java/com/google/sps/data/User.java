package com.google.sps.data;

public final class User {
    private final long id;
    private final String userEmail;
    private final String userName; 
    private final String userPassword;

     public User(long id, String userName, String userEmail, String userPassword) {
        this.id = id;
        this.userName = userName; 
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }
}
