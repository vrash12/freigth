package model;

public class Admin extends User {

    public Admin() {
        super();
    }

    public Admin(int userID, String name, String role, String credentials) {
        super(userID, name, role, credentials);
    }

    // Methods
    public void addUser(User user) {
        // Implementation to add a user to the system
    }

    public void removeUser(int userID) {
        // Implementation to remove a user from the system
    }

    public void assignRole(int userID, String role) {
        // Implementation to assign a role to a user
    }
}
