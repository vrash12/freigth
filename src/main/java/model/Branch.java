package model;

public class Branch {
    private int branchID;
    private String branchName;
    private String location;
    private String contactNumber;
    private String managerName;

    public Branch() {}

    public Branch(int branchID, String branchName, String location, String contactNumber, String managerName) {
        this.branchID = branchID;
        this.branchName = branchName;
        this.location = location;
        this.contactNumber = contactNumber;
        this.managerName = managerName;
    }

    // Getters and Setters
    public int getBranchID() {
        return branchID;
    }

    public void setBranchID(int branchID) {
        this.branchID = branchID;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }
}
