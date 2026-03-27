import java.util.*;

/**
 * BookMyStayApp
 * Use Case 10: Booking Cancellation & Inventory Rollback
 *
 * @version 10.0
 */

// Reservation
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
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

    private Map<String, Integer> availability = new HashMap<>();

    public RoomInventory() {
        availability.put("Single Room", 1);
        availability.put("Double Room", 1);
        availability.put("Suite Room", 1);
    }

    public void increment(String type) {
        availability.put(type, availability.get(type) + 1);
    }

    public void decrement(String type) {
        availability.put(type, availability.get(type) - 1);
    }

    public int getAvailability(String type) {
        return availability.getOrDefault(type, 0);
    }

    public void display() {
        System.out.println("Inventory:");
        for (Map.Entry<String, Integer> e : availability.entrySet()) {
            System.out.println(e.getKey() + " -> " + e.getValue());
        }
    }
}

// Booking History
class BookingHistory {

    private Map<String, Reservation> bookings = new HashMap<>();

    public void add(Reservation r) {
        bookings.put(r.getReservationId(), r);
    }

    public Reservation get(String id) {
        return bookings.get(id);
    }

    public void remove(String id) {
        bookings.remove(id);
    }

    public boolean exists(String id) {
        return bookings.containsKey(id);
    }
}

// Cancellation Service
class CancellationService {

    private RoomInventory inventory;
    private BookingHistory history;
    private Stack<String> rollbackStack = new Stack<>();

    public CancellationService(RoomInventory inventory, BookingHistory history) {
        this.inventory = inventory;
        this.history = history;
    }

    public void cancel(String reservationId) {

        if (!history.exists(reservationId)) {
            System.out.println("Cancellation Failed: Reservation not found -> " + reservationId);
            return;
        }

        Reservation r = history.get(reservationId);

        rollbackStack.push(reservationId);

        inventory.increment(r.getRoomType());

        history.remove(reservationId);

        System.out.println("Cancelled: "
                + r.getGuestName()
                + " | " + r.getRoomType()
                + " | " + reservationId);
    }

    public void showRollbackStack() {
        System.out.println("\nRollback Stack: " + rollbackStack);
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Book My Stay - Hotel Booking System v10.0\n");

        RoomInventory inventory = new RoomInventory();
        BookingHistory history = new BookingHistory();

        history.add(new Reservation("S-1", "Mokesh", "Single Room"));
        history.add(new Reservation("D-1", "Arun", "Double Room"));

        inventory.decrement("Single Room");
        inventory.decrement("Double Room");

        CancellationService service =
                new CancellationService(inventory, history);

        service.cancel("S-1");
        service.cancel("X-1");

        System.out.println();
        inventory.display();

        service.showRollbackStack();
    }
}