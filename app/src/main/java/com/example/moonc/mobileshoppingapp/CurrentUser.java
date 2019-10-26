package com.example.moonc.mobileshoppingapp;

public class CurrentUser {

    private static String currentUser;

    public CurrentUser() {
        currentUser = null;
    }

    public static String getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(String currentUser) {
        CurrentUser.currentUser = currentUser;
    }
}
