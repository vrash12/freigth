package view;

import model.User;
import model.Bus;
import model.Route;
import model.Shipment;
import model.Branch;
import model.Sender;
import model.Receiver;

import dao.RouteDAO;
import java.util.List;
import java.util.Scanner;

public class AdminView {
    private Scanner scanner;

    public AdminView() {
        scanner = new Scanner(System.in);
    }

    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public int getMainMenuChoice() {
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }

    public void displayMenu() {
        clearScreen();
        System.out.println("--- Admin Dashboard ---");
        System.out.println("1. User Management");
        System.out.println("2. Bus Management");
        System.out.println("3. Route Management");
        System.out.println("4. Shipment Management");
        System.out.println("5. Branch Management");
        System.out.println("6. Exit");
        System.out.print("Please select an option: ");
    }

    // ---------------------
    // USER MANAGEMENT
    // ---------------------
    public void displayUserManagementMenu() {
        System.out.println("--- User Management ---");
        System.out.println("a. Add User");
        System.out.println("b. Remove User");
        System.out.println("c. Assign Role");
        System.out.println("d. Show Current Users");
        System.out.println("e. Back to Main Menu");
        System.out.print("Please select an option: ");
    }

    public void displayUsers(List<User> users) {
        if (users.isEmpty()) {
            System.out.println("No users available.");
            return;
        }
        System.out.println("---- Current Users ----");
        for (User user : users) {
            System.out.println("UserID: " + user.getUserID() + ", Name: " + user.getName() + ", Role: " + user.getRole());
        }
        System.out.println("-----------------------");
    }

    public char getUserManagementChoice() {
        char choice = scanner.next().charAt(0);
        scanner.nextLine();
        return choice;
    }

    public User getUserDetails() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        String role = selectRole("Select role:");
        System.out.print("Enter credentials: ");
        String credentials = scanner.nextLine();
        System.out.print("Enter contact number: ");
        String contactNumber = scanner.nextLine();
        System.out.print("Enter address: ");
        String address = scanner.nextLine();

