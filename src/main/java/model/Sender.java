package model;


public class Sender extends Person {
    private int customerID;

    public Sender() {}

    public Sender(int customerID, String name, String contactNumber, String address) {
        super(name, address, contactNumber);
        this.customerID = customerID;
    }


    // Getters and Setters
    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }




    // Methods
    public void createShipment(Branch branch) {
        // Implementation to create a shipment
    }

    public void viewShipmentStatus(int shipmentID) {
        // Implementation to view the status of a shipment
    }

    public void viewShipmentHistory() {
        // Implementation to view shipment history
    }
}
