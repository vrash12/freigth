package model;

public class ShipmentVolumeReport {
    public int deliveredCount;
    public int pendingCount;
    public int otherCount;
    public int totalShipments;

    @Override
    public String toString() {
        return "Shipment Volume Report:\n" +
                "Delivered: " + deliveredCount + "\n" +
                "Pending: " + pendingCount + "\n" +
                "Other: " + otherCount + "\n" +
                "Total: " + totalShipments;
    }
}
