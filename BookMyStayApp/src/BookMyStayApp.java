import java.util.HashMap;
import java.util.Map;

/**
 * UseCase3InventorySetup
 * Centralized Room Inventory Management using HashMap
 *
 * @version 3.1
 */

// Inventory Class
class RoomInventory {

    private Map<String, Integer> roomAvailability;

    // Constructor
    public RoomInventory() {
        roomAvailability = new HashMap<>();

        roomAvailability.put("Single Room", 5);
        roomAvailability.put("Double Room", 3);
        roomAvailability.put("Suite Room", 2);
    }

    // Get availability
    public int getAvailability(String roomType) {
        return roomAvailability.getOrDefault(roomType, 0);
    }

    // Update availability
    public void updateAvailability(String roomType, int count) {
        roomAvailability.put(roomType, count);
    }

    // Display inventory
    public void displayInventory() {
        System.out.println("Room Inventory:");

        for (Map.Entry<String, Integer> entry : roomAvailability.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Book My Stay - Hotel Booking System v3.1\n");

        RoomInventory inventory = new RoomInventory();

        inventory.displayInventory();

        System.out.println("\nSingle Room Available: "
                + inventory.getAvailability("Single Room"));

        inventory.updateAvailability("Suite Room", 1);

        System.out.println("\nUpdated Inventory:");
        inventory.displayInventory();
    }
}