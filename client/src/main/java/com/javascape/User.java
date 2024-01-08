package com.javascape;

public class User {
    
    private String email;

    private String username;

    private boolean isAdmin;

    private int householdID;


    public String getUsername() {
        return username;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public String getEmail() {
        return email;
    }

    public int getHouseholdID() {
        return householdID;
    }

    @Override
    public String toString() {
        return email;
    }

}
