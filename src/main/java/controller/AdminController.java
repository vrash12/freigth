package controller;

import view.AdminView;
import model.Admin;
import dao.AdminDAO;
import model.User;
import java.util.List;
import java.util.Scanner;
import dao.BusDAO;
import dao.RouteDAO;
import model.Branch;
import model.Bus;
import dao.RouteDAO;
import model.Shipment;
import dao.ShipmentDAO;
import model.Route;
import dao.BranchDAO;
import dao.SenderDAO;
import dao.ReceiverDAO;
import model.Sender;
import model.Payment;
import dao.PaymentDAO;
import model.Receiver;

public class AdminController {
    private AdminView view;
    private Admin adminModel;
    private AdminDAO adminDAO;
    private BusDAO busDAO;
    private Scanner scanner;
    private RouteDAO routeDAO;
    private BranchDAO branchDAO;
    private ShipmentDAO shipmentDAO;
    private SenderDAO senderDAO;
    private ReceiverDAO receiverDAO;

    public AdminController(AdminView view, Admin adminModel) {
        this.view = view;
        this.adminModel = adminModel;
        this.adminDAO = new AdminDAO();
        this.busDAO = new BusDAO();
        this.routeDAO = new RouteDAO();
        this.shipmentDAO = new ShipmentDAO();
        this.branchDAO = new BranchDAO();
        this.senderDAO = new SenderDAO();
        this.receiverDAO = new ReceiverDAO();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean exit = false;
        while (!exit) {
            view.displayMenu();
            int choice = view.getMainMenuChoice();
            switch (choice) {
                case 1:
                    userManagement();
                    break;
                case 2:
                    busManagement();
                    break;
                case 3:
                    routeManagement();
                    break;
                case 4:
                    shipmentManagement();
                    break;
                case 5:
                    branchManagement();
                    break;
                case 6:
                    exit = true;
                    System.out.println("Exiting Admin Dashboard.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void userManagement() {
        boolean back = false;
        while (!back) {
            view.displayUserManagementMenu();
            char choice = view.getUserManagementChoice();
            switch (choice) {
                case 'a':
                    User newUser = view.getUserDetails();
                    adminDAO.addUser(newUser);
                    break;
                case 'b':
                    int userID = view.getUserID();
                    if (adminDAO.userExists(userID)) {
                        adminDAO.removeUser(userID);
                    } else {
                        System.out.println("User ID not found.");
                    }
                    break;
                case 'c':
                    int userIDForRole = view.getUserID();
                    if (adminDAO.userExists(userIDForRole)) {
                        scanner.nextLine();
                        String role = view.getRole();
                        adminDAO.assignRole(userIDForRole, role);
                        System.out.println("Role assigned successfully.");
                    } else {
                        System.out.println("User ID not found.");
                    }
                    break;
                case 'd':
                    List<User> users = adminDAO.getAllUsers();
                    view.displayUsers(users);
                    break;
                case 'e':
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void busManagement() {
        boolean back = false;
        while (!back) {
            view.displayBusManagementMenu();
            char choice = view.getBusManagementChoice();
            switch (choice) {
                case 'a':
                    Bus newBus = view.getBusDetails();
                    busDAO.addBus(newBus);
                    break;
                case 'b':
                    List<Bus> busesForRemoval = busDAO.getAllBuses();
                    view.displayAvailableBuses(busesForRemoval, routeDAO);
                    int busID = view.getBusID();
                    if (busDAO.busExists(busID)) {
                        busDAO.removeBus(busID);
                    } else {
                        System.out.println("Bus ID not found.");
                    }
                    break;
                case 'c':
                    List<Bus> busesForAssignment = busDAO.getAllBuses();
                    view.displayAvailableBuses(busesForAssignment, routeDAO);
                    int busIDForRoute = view.getBusID();
                    if (busDAO.busExists(busIDForRoute)) {
                        List<Route> routes = routeDAO.getAllRoutes();
                        view.displayAvailableRoutes(routes);
                        int routeID = view.getRouteID();
                        if (routeDAO.routeExists(routeID)) {
                            busDAO.assignRoute(busIDForRoute, routeID);
                        } else {
                            System.out.println("Route ID not found.");
                        }
                    } else {
                        System.out.println("Bus ID not found.");
                    }
                    break;
                case 'd':
                    List<Bus> busesForCapacityUpdate = busDAO.getAllBuses();
                    view.displayAvailableBuses(busesForCapacityUpdate, routeDAO);
                    int busIDForCapacity = view.getBusID();
                    if (busDAO.busExists(busIDForCapacity)) {
                        int newCapacity = view.getNewCapacity();
                        busDAO.updateCapacity(busIDForCapacity, newCapacity);
                    } else {
                        System.out.println("Bus ID not found.");
                    }
                    break;
                case 'e':
                    List<Bus> buses = busDAO.getAllBuses();
                    view.displayAvailableBuses(buses, routeDAO);
                    break;
                case 'f':
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void routeManagement() {
        boolean back = false;
        while (!back) {
            view.displayRouteManagementMenu();
            char choice = view.getRouteManagementChoice();
            switch (choice) {
                case 'a':
                    Route newRoute = view.getRouteDetails();
                    routeDAO.addRoute(newRoute);
                    break;
                case 'b':
                    List<Route> routesForRemoval = routeDAO.getAllRoutes();
                    view.displayAvailableRoutes(routesForRemoval);
                    int routeID = view.getRouteID();
                    if (routeDAO.routeExists(routeID)) {
                        routeDAO.removeRoute(routeID);
                    } else {
                        System.out.println("Route ID not found.");
                    }
                    break;
                case 'c':
                    List<Route> routesForUpdate = routeDAO.getAllRoutes();
                    view.displayAvailableRoutes(routesForUpdate);
                    int routeIDForUpdate = view.getRouteID();
                    if (routeDAO.routeExists(routeIDForUpdate)) {
                        double newDistance = view.getNewDistance();
                        routeDAO.updateDistance(routeIDForUpdate, newDistance);
                    } else {
                        System.out.println("Route ID not found.");
                    }
                    break;
                case 'd':
                    List<Route> routes = routeDAO.getAllRoutes();
                    view.displayAvailableRoutes(routes);
                    break;
                case 'e':
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void shipmentManagement() {
        boolean back = false;
        while (!back) {
            view.displayShipmentManagementMenu();
            char choice = view.getShipmentManagementChoice();
            switch (choice) {
                case 'a':
                    // Existing code for selecting sender, receiver, bus, branch
                    List<Sender> senders = senderDAO.getAllSenders();
                    if (senders.isEmpty()) {
                        System.out.println("No senders available. Cannot create shipment.");
                        break;
                    }
                    view.displayAvailableSenders(senders);
                    int senderID = view.getSenderID();
                    if (!senderDAO.senderExists(senderID)) {
                        System.out.println("Sender ID not found.");
                        break;
                    }

                    List<Receiver> receivers = receiverDAO.getAllReceivers();
                    if (receivers.isEmpty()) {
                        System.out.println("No receivers available. Cannot create shipment.");
                        break;
                    }
                    view.displayAvailableReceivers(receivers);
                    int receiverID = view.getReceiverID();
                    if (!receiverDAO.receiverExists(receiverID)) {
                        System.out.println("Receiver ID not found.");
                        break;
                    }

                    List<Bus> buses = busDAO.getAllBuses();
                    if (buses.isEmpty()) {
                        System.out.println("No buses available. Cannot create shipment.");
                        break;
                    }
                    view.displayAvailableBuses(buses, routeDAO);
                    int busID = view.getBusID();
                    if (!busDAO.busExists(busID)) {
                        System.out.println("Bus ID not found.");
                        break;
                    }

                    List<Branch> branches = branchDAO.getAllBranches();
                    if (branches.isEmpty()) {
                        System.out.println("No branches available. Cannot create shipment.");
                        break;
                    }
                    view.displayAvailableBranches(branches);
                    int branchID = view.getBranchID();
                    if (!branchDAO.branchExists(branchID)) {
                        System.out.println("Branch ID not found.");
                        break;
                    }

                    // Create the shipment
                    Shipment newShipment = view.getShipmentDetails(senderID, receiverID, busID, branchID);
                    double cost = newShipment.calculateCost(10.0); // Example rate per kg
                    newShipment.setCost(cost);
                    shipmentDAO.createShipment(newShipment); // Now newShipment should have an ID set

                    // Now that the shipment is created, let's process the payment
                    System.out.print("Enter payment method (e.g., Cash, Credit Card): ");
                    String paymentMethod = scanner.nextLine();

                    // Create Payment object with "Pending" status initially
                    Payment payment = new Payment(0, newShipment.getShipmentID(), newShipment.getCost(), paymentMethod, "Pending");

                    // Process the payment
                    payment.processPayment();

                    // Store the payment record
                    PaymentDAO paymentDAO = new PaymentDAO();
                    paymentDAO.createPayment(payment);

                    // Generate and display the receipt
                    String receipt = payment.generateReceipt();
                    System.out.println(receipt);

                    break;
                case 'b':
                    // Update shipment status code as before
                    List<Shipment> shipmentsForUpdate = shipmentDAO.getAllShipments();
                    view.displayShipments(shipmentsForUpdate);
                    int shipmentID = view.getShipmentID();
                    if (shipmentDAO.shipmentExists(shipmentID)) {
                        String newStatus = view.getNewShipmentStatus();
                        shipmentDAO.updateShipmentStatus(shipmentID, newStatus);
                    } else {
                        System.out.println("Shipment ID not found.");
                    }
                    break;
                case 'c':
                    // Show all shipments
                    List<Shipment> shipments = shipmentDAO.getAllShipments();
                    view.displayShipments(shipments);
                    break;
                case 'd':
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }


    private void branchManagement() {
        boolean back = false;
        while (!back) {
            view.displayBranchManagementMenu();
            char choice = view.getBranchManagementChoice();
            switch (choice) {
                case 'a':
                    Branch newBranch = view.getBranchDetails();
                    branchDAO.addBranch(newBranch);
                    break;
                case 'b':
                    List<Branch> branchesForRemoval = branchDAO.getAllBranches();
                    view.displayBranches(branchesForRemoval);
                    int branchID = view.getBranchID();
                    if (branchDAO.branchExists(branchID)) {
                        branchDAO.removeBranch(branchID);
                    } else {
                        System.out.println("Branch ID not found.");
                    }
                    break;
                case 'c':
                    List<Branch> branchesForUpdate = branchDAO.getAllBranches();
                    view.displayBranches(branchesForUpdate);
                    int branchIDForUpdate = view.getBranchID();
                    if (branchDAO.branchExists(branchIDForUpdate)) {
                        Branch updatedBranch = view.getUpdatedBranchDetails(branchIDForUpdate);
                        branchDAO.updateBranch(updatedBranch);
                    } else {
                        System.out.println("Branch ID not found.");
                    }
                    break;
                case 'd':
                    List<Branch> branches = branchDAO.getAllBranches();
                    view.displayBranches(branches);
                    break;
                case 'e':
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
