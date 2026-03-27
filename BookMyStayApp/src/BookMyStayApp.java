import java.util.*;

/**
 * UseCase8BookingHistoryReport
 * Booking History & Reporting
 *
 * @version 8.0
 */

// Reservation
class Reservation {

    private String guestName;
    private String roomType;
    private String roomId;

    public Reservation(String guestName, String roomType, String roomId) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    public void display() {
        System.out.println(
                "Guest: " + guestName +
                        " | Room: " + roomType +
                        " | RoomID: " + roomId);
    }
}

// Booking History
class BookingHistory {

    private List<Reservation> history;

    public BookingHistory() {
        history = new ArrayList<>();
    }

    public void add(Reservation reservation) {
        history.add(reservation);
    }

    public List<Reservation> getAll() {
        return history;
    }
}

// Report Service
class BookingReportService {

    private BookingHistory history;

    public BookingReportService(BookingHistory history) {
        this.history = history;
    }

    public void showAllBookings() {

        System.out.println("\n--- Booking History ---");

        for (Reservation r : history.getAll()) {
            r.display();
        }
    }

    public void showSummary() {

        Map<String, Integer> summary = new HashMap<>();

        for (Reservation r : history.getAll()) {
            summary.put(
                    r.getRoomType(),
                    summary.getOrDefault(r.getRoomType(), 0) + 1
            );
        }

        System.out.println("\n--- Booking Summary ---");

        for (Map.Entry<String, Integer> entry : summary.entrySet()) {
            System.out.println(
                    entry.getKey() + " : " + entry.getValue()
            );
        }
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Book My Stay - Hotel Booking System v8.0");

        BookingHistory history = new BookingHistory();

        // confirmed bookings
        history.add(new Reservation("Mokesh", "Single Room", "S-1"));
        history.add(new Reservation("Arun", "Double Room", "D-1"));
        history.add(new Reservation("Divya", "Suite Room", "S-1"));
        history.add(new Reservation("Kiran", "Single Room", "S-2"));

        BookingReportService report =
                new BookingReportService(history);

        report.showAllBookings();
        report.showSummary();
    }
}