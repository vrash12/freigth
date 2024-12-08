package model;

public class User {
    private int userID;
    private String name;
    private String role;
    private String credentials;
    private String contactNumber;
    private String address;

    public User() {}

    public User(int userID, String name, String role, String credentials, String contactNumber, String address) {
        this.userID = userID;
        this.name = name;
        this.role = role;
        this.credentials = credentials;
        this.contactNumber = contactNumber;
        this.address = address;
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

    public String getContactNumber() {
        return contactNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    // ... (Other getters and setters)
}