        return new User(0, name, role, credentials, contactNumber, address);

    }

    private String selectRole(String prompt) {
        int roleChoice = 0;
        String role = "";
        while (true) {
            System.out.println(prompt);
            System.out.println("[1] Sender");
            System.out.println("[2] Receiver");
            System.out.print("Enter choice: ");
            if (scanner.hasNextInt()) {
                roleChoice = scanner.nextInt();
                scanner.nextLine(); // consume newline
                if (roleChoice == 1) {
                    role = "Sender";
                    break;
                } else if (roleChoice == 2) {
                    role = "Receiver";
                    break;
                } else {
                    System.out.println("Invalid choice, please try again.");
                }
            } else {
                System.out.println("Invalid input, please enter a number.");
                scanner.nextLine(); // consume invalid input
            }
        }
        return role;
    }

    public int getUserID(List<User> users) {
        if (users.isEmpty()) {
            System.out.println("No users available.");
            return -1;
        }
        displayUsers(users);
        System.out.print("Enter User ID: ");
        int userID = scanner.nextInt();
        scanner.nextLine();
        return userID;
    }

    public String getRole() {
        // Role selection with menu when assigning a new role
        return selectRole("Select new role:");
    }

    // ---------------------
    // BUS MANAGEMENT
    // ---------------------
    public void displayBusManagementMenu() {
        System.out.println("--- Bus Management ---");
        System.out.println("a. Add Bus");
        System.out.println("b. Remove Bus");
        System.out.println("c. Assign Route to Bus");
        System.out.println("d. Update Bus Capacity");
        System.out.println("e. Show All Buses");
        System.out.println("f. Back to Main Menu");
        System.out.print("Please select an option: ");
    }

    public char getBusManagementChoice() {
        char choice = scanner.next().charAt(0);
        scanner.nextLine();
        return choice;
    }

    public Bus getBusDetails() {
        System.out.print("Enter capacity: ");
        int capacity = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter route ID (0 if none): ");
        int routeID = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter current status: ");
        String currentStatus = scanner.nextLine();

        System.out.print("Enter driver name: ");
        String driver = scanner.nextLine();

        return new Bus(0, capacity, routeID, currentStatus, driver);
    }

    public void displayAvailableBuses(List<Bus> buses, RouteDAO routeDAO) {
        if (buses.isEmpty()) {
            System.out.println("No buses available.");
            return;
        }
        System.out.println("---- Available Buses ----");
        for (Bus bus : buses) {
            String routeInfo = "No route assigned";
            if (bus.getRouteID() > 0) {
                Route route = routeDAO.getRouteByID(bus.getRouteID());
                if (route != null) {
                    routeInfo = route.getStartLocation() + " to " + route.getEndLocation();
                } else {
                    routeInfo = "Route ID " + bus.getRouteID() + " not found";
                }
            }
            System.out.println("BusID: " + bus.getBusID()
                    + ", Capacity: " + bus.getCapacity()
                    + ", Route: " + routeInfo
                    + ", Status: " + bus.getCurrentStatus()
                    + ", Driver: " + bus.getDriver());
        }
        System.out.println("-------------------------");
    }

    public int getBusID(List<Bus> buses, RouteDAO routeDAO) {
        if (buses.isEmpty()) {
            System.out.println("No buses available.");
            return -1;
        }
        displayAvailableBuses(buses, routeDAO);
        System.out.print("Enter Bus ID: ");
        int busID = scanner.nextInt();
        scanner.nextLine();
        return busID;
    }

    public int getNewCapacity() {
        System.out.print("Enter new capacity: ");
        return scanner.nextInt();
    }

    public String getNewRoute() {
        System.out.print("Enter new route: ");
        scanner.nextLine(); // consume any leftover newline
        return scanner.nextLine();
    }

    // ---------------------
    // ROUTE MANAGEMENT
    // ---------------------
    public void displayRouteManagementMenu() {
        System.out.println("--- Route Management ---");
        System.out.println("a. Add Route");
        System.out.println("b. Remove Route");
        System.out.println("c. Update Route Distance");
        System.out.println("d. Show All Routes");
        System.out.println("e. Back to Main Menu");
        System.out.print("Please select an option: ");
    }

    public char getRouteManagementChoice() {
        char choice = scanner.next().charAt(0);
        scanner.nextLine();
        return choice;
    }

    public Route getRouteDetails() {
        System.out.print("Enter start location: ");
        String startLocation = scanner.nextLine();
        System.out.print("Enter end location: ");
        String endLocation = scanner.nextLine();
        System.out.print("Enter distance (in km): ");
        double distance = scanner.nextDouble();
        scanner.nextLine();
        return new Route(0, startLocation, endLocation, distance);
    }

    public void displayAvailableRoutes(List<Route> routes) {
        if (routes.isEmpty()) {
            System.out.println("No routes available.");
            return;
        }
        System.out.println("---- Available Routes ----");
        for (Route route : routes) {
            System.out.println("RouteID: " + route.getRouteID()
                    + ", Start: " + route.getStartLocation()
                    + ", End: " + route.getEndLocation()
                    + ", Distance: " + route.getDistance() + " km");
        }
        System.out.println("--------------------------");
    }

    public int getRouteID(List<Route> routes) {
        if (routes.isEmpty()) {
            System.out.println("No routes available.");
            return -1;
        }
        displayAvailableRoutes(routes);
        System.out.print("Enter Route ID: ");
        int routeID = scanner.nextInt();
        scanner.nextLine();
        return routeID;
    }

    public double getNewDistance() {
        System.out.print("Enter new distance (in km): ");
        double distance = scanner.nextDouble();
        scanner.nextLine();
        return distance;
    }

    // ---------------------
    // SHIPMENT MANAGEMENT
    // ---------------------
    public void displayShipmentManagementMenu() {
        System.out.println("--- Shipment Management ---");
        System.out.println("a. Create Shipment");
        System.out.println("b. Update Shipment Status");
        System.out.println("c. Show All Shipments");
        System.out.println("d. Back to Main Menu");
        System.out.println("e. Track Shipment by Tracking Number");
        System.out.print("Please select an option: ");
    }

    public String getTrackingNumberInput() {
        System.out.print("Enter Tracking Number: ");
        return scanner.nextLine();
    }

    public char getShipmentManagementChoice() {
        char choice = scanner.next().charAt(0);
        scanner.nextLine();
        return choice;
    }

    public Shipment getShipmentDetails(int senderID, int receiverID, int busID, int branchID) {
        System.out.print("Enter Weight (kg): ");
        double weight = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter Dimensions (LxWxH): ");
        String dimensions = scanner.nextLine();
        String status = "Pending";
        double cost = 0.0;

        return new Shipment(0, senderID, receiverID, busID, branchID, weight, dimensions, status, cost);
    }

    public void displayShipments(List<Shipment> shipments) {
        if (shipments.isEmpty()) {
            System.out.println("No shipments available.");
            return;
        }
        System.out.println("---- All Shipments ----");
        for (Shipment shipment : shipments) {
            System.out.println("ShipmentID: " + shipment.getShipmentID()
                    + ", SenderID: " + shipment.getSenderID()
                    + ", ReceiverID: " + shipment.getReceiverID()
                    + ", BusID: " + shipment.getBusID()
                    + ", BranchID: " + shipment.getBranchID()
                    + ", Weight: " + shipment.getWeight() + " kg"
                    + ", Dimensions: " + shipment.getDimensions()
                    + ", Status: " + shipment.getStatus()
                    + ", Cost: " + shipment.getCost());
        }
        System.out.println("-----------------------");
    }

    public int getShipmentID(List<Shipment> shipments) {
        if (shipments.isEmpty()) {
            System.out.println("No shipments available.");
            return -1;
        }
        displayShipments(shipments);
        System.out.print("Enter Shipment ID: ");
        int shipmentID = scanner.nextInt();
        scanner.nextLine();
        return shipmentID;
    }

    // ---------------------
    // BRANCH MANAGEMENT
    // ---------------------
    public void displayBranchManagementMenu() {
        System.out.println("--- Branch Management ---");
        System.out.println("a. Add Branch");
        System.out.println("b. Remove Branch");
        System.out.println("c. Update Branch");
        System.out.println("d. Show All Branches");
        System.out.println("e. Back to Main Menu");
        System.out.print("Please select an option: ");
    }

    public char getBranchManagementChoice() {
        char choice = scanner.next().charAt(0);
        scanner.nextLine();
        return choice;
    }

    public Branch getBranchDetails() {
        System.out.print("Enter Branch Name: ");
        String branchName = scanner.nextLine();
        System.out.print("Enter Location: ");
        String location = scanner.nextLine();
        System.out.print("Enter Contact Number: ");
        String contactNumber = scanner.nextLine();
        System.out.print("Enter Manager Name: ");
        String managerName = scanner.nextLine();
        return new Branch(0, branchName, location, contactNumber, managerName);
    }

    public Branch getUpdatedBranchDetails(int branchID) {
        System.out.print("Enter New Branch Name: ");
        String branchName = scanner.nextLine();
        System.out.print("Enter New Location: ");
        String location = scanner.nextLine();
        System.out.print("Enter New Contact Number: ");
        String contactNumber = scanner.nextLine();
        System.out.print("Enter New Manager Name: ");
        String managerName = scanner.nextLine();
        return new Branch(branchID, branchName, location, contactNumber, managerName);
    }

    public void displayBranches(List<Branch> branches) {
        if (branches.isEmpty()) {
            System.out.println("No branches available.");
            return;
        }
        System.out.println("---- All Branches ----");
        for (Branch branch : branches) {
            System.out.println("BranchID: " + branch.getBranchID()
                    + ", Name: " + branch.getBranchName()
                    + ", Location: " + branch.getLocation()
                    + ", Contact: " + branch.getContactNumber()
                    + ", Manager: " + branch.getManagerName());
        }
        System.out.println("----------------------");
    }

    public int getBranchID(List<Branch> branches) {
        if (branches.isEmpty()) {
            System.out.println("No branches available.");
            return -1;
        }
        displayBranches(branches);
        System.out.print("Enter Branch ID: ");
        int branchID = scanner.nextInt();
        scanner.nextLine();
        return branchID;
    }

    // ---------------------
    // SENDER & RECEIVER
    // ---------------------
    public void displayAvailableSenders(List<Sender> senders) {
        if (senders.isEmpty()) {
            System.out.println("No senders available.");
            return;
        }
        System.out.println("---- Available Senders ----");
        for (Sender sender : senders) {
            System.out.println("SenderID: " + sender.getCustomerID()
                    + ", Name: " + sender.getName()
                    + ", Contact: " + sender.getContactNumber()
                    + ", Address: " + sender.getAddress());
        }
        System.out.println("---------------------------");
    }

    public int getSenderID(List<Sender> senders) {
        if (senders.isEmpty()) {
            System.out.println("No senders available.");
            return -1;
        }
        displayAvailableSenders(senders);
        System.out.print("Enter Sender ID: ");
        int senderID = scanner.nextInt();
        scanner.nextLine();
        return senderID;
    }

    public void displayAvailableReceivers(List<Receiver> receivers) {
        if (receivers.isEmpty()) {
            System.out.println("No receivers available.");
            return;
        }
        System.out.println("---- Available Receivers ----");
        for (Receiver receiver : receivers) {
            System.out.println("ReceiverID: " + receiver.getReceiverID()
                    + ", Name: " + receiver.getName()
                    + ", Contact: " + receiver.getContactNumber()
                    + ", Address: " + receiver.getAddress());
        }
        System.out.println("------------------------------");
    }

    public int getReceiverID(List<Receiver> receivers) {
        if (receivers.isEmpty()) {
            System.out.println("No receivers available.");
            return -1;
        }
        displayAvailableReceivers(receivers);
        System.out.print("Enter Receiver ID: ");
        int receiverID = scanner.nextInt();
        scanner.nextLine();
        return receiverID;
    }

    public String getNewShipmentStatus() {
        System.out.print("Enter new status: ");
        return scanner.nextLine();
    }
}
