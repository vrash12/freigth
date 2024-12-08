package dao;

import model.Shipment;
import model.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShipmentDAO {
    private Connection connection;

    public ShipmentDAO() {
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createShipment(Shipment shipment) {
        String sql = "INSERT INTO Shipment (senderID, receiverID, busID, branchID, weight, dimensions, status, cost, trackingNumber) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, shipment.getSenderID());
            stmt.setInt(2, shipment.getReceiverID());
            stmt.setInt(3, shipment.getBusID());
            stmt.setInt(4, shipment.getBranchID());
            stmt.setDouble(5, shipment.getWeight());
            stmt.setString(6, shipment.getDimensions());
            stmt.setString(7, shipment.getStatus());
            stmt.setDouble(8, shipment.getCost());
            // Ensure trackingNumber is set before insertion
            if (shipment.getTrackingNumber() == null || shipment.getTrackingNumber().isEmpty()) {
                shipment.setTrackingNumber(shipment.generateTrackingNumber());
            }
            stmt.setString(9, shipment.getTrackingNumber());

            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                shipment.setShipmentID(rs.getInt(1));
            }

            System.out.println("Shipment created successfully. Tracking Number: " + shipment.getTrackingNumber());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Shipment getShipmentByTrackingNumber(String trackingNumber) {
        String sql = "SELECT * FROM Shipment WHERE trackingNumber = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, trackingNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int shipmentID = rs.getInt("shipmentID");
                int senderID = rs.getInt("senderID");
                int receiverID = rs.getInt("receiverID");
                int busID = rs.getInt("busID");
                int branchID = rs.getInt("branchID");
                double weight = rs.getDouble("weight");
                String dimensions = rs.getString("dimensions");
                String status = rs.getString("status");
                double cost = rs.getDouble("cost");
                String tNumber = rs.getString("trackingNumber");
                return new Shipment(shipmentID, senderID, receiverID, busID, branchID, weight, dimensions, status, cost, tNumber);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean shipmentExists(int shipmentID) {
        String sql = "SELECT 1 FROM Shipment WHERE shipmentID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, shipmentID);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void updateShipmentStatus(int shipmentID, String newStatus) {
        String sql = "UPDATE Shipment SET status = ? WHERE shipmentID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, newStatus);
            stmt.setInt(2, shipmentID);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Shipment status updated successfully.");
            } else {
                System.out.println("Shipment ID not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Shipment> getAllShipments() {
        List<Shipment> shipments = new ArrayList<>();
        String sql = "SELECT * FROM Shipment";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int shipmentID = rs.getInt("shipmentID");
                int senderID = rs.getInt("senderID");
                int receiverID = rs.getInt("receiverID");
                int busID = rs.getInt("busID");
                int branchID = rs.getInt("branchID");
                double weight = rs.getDouble("weight");
                String dimensions = rs.getString("dimensions");
                String status = rs.getString("status");
                double cost = rs.getDouble("cost");
                Shipment shipment = new Shipment(shipmentID, senderID, receiverID, busID, branchID, weight, dimensions, status, cost);
                shipments.add(shipment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shipments;
    }

    public Shipment getShipmentByID(int shipmentID) {
        String sql = "SELECT * FROM Shipment WHERE shipmentID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, shipmentID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int senderID = rs.getInt("senderID");
                int receiverID = rs.getInt("receiverID");
                int busID = rs.getInt("busID");
                int branchID = rs.getInt("branchID");
                double weight = rs.getDouble("weight");
                String dimensions = rs.getString("dimensions");
                String status = rs.getString("status");
                double cost = rs.getDouble("cost");
                return new Shipment(shipmentID, senderID, receiverID, busID, branchID, weight, dimensions, status, cost);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
