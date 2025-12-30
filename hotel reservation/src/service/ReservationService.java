package service;
import model.Customer;
import model.IRoom;
import model.Reservation;
import java.util.*;
import java.util.Date;
import java.util.Calendar;
public class ReservationService {
    private static ReservationService reservationService = new ReservationService();
    private Set<IRoom> rooms = new HashSet<>();
    private Set<Reservation> reservations = new HashSet<>();
    private ReservationService() {
    }
    public static ReservationService getInstance() {
        return reservationService;
    }
    public void addRoom(IRoom room) {
        rooms.add(room);
    }
    public IRoom getARoom(String roomId) {
        for (IRoom room : rooms) {
            if (room.getRoomNumber().equals(roomId)) {
                return room;
            }
        }
        return null;
    }
    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        if (isRoomAlreadyBooked(room, checkInDate, checkOutDate)) {
            throw new IllegalArgumentException("Room is already booked for the selected dates.");
        }
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservations.add(reservation);
        return reservation;
    }
    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        Collection<IRoom> availableRooms = new HashSet<>(rooms);
        // Remove rooms already booked for the date range
        for (Reservation reservation : reservations) {
            boolean datesOverlap = !(checkOutDate.before(reservation.getCheckInDate()) ||
                    checkInDate.after(reservation.getCheckOutDate()));
            if (datesOverlap) {
                availableRooms.remove(reservation.getRoom());
            }
        }
        // If no rooms available, try recommended dates (+7 days)
        if (availableRooms.isEmpty()) {
            System.out.println("No rooms available for your selected dates.");
            availableRooms = findRecommendedRooms(checkInDate, checkOutDate, 7);
        }
        return availableRooms;
    }
    public Collection<Reservation> getCustomersReservation(Customer customer) {
        Collection<Reservation> customerReservations = new HashSet<>();
        for (Reservation reservation : reservations) {
            if (reservation.getCustomer().equals(customer)) {
                customerReservations.add(reservation);
            }
        }
        return customerReservations;
    }
    public void printAllReservation() {
        for (Reservation reservation : reservations) {
            System.out.println(reservation);
        }
    }
    public Collection<IRoom> getAllRooms() {
        return rooms;
    }
    // default access modifier (no public/private/protected keyword)
    Collection<IRoom> findRecommendedRooms(Date checkInDate, Date checkOutDate, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(checkInDate);
        cal.add(Calendar.DATE, days);
        Date newCheckIn = cal.getTime();
        cal.setTime(checkOutDate);
        cal.add(Calendar.DATE, days);
        Date newCheckOut = cal.getTime();
        Collection<IRoom> recommendedRooms = new HashSet<>(rooms);
        for (Reservation reservation : reservations) {
            boolean datesOverlap = !(newCheckOut.before(reservation.getCheckInDate()) ||
                    newCheckIn.after(reservation.getCheckOutDate()));
            if (datesOverlap) {
                recommendedRooms.remove(reservation.getRoom());
            }
        }
        System.out.println("Recommended rooms for dates: " + newCheckIn + " to " + newCheckOut);
        return recommendedRooms;
    }
    public Collection<IRoom> findRoomsWithFlexibleDates(
            Date checkInDate,
            Date checkOutDate,
            int days
    ) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(checkInDate);
        cal.add(Calendar.DATE, days);
        Date newCheckIn = cal.getTime();
        cal.setTime(checkOutDate);
        cal.add(Calendar.DATE, days);
        Date newCheckOut = cal.getTime();
        return findRooms(newCheckIn, newCheckOut);
    }
    private boolean isRoomAlreadyBooked(IRoom room, Date checkIn, Date checkOut) {
        for (Reservation reservation : reservations) {
            if (reservation.getRoom().equals(room)) {
                boolean overlap =
                        !(checkOut.before(reservation.getCheckInDate())
                                || checkIn.after(reservation.getCheckOutDate()));

                if (overlap) {
                    return true;
                }
            }
        }
        return false;
    }

}
