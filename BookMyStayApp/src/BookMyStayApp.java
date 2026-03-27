import java.util.*;

/**
 * UseCase7AddOnServiceSelection
 * Add-On Service Selection
 *
 * @version 7.0
 */

// Add-On Service
class AddOnService {

    private String name;
    private double price;

    public AddOnService(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

// Add-On Service Manager
class AddOnServiceManager {

    // reservationId -> list of services
    private Map<String, List<AddOnService>> serviceMap;

    public AddOnServiceManager() {
        serviceMap = new HashMap<>();
    }

    public void addService(String reservationId, AddOnService service) {

        serviceMap
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);
    }

    public void displayServices(String reservationId) {

        List<AddOnService> services = serviceMap.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No add-on services for " + reservationId);
            return;
        }

        System.out.println("Add-On Services for " + reservationId);

        for (AddOnService s : services) {
            System.out.println(s.getName() + " : ₹" + s.getPrice());
        }
    }

    public double calculateTotal(String reservationId) {

        double total = 0;

        List<AddOnService> services = serviceMap.get(reservationId);

        if (services != null) {
            for (AddOnService s : services) {
                total += s.getPrice();
            }
        }

        return total;
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Book My Stay - Hotel Booking System v7.0\n");

        AddOnServiceManager manager = new AddOnServiceManager();

        String reservationId = "S-1";

        manager.addService(reservationId,
                new AddOnService("Breakfast", 500));

        manager.addService(reservationId,
                new AddOnService("Airport Pickup", 1200));

        manager.addService(reservationId,
                new AddOnService("Extra Bed", 800));

        manager.displayServices(reservationId);

        double total = manager.calculateTotal(reservationId);

        System.out.println("\nTotal Add-On Cost: ₹" + total);
    }
}