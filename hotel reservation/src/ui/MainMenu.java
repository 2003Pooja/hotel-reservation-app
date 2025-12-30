package ui;
import api.AdminResource;
import api.HotelResource;
import model.*;
import service.CustomerService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
public class MainMenu {
    private static final HotelResource hotelResource = HotelResource.getInstance();
    private static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        displayMainMenu();
    }
    private static void displayMainMenu() {
        boolean keepRunning = true;
        while (keepRunning) {
            System.out.println("\n--- Hotel Reservation Main Menu ---");
            System.out.println("1. Find and reserve a room");
            System.out.println("2. See my reservations");
            System.out.println("3. Create an account");
            System.out.println("4. Admin");
            System.out.println("5. Exit");
            System.out.print("Please select a number: ");

            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    findAndReserveRoom();
                    break;
                case "2":
                    viewMyReservations();
                    break;
                case "3":
                    createAccount();
                    break;
                case "4":
                    AdminMenu.displayAdminMenu();
                    break;
                case "5":
                    System.out.println("Exiting application...");
                    keepRunning = false;
                    break;
                default:
                    System.out.println("Invalid option. Please enter a number between 1 and 5.");
            }
        }
    }
    private static void createAccount() {
        try {
            System.out.print("Enter Email (name@domain.com): ");
            String email = scanner.nextLine();
            System.out.print("Enter First Name: ");
            String firstName = scanner.nextLine();
            System.out.print("Enter Last Name: ");
            String lastName = scanner.nextLine();
            hotelResource.createACustomer(email, firstName, lastName);
            System.out.println("\nWelcome to Hotel Reservation Application!");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid email format. Please try again.");
        }
    }
    public static void findAndReserveRoom() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date checkInDate = null;
        Date checkOutDate = null;
        Date finalCheckIn;
        Date finalCheckOut;
        IRoom roomToBook;
        Collection<IRoom> availableRooms;
        // Check-In Date
        while (true) {
            try {
                System.out.print("Enter Check-In date (MM/dd/yyyy): ");
                checkInDate = sdf.parse(scanner.nextLine());
                break;
            } catch (ParseException e) {
                System.out.println("Invalid date format.");
            }
        }
        // Check-Out Date
        while (true) {
            try {
                System.out.print("Enter Check-Out date (MM/dd/yyyy): ");
                checkOutDate = sdf.parse(scanner.nextLine());
                if (checkOutDate.after(checkInDate)) break;
                System.out.println("Check-Out must be after Check-In.");
            } catch (ParseException e) {
                System.out.println("Invalid date format.");
            }
        }
        finalCheckIn = checkInDate;
        finalCheckOut = checkOutDate;
        availableRooms = hotelResource.findARoom(checkInDate, checkOutDate);
        if (availableRooms.isEmpty()) {
            System.out.println("No rooms available for selected dates.");
            finalCheckIn = addDays(checkInDate, 7);
            finalCheckOut = addDays(checkOutDate, 7);
            System.out.println("Recommended rooms for dates: " + finalCheckIn + " to " + finalCheckOut);
            availableRooms = hotelResource.findARoom(finalCheckIn, finalCheckOut);
            if (availableRooms.isEmpty()) {
                System.out.println("No rooms available on recommended dates either.");
                return;
            }
        }
        System.out.println("\nAvailable Rooms:");
        for (IRoom room : availableRooms) {
            System.out.println(room);
        }
        if (!askYesNo("Would you like to book a room? (y/n): ")) return;
        if (!askYesNo("Do you have an account with us? (y/n): ")) {
            System.out.println("Please create an account first.");
            return;
        }
        System.out.print("Enter Email (name@domain.com): ");
        String email = scanner.nextLine();
        Customer customer = hotelResource.getCustomer(email);
        if (customer == null) {
            System.out.println("No account found.");
            return;
        }
        System.out.print("What room number would you like to reserve? ");
        String roomNumber = scanner.nextLine();
        roomToBook = hotelResource.getRoom(roomNumber);
        if (roomToBook == null || !availableRooms.contains(roomToBook)) {
            System.out.println("This room is not available for the selected dates.");
            return;
        }
        hotelResource.bookARoom(email, roomToBook, finalCheckIn, finalCheckOut);
        System.out.println("\n--- Reservation Confirmed ---");
        System.out.println(customer.getFirstName() + " " + customer.getLastName());
        System.out.println("Room Number: " + roomToBook.getRoomNumber());
        System.out.println("Price: " + roomToBook.getRoomPrice() + " rs");
        System.out.println("Room Type: " + roomToBook.getRoomType());
        System.out.println("Check-In Date: " + finalCheckIn);
        System.out.println("Check-Out Date: " + finalCheckOut);
    }
    private static boolean askYesNo(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("y")) return true;
            if (input.equals("n")) return false;
            System.out.println("Please enter y or n only.");
        }
    }
    public static void viewMyReservations() {
        System.out.print("Enter your email (name@domain.com): ");
        String email = scanner.nextLine();
        Customer customer = hotelResource.getCustomer(email);
        if (customer == null) {
            System.out.println("No account found.");
            return;
        }
        Collection<Reservation> reservations = hotelResource.getCustomersReservations(email);
        if (reservations.isEmpty()) {
            System.out.println("You have no reservations.");
            return;
        }
        System.out.println("\n--- Your Reservations ---");
        for (Reservation r : reservations) {
            System.out.println(r);
        }
    }
    private static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }
}
//All fixes are saved locally on my computer; I will zip the updated project folder and upload it.
