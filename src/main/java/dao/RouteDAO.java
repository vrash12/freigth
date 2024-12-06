package dao;

import model.Route;
import model.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RouteDAO {
    private Connection connection;

    public RouteDAO() {
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addRoute(Route route) {
        String sql = "INSERT INTO Route (startLocation, endLocation, distance) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, route.getStartLocation());
            stmt.setString(2, route.getEndLocation());
            stmt.setDouble(3, route.getDistance());
            stmt.executeUpdate();
            System.out.println("Route added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean routeExists(int routeID) {
        String sql = "SELECT 1 FROM Route WHERE routeID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, routeID);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void removeRoute(int routeID) {
        String sql = "DELETE FROM Route WHERE routeID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, routeID);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Route removed successfully.");
            } else {
                System.out.println("Route ID not found. No route removed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateDistance(int routeID, double newDistance) {
        String sql = "UPDATE Route SET distance = ? WHERE routeID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, newDistance);
            stmt.setInt(2, routeID);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Route distance updated successfully.");
            } else {
                System.out.println("Route ID not found. Distance not updated.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Route getRouteByID(int routeID) {
        String sql = "SELECT * FROM Route WHERE routeID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, routeID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String startLocation = rs.getString("startLocation");
                String endLocation = rs.getString("endLocation");
                double distance = rs.getDouble("distance");
                return new Route(routeID, startLocation, endLocation, distance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Route> getAllRoutes() {
        List<Route> routes = new ArrayList<>();
        String sql = "SELECT * FROM Route";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int routeID = rs.getInt("routeID");
                String startLocation = rs.getString("startLocation");
                String endLocation = rs.getString("endLocation");
                double distance = rs.getDouble("distance");
                routes.add(new Route(routeID, startLocation, endLocation, distance));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return routes;
    }
}
