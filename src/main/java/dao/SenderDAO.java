package dao;


import model.Sender;
import model.DatabaseConnection;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SenderDAO {
    private Connection connection;

    public SenderDAO() {
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createSender(Sender sender) {
        String sql = "INSERT INTO Sender (name, address, contactNumber) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, sender.getName());
            stmt.setString(2, sender.getAddress());
            stmt.setString(3, sender.getContactNumber());
            stmt.executeUpdate();
            System.out.println("Sender created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Sender> getAllSenders() {
        List<Sender> senders = new ArrayList<>();
        String sql = "SELECT userID, name, contactNumber, address FROM Users WHERE role = 'Sender'";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int userID = rs.getInt("userID");
                String name = rs.getString("name");
                String contactNumber = rs.getString("contactNumber");
                String address = rs.getString("address");
                // Sender constructor: Sender(int customerID, String name, String contactNumber, String address)
                Sender sender = new Sender(userID, name, contactNumber, address);
                senders.add(sender);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return senders;
    }

    public boolean senderExists(int customerID) {
        String sql = "SELECT 1 FROM Users WHERE userID = ? AND role = 'Sender'";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, customerID);
            ResultSet rs = stmt.executeQuery();
            boolean exists = rs.next();
            System.out.println("senderExists(" + customerID + ") returns " + exists);
            return exists;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }





    // Implement other methods like viewShipmentStatus and viewShipmentHistory if they interact with the database
}
