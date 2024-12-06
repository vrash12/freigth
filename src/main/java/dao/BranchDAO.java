package dao;

import model.Branch;
import model.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BranchDAO {
    private Connection connection;

    public BranchDAO() {
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addBranch(Branch branch) {
        String sql = "INSERT INTO Branch (branchName, location, contactNumber, managerName) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, branch.getBranchName());
            stmt.setString(2, branch.getLocation());
            stmt.setString(3, branch.getContactNumber());
            stmt.setString(4, branch.getManagerName());
            stmt.executeUpdate();
            System.out.println("Branch added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeBranch(int branchID) {
        String sql = "DELETE FROM Branch WHERE branchID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, branchID);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Branch removed successfully.");
            } else {
                System.out.println("Branch ID not found. No branch removed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBranch(Branch branch) {
        String sql = "UPDATE Branch SET branchName = ?, location = ?, contactNumber = ?, managerName = ? WHERE branchID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, branch.getBranchName());
            stmt.setString(2, branch.getLocation());
            stmt.setString(3, branch.getContactNumber());
            stmt.setString(4, branch.getManagerName());
            stmt.setInt(5, branch.getBranchID());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Branch updated successfully.");
            } else {
                System.out.println("Branch ID not found. No branch updated.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Branch> getAllBranches() {
        List<Branch> branches = new ArrayList<>();
        String sql = "SELECT * FROM Branch";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int branchID = rs.getInt("branchID");
                String branchName = rs.getString("branchName");
                String location = rs.getString("location");
                String contactNumber = rs.getString("contactNumber");
                String managerName = rs.getString("managerName");
                Branch branch = new Branch(branchID, branchName, location, contactNumber, managerName);
                branches.add(branch);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return branches;
    }

    public Branch getBranchByID(int branchID) {
        String sql = "SELECT * FROM Branch WHERE branchID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, branchID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String branchName = rs.getString("branchName");
                String location = rs.getString("location");
                String contactNumber = rs.getString("contactNumber");
                String managerName = rs.getString("managerName");
                return new Branch(branchID, branchName, location, contactNumber, managerName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean branchExists(int branchID) {
        String sql = "SELECT 1 FROM Branch WHERE branchID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, branchID);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
