package model;
import java.util.UUID;

public class Shipment {
    private int shipmentID;
    private int senderID;
    private int receiverID;
    private int busID;
    private int branchID;
    private double weight;
    private String dimensions; // Can be formatted as "LxWxH"
    private String status;
    private double cost;
    private String trackingNumber;
    public Shipment() {}

    public Shipment(int shipmentID, int senderID, int receiverID, int busID, int branchID,
                    double weight, String dimensions, String status, double cost) {
        this.shipmentID = shipmentID;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.busID = busID;
        this.branchID = branchID;
        this.weight = weight;
        this.dimensions = dimensions;
        this.status = status;
        this.cost = cost;
        // Initialize tracking number if desired
        this.trackingNumber = generateTrackingNumber();
    }
    public Shipment(int shipmentID, int senderID, int receiverID, int busID, int branchID,
                    double weight, String dimensions, String status, double cost, String trackingNumber) {
        this(shipmentID, senderID, receiverID, busID, branchID, weight, dimensions, status, cost);
        this.trackingNumber = trackingNumber;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }
    // Getters and Setters
    public int getShipmentID() {
        return shipmentID;
    }

    public void setShipmentID(int shipmentID) {
        this.shipmentID = shipmentID;
    }

    public int getSenderID() {
        return senderID;
    }

    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }

    public int getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(int receiverID) {
        this.receiverID = receiverID;
    }

    public int getBusID() {
        return busID;
    }

    public void setBusID(int busID) {
        this.busID = busID;
    }

    public int getBranchID() {
        return branchID;
    }

    public void setBranchID(int branchID) {
        this.branchID = branchID;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    // Methods
    public double calculateCost(double ratePerKg) {
        // Simple cost calculation based on weight
        this.cost = this.weight * ratePerKg;
        return this.cost;
    }

    public void assignToBus(int busID) {
        this.busID = busID;
    }

    public void updateStatus(String newStatus) {
        this.status = newStatus;
    }

    public String generateTrackingNumber() {
        // Generate a unique tracking number
        // For simplicity, use a UUID substring
        return "TRK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
