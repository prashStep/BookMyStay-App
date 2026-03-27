import java.util.*;

/* Reservation class */
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

/* Inventory Service */
class InventoryService {

    private HashMap<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decreaseAvailability(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " → " + entry.getValue());
        }
    }
}

/* Booking Service */
class BookingService {

    private Queue<Reservation> requestQueue;
    private InventoryService inventory;

    private Set<String> allocatedRoomIds = new HashSet<>();
    private HashMap<String, Set<String>> roomAllocations = new HashMap<>();

    private int roomCounter = 1;

    public BookingService(Queue<Reservation> requestQueue, InventoryService inventory) {
        this.requestQueue = requestQueue;
        this.inventory = inventory;
    }

    public void processBookings() {

        while (!requestQueue.isEmpty()) {

            Reservation reservation = requestQueue.poll();

            String roomType = reservation.getRoomType();
            String guest = reservation.getGuestName();

            System.out.println("\nProcessing booking for " + guest);

            if (inventory.getAvailability(roomType) > 0) {

                // Generate unique room ID
                String roomId = roomType.replace(" ", "").toUpperCase() + "-" + roomCounter++;

                // Ensure uniqueness
                if (!allocatedRoomIds.contains(roomId)) {

                    allocatedRoomIds.add(roomId);

                    roomAllocations.putIfAbsent(roomType, new HashSet<>());
                    roomAllocations.get(roomType).add(roomId);

                    // Update inventory
                    inventory.decreaseAvailability(roomType);

                    System.out.println("Reservation Confirmed!");
                    System.out.println("Guest: " + guest);
                    System.out.println("Room Type: " + roomType);
                    System.out.println("Assigned Room ID: " + roomId);
                }

            } else {

                System.out.println("Reservation Failed – No rooms available for " + roomType);
            }
        }
    }

    public void displayAllocations() {

        System.out.println("\nRoom Allocations:");

        for (Map.Entry<String, Set<String>> entry : roomAllocations.entrySet()) {
            System.out.println(entry.getKey() + " → " + entry.getValue());
        }
    }
}

/* Main Application */

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("        Book My Stay App         ");
        System.out.println("   Hotel Booking System v1.0     ");
        System.out.println("=================================");

        // Create booking request queue
        Queue<Reservation> requestQueue = new LinkedList<>();

        requestQueue.add(new Reservation("Alice", "Single Room"));
        requestQueue.add(new Reservation("Bob", "Double Room"));
        requestQueue.add(new Reservation("Charlie", "Suite Room"));
        requestQueue.add(new Reservation("David", "Suite Room")); // extra request

        // Initialize inventory
        InventoryService inventory = new InventoryService();

        // Booking service
        BookingService bookingService = new BookingService(requestQueue, inventory);

        // Process bookings
        bookingService.processBookings();

        // Show allocations
        bookingService.displayAllocations();

        // Show updated inventory
        inventory.displayInventory();
    }
}