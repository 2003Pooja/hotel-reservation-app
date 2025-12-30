package ui;
import api.AdminResource;
import model.Customer;
import model.IRoom;
import java.util.Collection;
import java.util.Scanner;
public class AdminMenu {
    private static final Scanner scanner = new Scanner(System.in);
    private static final AdminResource adminResource = AdminResource.getInstance();
    public static void displayAdminMenu() {
        boolean keepRunning = true;
        while (keepRunning) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. See all Customers");
            System.out.println("2. See all Rooms");
            System.out.println("3. See all Reservations");
            System.out.println("4. Add a Room");
            System.out.println("5. Back to Main Menu");
            System.out.print("Please select a number: ");
            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    seeAllCustomers();
                    break;
                case "2":
                    seeAllRooms();
                    break;
                case "3":
                    seeAllReservations();
                    break;
                case "4":
                    addARoom();
                    break;
                case "5":
                    keepRunning = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    private static void seeAllCustomers() {
        Collection<Customer> customers = adminResource.getAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
            return;
        }
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }
    private static void seeAllRooms() {
        Collection<IRoom> rooms = adminResource.getAllRooms();

        if (rooms.isEmpty()) {
            System.out.println("No rooms available.");
            return;
        }
        for (IRoom room : rooms) {
            System.out.println(room);
        }
    }
    private static void seeAllReservations() {
        adminResource.displayAllReservations();
    }
    private static void addARoom() {
        boolean addMoreRooms = true;
        while (addMoreRooms) {
            try {
                System.out.print("Enter room number: ");
                String roomNumber = scanner.nextLine();
                System.out.print("Enter price per night: ");
                double price = Double.parseDouble(scanner.nextLine());
                System.out.print("Enter room type (1 for SINGLE, 2 for DOUBLE): ");
                int roomTypeInput = Integer.parseInt(scanner.nextLine());
                model.RoomType roomType = (roomTypeInput == 1)
                        ? model.RoomType.SINGLE
                        : model.RoomType.DOUBLE;
                IRoom room = (price == 0)
                        ? new model.FreeRoom(roomNumber, roomType)
                        : new model.Room(roomNumber, price, roomType);
                adminResource.addRoom(java.util.List.of(room));
                System.out.println("Room added successfully!");
                while (true) {
                    System.out.print("Would you like to add another room? (y/n): ");
                    String response = scanner.nextLine().trim().toLowerCase();
                    if (response.equals("y")) {
                        break; // continue adding rooms
                    } else if (response.equals("n")) {
                        addMoreRooms = false;
                        break;
                    } else {
                        System.out.println("Invalid input. Please enter 'y' or 'n' only.");
                    }
                }
            } catch (Exception e) {
                System.out.println("Error adding room. Please try again.");
                scanner.nextLine(); // clear bad input
            }
        }
    }
}