import java.util.LinkedList;
import java.util.Queue;

/**
 * UseCase5BookingRequestQueue
 * Booking Request Queue (First-Come-First-Served)
 *
 * @version 5.0
 */

// Reservation class
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

    public void display() {
        System.out.println("Guest : " + guestName + " | Room : " + roomType);
    }
}

// Booking Queue
class BookingRequestQueue {

    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    // Add booking request
    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
        System.out.println("Request added for " + reservation.getGuestName());
    }

    // View all requests
    public void displayRequests() {
        System.out.println("\nBooking Requests (FIFO Order):");

        for (Reservation r : queue) {
            r.display();
        }
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Book My Stay - Hotel Booking System v5.0\n");

        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        bookingQueue.addRequest(new Reservation("Mokesh", "Single Room"));
        bookingQueue.addRequest(new Reservation("Arun", "Double Room"));
        bookingQueue.addRequest(new Reservation("Divya", "Suite Room"));

        bookingQueue.displayRequests();
    }
}