import java.util.*;

/**
 * UseCase6RoomAllocationService
 * Reservation Confirmation & Room Allocation
 *
 * @version 6.1
 */

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

// Booking Queue
class BookingRequestQueue {

    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
    }

    public Reservation getNext() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

// Inventory Service
class RoomInventory {

    private Map<String, Integer> availability;

    public RoomInventory() {
        availability = new HashMap<>();
        availability.put("Single Room", 2);
        availability.put("Double Room", 1);
        availability.put("Suite Room", 1);
    }

    public int getAvailability(String type) {
        return availability.getOrDefault(type, 0);
    }

    public void decrement(String type) {
        availability.put(type, availability.get(type) - 1);
    }
}

// Booking Service
class BookingService {

    private RoomInventory inventory;

    // roomType -> set of allocated room IDs
    private Map<String, Set<String>> allocatedRooms;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
        allocatedRooms = new HashMap<>();
    }

    public void processBookings(BookingRequestQueue queue) {

        while (!queue.isEmpty()) {

            Reservation request = queue.getNext();
            String type = request.getRoomType();

            if (inventory.getAvailability(type) > 0) {

                String roomId = generateRoomId(type);

                allocatedRooms
                        .computeIfAbsent(type, k -> new HashSet<>())
                        .add(roomId);

                inventory.decrement(type);

                System.out.println("Reservation Confirmed");
                System.out.println("Guest  : " + request.getGuestName());
                System.out.println("Room   : " + type);
                System.out.println("RoomID : " + roomId);
                System.out.println();

            } else {

                System.out.println("No rooms available for "
                        + request.getGuestName()
                        + " (" + type + ")");
                System.out.println();
            }
        }
    }

    private String generateRoomId(String type) {

        String prefix = type.substring(0, 1).toUpperCase();

        int count = allocatedRooms
                .getOrDefault(type, new HashSet<>())
                .size() + 1;

        return prefix + "-" + count;
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Book My Stay - Hotel Booking System v6.1\n");

        BookingRequestQueue queue = new BookingRequestQueue();

        queue.addRequest(new Reservation("Mokesh", "Single Room"));
        queue.addRequest(new Reservation("Arun", "Single Room"));
        queue.addRequest(new Reservation("Divya", "Single Room"));
        queue.addRequest(new Reservation("Kiran", "Suite Room"));

        RoomInventory inventory = new RoomInventory();

        BookingService bookingService =
                new BookingService(inventory);

        bookingService.processBookings(queue);
    }
}