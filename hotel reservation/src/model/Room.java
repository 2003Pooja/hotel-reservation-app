package model;
public class Room implements IRoom {
    private final String roomNumber;   // cannot change after creation
    private final Double price;
    private final RoomType roomType;
    public Room(String roomNumber, Double price, RoomType roomType) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.roomType = roomType;
    }
    @Override
    public String getRoomNumber() {
        return roomNumber;
    }
    @Override
    public Double getRoomPrice() {
        return price;
    }
    @Override
    public RoomType getRoomType() {
        return roomType;
    }
    @Override
    public boolean isFree() {
        return price == 0.0;
    }
    @Override
    public String toString() {
        return "Room Number: " + roomNumber +
                ", Price: " + price +
                ", Room Type: " + roomType;
    }
    // equals and hashCode
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Room)) return false;
        Room other = (Room) obj;
        return roomNumber.equals(other.roomNumber);
    }
    @Override
    public int hashCode() {
        return roomNumber.hashCode();
    }
}
