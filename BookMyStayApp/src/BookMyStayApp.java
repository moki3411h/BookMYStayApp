import java.util.*;

/**
 * BookMyStayApp
 * Use Case 9: Error Handling & Validation
 *
 * @version 9.0
 */

// Custom Exception
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Reservation
class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// Inventory
class RoomInventory {

    private Map<String, Integer> availability;

    public RoomInventory() {
        availability = new HashMap<>();
        availability.put("Single Room", 2);
        availability.put("Double Room", 1);
        availability.put("Suite Room", 0);
    }

    public boolean isValidRoom(String type) {
        return availability.containsKey(type);
    }

    public int getAvailability(String type) {
        return availability.getOrDefault(type, 0);
    }

    public void decrement(String type) throws InvalidBookingException {

        int count = getAvailability(type);

        if (count <= 0) {
            throw new InvalidBookingException(
                    "No rooms available for " + type);
        }

        availability.put(type, count - 1);
    }
}

// Validator
class InvalidBookingValidator {

    private RoomInventory inventory;

    public InvalidBookingValidator(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void validate(Reservation reservation)
            throws InvalidBookingException {

        if (reservation.getGuestName() == null
                || reservation.getGuestName().isEmpty()) {
            throw new InvalidBookingException("Guest name is required");
        }

        if (!inventory.isValidRoom(reservation.getRoomType())) {
            throw new InvalidBookingException(
                    "Invalid room type: " + reservation.getRoomType());
        }

        if (inventory.getAvailability(reservation.getRoomType()) <= 0) {
            throw new InvalidBookingException(
                    "Room not available: " + reservation.getRoomType());
        }
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Book My Stay - Hotel Booking System v9.0\n");

        RoomInventory inventory = new RoomInventory();
        InvalidBookingValidator validator =
                new InvalidBookingValidator(inventory);

        List<Reservation> requests = Arrays.asList(
                new Reservation("Mokesh", "Single Room"),
                new Reservation("", "Double Room"),
                new Reservation("Arun", "Suite Room"),
                new Reservation("Divya", "Invalid Room")
        );

        for (Reservation r : requests) {
            try {

                validator.validate(r);
                inventory.decrement(r.getRoomType());

                System.out.println("Booking Confirmed: "
                        + r.getGuestName()
                        + " -> " + r.getRoomType());

            } catch (InvalidBookingException e) {

                System.out.println("Booking Failed: "
                        + e.getMessage());
            }
        }
    }
}