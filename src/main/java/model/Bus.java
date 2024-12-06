package model;

public class Bus {
    private int busID;
    private int capacity;
    private int routeID;          // Changed from String route to int routeID
    private String currentStatus;
    private String driver;

    public Bus() {}

    public Bus(int busID, int capacity, int routeID, String currentStatus, String driver) {
        this.busID = busID;
        this.capacity = capacity;
        this.routeID = routeID;
        this.currentStatus = currentStatus;
        this.driver = driver;
    }

    // Getters and Setters
    public int getBusID() {
        return busID;
    }

    public void setBusID(int busID) {
        this.busID = busID;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getRouteID() {
        return routeID;
    }

    public void setRouteID(int routeID) {
        this.routeID = routeID;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    // Methods
    public void updateCapacity(int newCapacity) {
        this.capacity = newCapacity;
    }

    public void assignRoute(int newRouteID) {
        this.routeID = newRouteID;
    }

    public boolean checkAvailability() {
        return "Available".equalsIgnoreCase(this.currentStatus);
    }
}
