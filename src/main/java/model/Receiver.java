package model;

public class Receiver extends Person {
    private int receiverID;

    public Receiver() {}

    public Receiver(int receiverID, String name, String address, String contactNumber) {
        super(name, address, contactNumber);
        this.receiverID = receiverID;
    }


    public int getReceiverID() {
        return receiverID;
    }

    // Methods
    public void shipmentStatus(int shipmentID) {

    }
}
