package dao;

import model.Bus;
import model.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BusDAO {
    private Connection connection;

    public BusDAO() {
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addBus(Bus bus) {
        String sql = "INSERT INTO Bus (capacity, routeID, currentStatus, driver) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, bus.getCapacity());

            if (bus.getRouteID() > 0) {
                stmt.setInt(2, bus.getRouteID());
            } else {
                stmt.setNull(2, Types.INTEGER);
            }

            stmt.setString(3, bus.getCurrentStatus());
            stmt.setString(4, bus.getDriver());
            stmt.executeUpdate();
            System.out.println("Bus added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean busExists(int busID) {
        String sql = "SELECT 1 FROM Bus WHERE busID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, busID);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void removeBus(int busID) {
        String sql = "DELETE FROM Bus WHERE busID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, busID);
            stmt.executeUpdate();
            System.out.println("Bus removed successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCapacity(int busID, int newCapacity) {
        String sql = "UPDATE Bus SET capacity = ? WHERE busID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, newCapacity);
            stmt.setInt(2, busID);
            stmt.executeUpdate();
            System.out.println("Bus capacity updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void assignRoute(int busID, int routeID) {
        String sql = "UPDATE Bus SET routeID = ? WHERE busID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, routeID);
            stmt.setInt(2, busID);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Bus route assigned successfully.");
            } else {
                System.out.println("Bus ID not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Bus getBusByID(int busID) {
        String sql = "SELECT * FROM Bus WHERE busID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, busID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int capacity = rs.getInt("capacity");
                int routeID = rs.getInt("routeID");
                if (rs.wasNull()) {
                    routeID = 0; // Indicate no route assigned
                }
                String currentStatus = rs.getString("currentStatus");
                String driver = rs.getString("driver");
                return new Bus(busID, capacity, routeID, currentStatus, driver);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Bus> getAllBuses() {
        List<Bus> buses = new ArrayList<>();
        String sql = "SELECT * FROM Bus";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int busID = rs.getInt("busID");
                int capacity = rs.getInt("capacity");
                int routeID = rs.getInt("routeID");
                if (rs.wasNull()) {
                    routeID = 0; // Indicate no route assigned
                }
                String currentStatus = rs.getString("currentStatus");
                String driver = rs.getString("driver");
                Bus bus = new Bus(busID, capacity, routeID, currentStatus, driver);
                buses.add(bus);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return buses;
    }
}
