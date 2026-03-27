import java.util.*;

/* Reservation Class */

class Reservation {

    private String reservationId;
    private String guestName;
    private String roomType;
    private String roomId;

    public Reservation(String reservationId, String guestName, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
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

    public String getRoomId() {
        return roomId;
    }

    public void displayReservation() {
        System.out.println("Reservation ID: " + reservationId +
                " | Guest: " + guestName +
                " | Room Type: " + roomType +
                " | Room ID: " + roomId);
    }
}


/* Inventory Service */

class InventoryService {

    private Map<String, Integer> inventory;

    public InventoryService() {

        inventory = new HashMap<>();

        inventory.put("Single", 2);
        inventory.put("Double", 2);
        inventory.put("Suite", 1);
    }

    public void decreaseInventory(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void increaseInventory(String roomType) {
        inventory.put(roomType, inventory.get(roomType) + 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " → " + entry.getValue());
        }
    }
}


/* Booking History */

class BookingHistory {

    private Map<String, Reservation> reservations;

    public BookingHistory() {
        reservations = new HashMap<>();
    }

    public void addReservation(Reservation r) {
        reservations.put(r.getReservationId(), r);
    }

    public Reservation getReservation(String id) {
        return reservations.get(id);
    }

    public void removeReservation(String id) {
        reservations.remove(id);
    }
}


/* Cancellation Service */

class CancellationService {

    private BookingHistory history;
    private InventoryService inventory;
    private Stack<String> rollbackStack;

    public CancellationService(BookingHistory history, InventoryService inventory) {
        this.history = history;
        this.inventory = inventory;
        rollbackStack = new Stack<>();
    }

    public void cancelBooking(String reservationId) {

        Reservation reservation = history.getReservation(reservationId);

        if (reservation == null) {
            System.out.println("Cancellation Failed: Reservation does not exist.");
            return;
        }

        // Push released room ID to stack
        rollbackStack.push(reservation.getRoomId());

        // Restore inventory
        inventory.increaseInventory(reservation.getRoomType());

        // Remove reservation from history
        history.removeReservation(reservationId);

        System.out.println("\nBooking Cancelled Successfully.");
        System.out.println("Released Room ID: " + rollbackStack.peek());
    }
}


/* Main Application */

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("        Book My Stay App         ");
        System.out.println("   Hotel Booking System v1.0     ");
        System.out.println("=================================");

        InventoryService inventory = new InventoryService();
        BookingHistory history = new BookingHistory();

        // Simulated confirmed bookings
        Reservation r1 = new Reservation("RES-301", "Alice", "Single", "S-101");
        Reservation r2 = new Reservation("RES-302", "Bob", "Double", "D-201");

        history.addReservation(r1);
        history.addReservation(r2);

        CancellationService cancelService = new CancellationService(history, inventory);

        inventory.displayInventory();

        // Guest cancels booking
        cancelService.cancelBooking("RES-301");

        inventory.displayInventory();

        // Attempt invalid cancellation
        cancelService.cancelBooking("RES-999");
    }
}