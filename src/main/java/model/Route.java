package model;

public class Route {
    private int routeID;
    private String startLocation;
    private String endLocation;
    private double distance;

    public Route() {}

    public Route(int routeID, String startLocation, String endLocation, double distance) {
        this.routeID = routeID;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.distance = distance;
    }

    // Getters and Setters
    public int getRouteID() {
        return routeID;
    }

    public void setRouteID(int routeID) {
        this.routeID = routeID;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }


    public double calculateETA(double averageSpeed) {

        if (averageSpeed <= 0) {
            throw new IllegalArgumentException("Average speed must be greater than zero.");
        }
        return distance / averageSpeed;
    }

    public void calibrateRoute(double newDistance) {
        if (newDistance <= 0) {
            throw new IllegalArgumentException("Distance must be greater than zero.");
        }
        this.distance = newDistance;
    }
}
