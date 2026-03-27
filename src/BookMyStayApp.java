import java.util.*;

/* Service class representing an add-on service */
class Service {

    private String serviceName;
    private double price;

    public Service(String serviceName, double price) {
        this.serviceName = serviceName;
        this.price = price;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getPrice() {
        return price;
    }

    public void displayService() {
        System.out.println(serviceName + " (₹" + price + ")");
    }
}


/* Add-On Service Manager */

class AddOnServiceManager {

    // Map: Reservation ID → List of services
    private Map<String, List<Service>> reservationServices;

    public AddOnServiceManager() {
        reservationServices = new HashMap<>();
    }

    // Add service to reservation
    public void addService(String reservationId, Service service) {

        reservationServices.putIfAbsent(reservationId, new ArrayList<>());

        reservationServices.get(reservationId).add(service);

        System.out.println("Service added: " + service.getServiceName() +
                " for Reservation " + reservationId);
    }

    // Display services for reservation
    public void displayServices(String reservationId) {

        List<Service> services = reservationServices.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No add-on services selected.");
            return;
        }

        System.out.println("\nServices for Reservation " + reservationId);

        for (Service s : services) {
            s.displayService();
        }
    }

    // Calculate total cost of services
    public double calculateTotalServiceCost(String reservationId) {

        double total = 0;

        List<Service> services = reservationServices.get(reservationId);

        if (services != null) {

            for (Service s : services) {
                total += s.getPrice();
            }
        }

        return total;
    }
}


/* Main Application */

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("        Book My Stay App         ");
        System.out.println("   Hotel Booking System v1.0     ");
        System.out.println("=================================");

        // Example reservation IDs (created during booking)
        String reservation1 = "RES-101";
        String reservation2 = "RES-102";

        // Create services
        Service breakfast = new Service("Breakfast", 500);
        Service spa = new Service("Spa Access", 1500);
        Service pickup = new Service("Airport Pickup", 800);

        // Service manager
        AddOnServiceManager manager = new AddOnServiceManager();

        // Guest selects services
        manager.addService(reservation1, breakfast);
        manager.addService(reservation1, spa);

        manager.addService(reservation2, pickup);

        // Display services
        manager.displayServices(reservation1);
        manager.displayServices(reservation2);

        // Calculate extra cost
        System.out.println("\nTotal Add-On Cost for " + reservation1 +
                " : ₹" + manager.calculateTotalServiceCost(reservation1));

        System.out.println("Total Add-On Cost for " + reservation2 +
                " : ₹" + manager.calculateTotalServiceCost(reservation2));
    }
}