import java.io.*;
import java.util.*;

/**
 * BookMyStayApp
 * Use Case 12: Data Persistence & System Recovery
 *
 * @version 12.0
 */

// Reservation (Serializable)
class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;

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

    public String toString() {
        return reservationId + " | " + guestName + " | " + roomType;
    }
}

// System State (Serializable)
class SystemState implements Serializable {

    private static final long serialVersionUID = 1L;

    Map<String, Integer> inventory;
    List<Reservation> bookings;

    public SystemState(Map<String, Integer> inventory,
                       List<Reservation> bookings) {
        this.inventory = inventory;
        this.bookings = bookings;
    }
}

// Persistence Service
class PersistenceService {

    private static final String FILE_NAME = "bookmyStay.dat";

    public void save(SystemState state) {

        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            out.writeObject(state);
            System.out.println("System state saved.");

        } catch (IOException e) {
            System.out.println("Save failed: " + e.getMessage());
        }
    }

    public SystemState load() {

        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            SystemState state = (SystemState) in.readObject();
            System.out.println("System state restored.");
            return state;

        } catch (Exception e) {
            System.out.println("No previous data found. Starting fresh.");
            return null;
        }
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Book My Stay - Hotel Booking System v12.0\n");

        PersistenceService persistence = new PersistenceService();

        SystemState state = persistence.load();

        Map<String, Integer> inventory;
        List<Reservation> bookings;

        if (state != null) {
            inventory = state.inventory;
            bookings = state.bookings;
        } else {
            inventory = new HashMap<>();
            inventory.put("Single Room", 2);
            inventory.put("Double Room", 1);
            inventory.put("Suite Room", 1);

            bookings = new ArrayList<>();
        }

        // simulate new booking
        Reservation r = new Reservation("S-1", "Mokesh", "Single Room");
        bookings.add(r);
        inventory.put("Single Room", inventory.get("Single Room") - 1);

        System.out.println("Current Bookings:");
        for (Reservation res : bookings) {
            System.out.println(res);
        }

        System.out.println("\nInventory:");
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " -> " + e.getValue());
        }

        // save before shutdown
        persistence.save(new SystemState(inventory, bookings));
    }
}