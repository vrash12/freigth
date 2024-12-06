package dao;

import model.Payment;
import model.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {
    private Connection connection;

    public PaymentDAO() {
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createPayment(Payment payment) {
        String sql = "INSERT INTO Payment (shipmentID, amount, paymentMethod, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, payment.getShipmentID());
            stmt.setDouble(2, payment.getAmount());
            stmt.setString(3, payment.getPaymentMethod());
            stmt.setString(4, payment.getStatus());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                payment.setPaymentID(rs.getInt(1));
            }

            System.out.println("Payment record created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePaymentStatus(int paymentID, String newStatus) {
        String sql = "UPDATE Payment SET status = ? WHERE paymentID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, newStatus);
            stmt.setInt(2, paymentID);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Payment status updated successfully.");
            } else {
                System.out.println("Payment ID not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Payment getPaymentByID(int paymentID) {
        String sql = "SELECT * FROM Payment WHERE paymentID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, paymentID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int shipmentID = rs.getInt("shipmentID");
                double amount = rs.getDouble("amount");
                String paymentMethod = rs.getString("paymentMethod");
                String status = rs.getString("status");
                return new Payment(paymentID, shipmentID, amount, paymentMethod, status);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Payment> getAllPayments() {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM Payment";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int paymentID = rs.getInt("paymentID");
                int shipmentID = rs.getInt("shipmentID");
                double amount = rs.getDouble("amount");
                String paymentMethod = rs.getString("paymentMethod");
                String status = rs.getString("status");
                payments.add(new Payment(paymentID, shipmentID, amount, paymentMethod, status));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }

    public boolean paymentExists(int paymentID) {
        String sql = "SELECT 1 FROM Payment WHERE paymentID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, paymentID);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
