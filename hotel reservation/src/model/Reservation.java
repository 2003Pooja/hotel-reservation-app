package model;
import java.util.Date;
import java.util.Objects;
public class Reservation {
    private final Customer customer;
    private final IRoom room;
    private final Date checkInDate;
    private final Date checkOutDate;
    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }
    public Customer getCustomer() {
        return customer;
    }
    public IRoom getRoom() {
        return room;
    }
    public Date getCheckInDate() {
        return checkInDate;
    }
    public Date getCheckOutDate() {
        return checkOutDate;
    }
    @Override
    public String toString() {
        return "\n--- Reservation Confirmed ---\n" +
                customer.getFullName() + "\n" +
                "Room Number: " + room.getRoomNumber() + "\n" +
                "Price: " + room.getRoomPrice() + " rs\n" +
                "Room Type: " + room.getRoomType() + "\n" +
                "Check-In Date: " + checkInDate + "\n" +
                "Check-Out Date: " + checkOutDate;
    }
    //  equals and hashCode
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Reservation)) return false;
        Reservation other = (Reservation) obj;
        return customer.equals(other.customer) &&
                room.equals(other.room) &&
                checkInDate.equals(other.checkInDate) &&
                checkOutDate.equals(other.checkOutDate);
    }
    @Override
    public int hashCode() {
        return Objects.hash(customer, room, checkInDate, checkOutDate);
    }
}
