import java.io.*;
import java.util.*;

/* Reservation Class */

class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;

    String reservationId;
    String guestName;
    String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public void display() {
        System.out.println(reservationId + " | " + guestName + " | " + roomType);
    }
}


/* System State (Inventory + Booking History) */

class SystemState implements Serializable {

    private static final long serialVersionUID = 1L;

    Map<String, Integer> inventory;
    List<Reservation> bookingHistory;

    public SystemState(Map<String, Integer> inventory, List<Reservation> bookingHistory) {
        this.inventory = inventory;
        this.bookingHistory = bookingHistory;
    }
}


/* Persistence Service */

class PersistenceService {

    private static final String FILE_NAME = "hotel_state.dat";

    // Save system state
    public void saveState(SystemState state) {

        try {

            FileOutputStream fileOut = new FileOutputStream(FILE_NAME);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(state);

            out.close();
            fileOut.close();

            System.out.println("System state saved successfully.");

        } catch (IOException e) {

            System.out.println("Error saving system state.");
        }
    }

    // Load system state
    public SystemState loadState() {

        try {

            FileInputStream fileIn = new FileInputStream(FILE_NAME);
            ObjectInputStream in = new ObjectInputStream(fileIn);

            SystemState state = (SystemState) in.readObject();

            in.close();
            fileIn.close();

            System.out.println("System state restored successfully.");

            return state;

        } catch (Exception e) {

            System.out.println("No previous state found. Starting fresh.");

            return null;
        }
    }
}


/* Main Application */

public class BookMyStayApp {

    public static void main(String[] args) {

        PersistenceService persistence = new PersistenceService();

        // Try to restore previous state
        SystemState state = persistence.loadState();

        Map<String, Integer> inventory;
        List<Reservation> bookings;

        if (state != null) {

            inventory = state.inventory;
            bookings = state.bookingHistory;

        } else {

            inventory = new HashMap<>();
            inventory.put("Single", 2);
            inventory.put("Double", 2);
            inventory.put("Suite", 1);

            bookings = new ArrayList<>();
        }

        // Simulate booking
        Reservation r1 = new Reservation("RES401", "Alice", "Single");
        Reservation r2 = new Reservation("RES402", "Bob", "Double");

        bookings.add(r1);
        bookings.add(r2);

        inventory.put("Single", inventory.get("Single") - 1);
        inventory.put("Double", inventory.get("Double") - 1);

        // Display current state
        System.out.println("\nCurrent Bookings:");
        for (Reservation r : bookings) {
            r.display();
        }

        System.out.println("\nInventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " → " + entry.getValue());
        }

        // Save state before shutdown
        SystemState newState = new SystemState(inventory, bookings);

        persistence.saveState(newState);
    }
}