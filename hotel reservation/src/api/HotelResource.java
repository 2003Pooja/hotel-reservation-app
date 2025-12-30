package api;
import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;
import java.util.Collection;
import java.util.Date;
public class HotelResource {
    private static HotelResource hotelResource = new HotelResource();
    private CustomerService customerService = CustomerService.getInstance();
    private ReservationService reservationService = ReservationService.getInstance();
    private HotelResource() {
    }
    public static HotelResource getInstance() {
        return hotelResource;
    }
    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }
    public void createACustomer(String email, String firstName, String lastName) {
        customerService.addCustomer(email, firstName, lastName);
    }
    public IRoom getRoom(String roomNumber) {
        return reservationService.getARoom(roomNumber);
    }
    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
        Customer customer = getCustomer(customerEmail);
        if (customer == null) {
            throw new IllegalArgumentException("Customer does not exist: " + customerEmail);
        }
        return reservationService.reserveARoom(customer, room, checkInDate, checkOutDate);
    }
    public Collection<Reservation> getCustomersReservations(String customerEmail) {
        Customer customer = getCustomer(customerEmail);
        if (customer == null) {
            throw new IllegalArgumentException("Customer does not exist: " + customerEmail);
        }
        return reservationService.getCustomersReservation(customer);
    }
    public Collection<IRoom> findARoom(Date checkInDate, Date checkOutDate) {
        return reservationService.findRooms(checkInDate, checkOutDate);
    }
    public Collection<IRoom> findARoomWithFlexibleDates(Date checkIn, Date checkOut, int days) {
        return reservationService.findRoomsWithFlexibleDates(checkIn, checkOut, days);
    }
}
