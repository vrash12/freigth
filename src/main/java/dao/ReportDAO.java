package dao;

import model.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import model.ShipmentVolumeReport;

public class ReportDAO {
    private Connection connection;

    public ReportDAO() {
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves shipment volume report within a given date range.
     * @param startDate The start of the period (inclusive).
     * @param endDate The end of the period (inclusive).
     * @return A ShipmentVolumeReport object containing counts of delivered, pending, and total shipments.
     */
    public ShipmentVolumeReport getShipmentVolumeReport(Date startDate, Date endDate) {
        String sql = "SELECT " +
                "SUM(CASE WHEN status = 'Delivered' THEN 1 ELSE 0 END) AS delivered_count, " +
                "SUM(CASE WHEN status = 'Pending' THEN 1 ELSE 0 END) AS pending_count, " +
                "SUM(CASE WHEN status NOT IN ('Delivered','Pending') THEN 1 ELSE 0 END) AS other_count, " +
                "COUNT(*) AS total_shipments " +
                "FROM Shipment " +
                "WHERE created_at BETWEEN ? AND ?";

        ShipmentVolumeReport report = new ShipmentVolumeReport();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setTimestamp(1, new java.sql.Timestamp(startDate.getTime()));
            stmt.setTimestamp(2, new java.sql.Timestamp(endDate.getTime()));

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                report.deliveredCount = rs.getInt("delivered_count");
                report.pendingCount = rs.getInt("pending_count");
                report.otherCount = rs.getInt("other_count");
                report.totalShipments = rs.getInt("total_shipments");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return report;
    }

    /**
     * Retrieves total revenue from paid shipments within a given date range.
     * @param startDate The start of the period (inclusive).
     * @param endDate The end of the period (inclusive).
     * @return The total revenue as a double.
     */
    public double getRevenueReport(Date startDate, Date endDate) {
        String sql = "SELECT SUM(amount) AS total_revenue FROM Payment WHERE paymentDate BETWEEN ? AND ?";
        double totalRevenue = 0.0;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setTimestamp(1, new java.sql.Timestamp(startDate.getTime()));
            stmt.setTimestamp(2, new java.sql.Timestamp(endDate.getTime()));

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                totalRevenue = rs.getDouble("total_revenue");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalRevenue;
    }
}
