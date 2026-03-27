import java.util.HashMap;
import java.util.Map;

/* Abstract Room Class */
abstract class Room {

    protected String type;
    protected int beds;
    protected double price;

    public Room(String type, int beds, double price) {
        this.type = type;
        this.beds = beds;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public int getBeds() {
        return beds;
    }

    public double getPrice() {
        return price;
    }
}

/* Concrete Room Classes */

class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 3000);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 5000);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 9000);
    }
}


/* Inventory Class - Holds room availability */

class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {

        inventory = new HashMap<>();

        inventory.put("Single Room", 10);
        inventory.put("Double Room", 5);
        inventory.put("Suite Room", 0);   // Example: Suite unavailable
    }

    // Read-only availability
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}


/* Search Service - Only reads data */

class SearchService {

    private RoomInventory inventory;

    public SearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void searchRooms(Room[] rooms) {

        System.out.println("\nAvailable Rooms:\n");

        for (Room room : rooms) {

            int availability = inventory.getAvailability(room.getType());

            // Defensive check
            if (availability > 0) {

                System.out.println("Room Type: " + room.getType());
                System.out.println("Beds: " + room.getBeds());
                System.out.println("Price per night: ₹" + room.getPrice());
                System.out.println("Available Rooms: " + availability);
                System.out.println("---------------------------");
            }
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

        // Create room objects
        Room[] rooms = {
                new SingleRoom(),
                new DoubleRoom(),
                new SuiteRoom()
        };

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Initialize search service
        SearchService searchService = new SearchService(inventory);

        // Guest searches rooms
        searchService.searchRooms(rooms);
    }
}