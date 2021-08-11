package com.google.sps.data;

public final class User {
    private final long id;
    private final String userEmail;
    private final String userName; 
    private final String userPassword;
    private final String passwordHash;
    private final String passwordSalt;

    public User(long id, String userName, String userEmail, String userPassword, String passwordHash, String passwordSalt) {
        this.id = id;
        this.userName = userName; 
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.passwordHash = passwordHash;
        this.passwordSalt = passwordSalt;
    }

    public long getuserId() {
        return id;
    }
    
    public String getuserEmail() {
        return userEmail;
    }

    public String getuserName() {
        return userName;
    }

    public String getuserPassword() {
        return userPassword;
    }

    public String getHash() {
        return passwordHash;
    }

    public String getSalt() {
        return passwordSalt;
    }
}
