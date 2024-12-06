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
        String sql = "SELECT * FROM Sender";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int customerID = rs.getInt("customerID");
                String name = rs.getString("name");
                String contactNumber = rs.getString("contactNumber");
                String address = rs.getString("address");
                Sender sender = new Sender(customerID, name, contactNumber, address);
                senders.add(sender);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return senders;
    }

    public boolean senderExists(int customerID) {
        String sql = "SELECT 1 FROM Sender WHERE customerID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, customerID);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    // Implement other methods like viewShipmentStatus and viewShipmentHistory if they interact with the database
}
