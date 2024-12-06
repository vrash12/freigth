package dao;

import model.Receiver;
import model.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReceiverDAO {
    private Connection connection;

    public ReceiverDAO() {
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Receiver> getAllReceivers() {
        List<Receiver> receivers = new ArrayList<>();
        String sql = "SELECT * FROM Receiver";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int receiverID = rs.getInt("receiverID");
                String name = rs.getString("name");
                String contactNumber = rs.getString("contactNumber");
                String address = rs.getString("address");
                Receiver receiver = new Receiver(receiverID, name, contactNumber, address);
                receivers.add(receiver);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return receivers;
    }

    public boolean receiverExists(int receiverID) {
        String sql = "SELECT 1 FROM Receiver WHERE receiverID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, receiverID);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
