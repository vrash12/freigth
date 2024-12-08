package controller;

import view.AdminView;
import model.Admin;
import dao.AdminDAO;
import model.User;

import dao.BusDAO;
import dao.RouteDAO;
import dao.BranchDAO;
import dao.SenderDAO;
import dao.ReceiverDAO;
import dao.ReportDAO;
import dao.ShipmentDAO;
import dao.PaymentDAO;

import model.Branch;
import model.Bus;
import model.Shipment;
import model.Route;
import model.Sender;
import model.Receiver;
import model.Payment;
import model.ShipmentVolumeReport;

import java.util.List;
import java.util.Scanner;
import java.util.Date;
import java.util.Calendar;

public class AdminController {
    private AdminView view;
    private Admin adminModel;
    private AdminDAO adminDAO;
    private BusDAO busDAO;
    private RouteDAO routeDAO;
    private BranchDAO branchDAO;
    private ShipmentDAO shipmentDAO;
    private SenderDAO senderDAO;
    private ReceiverDAO receiverDAO;
    private ReportDAO reportDAO;
    private PaymentDAO paymentDAO;
    private Scanner scanner;

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
        this.reportDAO = new ReportDAO();
        this.paymentDAO = new PaymentDAO();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        // Simple admin login
        System.out.print("Enter admin username: ");
        String username = scanner.nextLine();
        System.out.print("Enter admin password: ");
        String password = scanner.nextLine();

