package model;

public class User {
    private int userID;
    private String name;
    private String role;
    private String credentials;

    public User() {}

    public User(int userID, String name, String role, String credentials) {
        this.userID = userID;
        this.name = name;
        this.role = role;
        this.credentials = credentials;
    }

    // Getters and Setters
    public int getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getCredentials() {
        return credentials;
    }


    // ... (Other getters and setters)
}
