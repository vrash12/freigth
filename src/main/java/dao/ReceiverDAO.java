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
        String sql = "SELECT userID, name, contactNumber, address FROM Users WHERE role = 'Receiver'";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int userID = rs.getInt("userID");
                String name = rs.getString("name");
                String contactNumber = rs.getString("contactNumber");
                String address = rs.getString("address");
                Receiver receiver = new Receiver(userID, name, contactNumber, address);
                receivers.add(receiver);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return receivers;
    }


    public boolean receiverExists(int userID) {
        String sql = "SELECT 1 FROM Users WHERE userID = ? AND role = 'Receiver'";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