        // Hardcoded check for now
        if (!("admin".equals(username) && "adminpass".equals(password))) {
            System.out.println("Invalid admin credentials. Exiting.");
            return;
        }

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
                case 'a': {
                    User newUser = view.getUserDetails();
                    adminDAO.addUser(newUser);
                    break;
                }
                case 'b': {
                    List<User> usersForRemoval = adminDAO.getAllUsers();
                    int userID = view.getUserID(usersForRemoval);
                    if (userID == -1) break; // no users or user not selected
                    if (adminDAO.userExists(userID)) {
                        adminDAO.removeUser(userID);
                    } else {
                        System.out.println("User ID not found.");
                    }
                    break;
                }
                case 'c': {
                    List<User> usersForRole = adminDAO.getAllUsers();
                    int userIDForRole = view.getUserID(usersForRole);
                    if (userIDForRole == -1) break;
                    if (adminDAO.userExists(userIDForRole)) {
                        String role = view.getRole();
                        adminDAO.assignRole(userIDForRole, role);
                        System.out.println("Role assigned successfully.");
                    } else {
                        System.out.println("User ID not found.");
                    }
                    break;
                }
                case 'd': {
                    List<User> users = adminDAO.getAllUsers();
                    view.displayUsers(users);
                    break;
                }
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
                case 'a': {
                    Bus newBus = view.getBusDetails();
                    busDAO.addBus(newBus);
                    break;
                }
                case 'b': {
                    List<Bus> busesForRemoval = busDAO.getAllBuses();
                    int busID = view.getBusID(busesForRemoval, routeDAO);
                    if (busID == -1) break;
                    if (busDAO.busExists(busID)) {
                        busDAO.removeBus(busID);
                    } else {
                        System.out.println("Bus ID not found.");
                    }
                    break;
                }
                case 'c': {
                    List<Bus> busesForAssignment = busDAO.getAllBuses();
                    int busIDForRoute = view.getBusID(busesForAssignment, routeDAO);
                    if (busIDForRoute == -1) break;
                    if (busDAO.busExists(busIDForRoute)) {
                        List<Route> routes = routeDAO.getAllRoutes();
                        int routeID = view.getRouteID(routes);
                        if (routeID == -1) break;
                        if (routeDAO.routeExists(routeID)) {
                            busDAO.assignRoute(busIDForRoute, routeID);
                        } else {
                            System.out.println("Route ID not found.");
                        }
                    } else {
                        System.out.println("Bus ID not found.");
                    }
                    break;
                }
                case 'd': {
                    List<Bus> busesForCapacityUpdate = busDAO.getAllBuses();
                    int busIDForCapacity = view.getBusID(busesForCapacityUpdate, routeDAO);
                    if (busIDForCapacity == -1) break;
                    if (busDAO.busExists(busIDForCapacity)) {
                        int newCapacity = view.getNewCapacity();
                        busDAO.updateCapacity(busIDForCapacity, newCapacity);
                    } else {
                        System.out.println("Bus ID not found.");
                    }
                    break;
                }
                case 'e': {
                    List<Bus> buses = busDAO.getAllBuses();
                    view.displayAvailableBuses(buses, routeDAO);
                    break;
                }
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
                case 'a': {
                    Route newRoute = view.getRouteDetails();
                    routeDAO.addRoute(newRoute);
                    break;
                }
                case 'b': {
                    List<Route> routesForRemoval = routeDAO.getAllRoutes();
                    int routeID = view.getRouteID(routesForRemoval);
                    if (routeID == -1) break;
                    if (routeDAO.routeExists(routeID)) {
                        routeDAO.removeRoute(routeID);
                    } else {
                        System.out.println("Route ID not found.");
                    }
                    break;
                }
                case 'c': {
                    List<Route> routesForUpdate = routeDAO.getAllRoutes();
                    int routeIDForUpdate = view.getRouteID(routesForUpdate);
                    if (routeIDForUpdate == -1) break;
                    if (routeDAO.routeExists(routeIDForUpdate)) {
                        double newDistance = view.getNewDistance();
                        routeDAO.updateDistance(routeIDForUpdate, newDistance);
                    } else {
                        System.out.println("Route ID not found.");
                    }
                    break;
                }
                case 'd': {
                    List<Route> routes = routeDAO.getAllRoutes();
                    view.displayAvailableRoutes(routes);
                    break;
                }
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
                case 'a': {
                    // Create Shipment
                    List<Sender> senders = senderDAO.getAllSenders();
                    if (senders.isEmpty()) {
                        System.out.println("No senders available. Cannot create shipment.");
                        break;
                    }
                    int senderID = view.getSenderID(senders);
                    if (senderID == -1) break;
                    if (!senderDAO.senderExists(senderID)) {
                        System.out.println("Sender ID not found.");
                        break;
                    }

                    List<Receiver> receivers = receiverDAO.getAllReceivers();
                    if (receivers.isEmpty()) {
                        System.out.println("No receivers available. Cannot create shipment.");
                        break;
                    }
                    int receiverID = view.getReceiverID(receivers);
                    if (receiverID == -1) break;
                    if (!receiverDAO.receiverExists(receiverID)) {
                        System.out.println("Receiver ID not found.");
                        break;
                    }

                    List<Bus> buses = busDAO.getAllBuses();
                    if (buses.isEmpty()) {
                        System.out.println("No buses available. Cannot create shipment.");
                        break;
                    }
                    int busID = view.getBusID(buses, routeDAO);
                    if (busID == -1) break;
                    if (!busDAO.busExists(busID)) {
                        System.out.println("Bus ID not found.");
                        break;
                    }

                    List<Branch> branches = branchDAO.getAllBranches();
                    if (branches.isEmpty()) {
                        System.out.println("No branches available. Cannot create shipment.");
                        break;
                    }
                    int branchID = view.getBranchID(branches);
                    if (branchID == -1) break;
                    if (!branchDAO.branchExists(branchID)) {
                        System.out.println("Branch ID not found.");
                        break;
                    }

                    // Create the shipment
                    Shipment newShipment = view.getShipmentDetails(senderID, receiverID, busID, branchID);
                    double cost = newShipment.calculateCost(10.0); // Example rate per kg
                    newShipment.setCost(cost);
                    shipmentDAO.createShipment(newShipment); // Now newShipment should have an ID set

                    // Payment process
                    System.out.print("Enter payment method (e.g., Cash, Credit Card): ");
                    String paymentMethod = scanner.nextLine();

                    Payment payment = new Payment(0, newShipment.getShipmentID(), newShipment.getCost(), paymentMethod, "Pending");
                    payment.processPayment();
                    paymentDAO.createPayment(payment);

                    String receipt = payment.generateReceipt();
                    System.out.println(receipt);

                    break;
                }
                case 'b': {
                    // Update shipment status
                    List<Shipment> shipmentsForUpdate = shipmentDAO.getAllShipments();
                    int shipmentID = view.getShipmentID(shipmentsForUpdate);
                    if (shipmentID == -1) break;
                    if (shipmentDAO.shipmentExists(shipmentID)) {
                        String newStatus = view.getNewShipmentStatus();
                        shipmentDAO.updateShipmentStatus(shipmentID, newStatus);
                    } else {
                        System.out.println("Shipment ID not found.");
                    }
                    break;
                }
                case 'c': {
                    // Show all shipments
                    List<Shipment> shipments = shipmentDAO.getAllShipments();
                    view.displayShipments(shipments);
                    break;
                }
                case 'd':
                    back = true;
                    break;
                case 'e': {
                    // Track Shipment by Tracking Number
                    String tNumber = view.getTrackingNumberInput();
                    Shipment trackedShipment = shipmentDAO.getShipmentByTrackingNumber(tNumber);
                    if (trackedShipment != null) {
                        System.out.println("Shipment Found:");
                        System.out.println("ShipmentID: " + trackedShipment.getShipmentID()
                                + ", SenderID: " + trackedShipment.getSenderID()
                                + ", ReceiverID: " + trackedShipment.getReceiverID()
                                + ", BusID: " + trackedShipment.getBusID()
                                + ", BranchID: " + trackedShipment.getBranchID()
                                + ", Weight: " + trackedShipment.getWeight() + " kg"
                                + ", Dimensions: " + trackedShipment.getDimensions()
                                + ", Status: " + trackedShipment.getStatus()
                                + ", Cost: " + trackedShipment.getCost()
                                + ", Tracking Number: " + trackedShipment.getTrackingNumber());
                    } else {
                        System.out.println("No shipment found with tracking number: " + tNumber);
                    }
                    break;
                }
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
                case 'a': {
                    Branch newBranch = view.getBranchDetails();
                    branchDAO.addBranch(newBranch);
                    break;
                }
                case 'b': {
                    List<Branch> branchesForRemoval = branchDAO.getAllBranches();
                    int branchID = view.getBranchID(branchesForRemoval);
                    if (branchID == -1) break;
                    if (branchDAO.branchExists(branchID)) {
                        branchDAO.removeBranch(branchID);
                    } else {
                        System.out.println("Branch ID not found.");
                    }
                    break;
                }
                case 'c': {
                    List<Branch> branchesForUpdate = branchDAO.getAllBranches();
                    int branchIDForUpdate = view.getBranchID(branchesForUpdate);
                    if (branchIDForUpdate == -1) break;
                    if (branchDAO.branchExists(branchIDForUpdate)) {
                        Branch updatedBranch = view.getUpdatedBranchDetails(branchIDForUpdate);
                        branchDAO.updateBranch(updatedBranch);
                    } else {
                        System.out.println("Branch ID not found.");
                    }
                    break;
                }
                case 'd': {
                    List<Branch> branches = branchDAO.getAllBranches();
                    view.displayBranches(branches);
                    break;
                }
                case 'e':
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void reportManagement() {
        boolean back = false;
        while (!back) {
            System.out.println("--- Reports ---");
            System.out.println("1. Daily Shipment Volume Report");
            System.out.println("2. Monthly Shipment Volume Report");
            System.out.println("3. Daily Revenue Report");
            System.out.println("4. Monthly Revenue Report");
            System.out.println("5. Back to Main Menu");
            System.out.print("Please select an option: ");

            int choice = view.getMainMenuChoice();

            // Current date as reference
            Date now = new Date();
            Date startOfDay = getStartOfDay(now);
            Date endOfDay = getEndOfDay(now);
            Date startOfMonth = getStartOfMonth(now);
            Date endOfMonth = getEndOfMonth(now);

            switch (choice) {
                case 1: {
                    // Daily Shipment Volume
                    ShipmentVolumeReport dailyReport = reportDAO.getShipmentVolumeReport(startOfDay, endOfDay);
                    System.out.println(dailyReport.toString());
                    break;
                }
                case 2: {
                    // Monthly Shipment Volume
                    ShipmentVolumeReport monthlyReport = reportDAO.getShipmentVolumeReport(startOfMonth, endOfMonth);
                    System.out.println(monthlyReport.toString());
                    break;
                }
                case 3: {
                    // Daily Revenue
                    double dailyRevenue = reportDAO.getRevenueReport(startOfDay, endOfDay);
                    System.out.println("Daily Revenue: " + dailyRevenue);
                    break;
                }
                case 4: {
                    // Monthly Revenue
                    double monthlyRevenue = reportDAO.getRevenueReport(startOfMonth, endOfMonth);
                    System.out.println("Monthly Revenue: " + monthlyRevenue);
                    break;
                }
                case 5:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private Date getStartOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    private Date getEndOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    private Date getStartOfDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    private Date getEndOfDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }
}
