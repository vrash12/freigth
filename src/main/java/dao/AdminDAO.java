package dao;

import model.Admin;
import model.User;
import model.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {
    private Connection connection;

    public AdminDAO() {
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addUser(User user) {
        String sql = "INSERT INTO Users (name, role, credentials) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getRole());
            stmt.setString(3, user.getCredentials());
            stmt.executeUpdate();
            System.out.println("User added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean userExists(int userID) {
        String sql = "SELECT 1 FROM Users WHERE userID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM Users";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int userID = rs.getInt("userID");
                String name = rs.getString("name");
                String role = rs.getString("role");
                String credentials = rs.getString("credentials");
                User user = new User(userID, name, role, credentials);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void removeUser(int userID) {
        String sql = "DELETE FROM Users WHERE userID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            stmt.executeUpdate();
            System.out.println("User removed successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void assignRole(int userID, String role) {
        String sql = "UPDATE Users SET role = ? WHERE userID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, role);
            stmt.setInt(2, userID);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("User ID not found. No role assigned.");
            } else {
                System.out.println("Role assigned successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
