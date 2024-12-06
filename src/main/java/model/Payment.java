package model;

public class Payment {
    private int paymentID;
    private int shipmentID;
    private double amount;
    private String paymentMethod;
    private String status;

    public Payment() {}

    public Payment(int paymentID, int shipmentID, double amount, String paymentMethod, String status) {
        this.paymentID = paymentID;
        this.shipmentID = shipmentID;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.status = status;
    }

    // Getters and Setters
    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public int getShipmentID() {
        return shipmentID;
    }

    public void setShipmentID(int shipmentID) {
        this.shipmentID = shipmentID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Methods
    // processPayment() could simulate payment verification and update the status
    public void processPayment() {
        // In a real system, here you might integrate with a payment service.
        // For now, we'll simulate a successful payment.
        if (this.amount > 0 && (this.paymentMethod != null && !this.paymentMethod.isEmpty())) {
            this.status = "Paid";
        } else {
            this.status = "Failed";
        }
    }

    // generateReceipt() creates a textual representation of the transaction
    public String generateReceipt() {
        StringBuilder sb = new StringBuilder();
        sb.append("------ Payment Receipt ------\n");
        sb.append("Payment ID: ").append(paymentID).append("\n");
        sb.append("Shipment ID: ").append(shipmentID).append("\n");
        sb.append("Amount: ").append(amount).append("\n");
        sb.append("Payment Method: ").append(paymentMethod).append("\n");
        sb.append("Status: ").append(status).append("\n");
        sb.append("----------------------------\n");
        return sb.toString();
    }
}
