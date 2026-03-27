/**
 * Book My Stay Application
 * Demonstrates basic room types using inheritance and abstraction.
 *
 * @author Prashanta
 * @version 1.0
 */

// Abstract Room class
abstract class Room {

    private int beds;
    private int size;
    private double price;

    public Room(int beds, int size, double price) {
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public int getBeds() {
        return beds;
    }

    public int getSize() {
        return size;
    }

    public double getPrice() {
        return price;
    }

    // Abstract method to display room type
    public abstract String getRoomType();
}


// Single Room Class
class SingleRoom extends Room {

    public SingleRoom() {
        super(1, 200, 3000);
    }

    public String getRoomType() {
        return "Single Room";
    }
}


// Double Room Class
class DoubleRoom extends Room {

    public DoubleRoom() {
        super(2, 350, 5000);
    }

    public String getRoomType() {
        return "Double Room";
    }
}


// Suite Room Class
class SuiteRoom extends Room {

    public SuiteRoom() {
        super(3, 600, 9000);
    }

    public String getRoomType() {
        return "Suite Room";
    }
}


// Main Application
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("        Book My Stay App         ");
        System.out.println("   Hotel Booking System v1.0     ");
        System.out.println("=================================");

        // Create Room Objects (Polymorphism)
        Room single = new SingleRoom();
        Room doub = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Static availability variables
        int singleAvailable = 10;
        int doubleAvailable = 7;
        int suiteAvailable = 3;

        // Display Room Details
        displayRoom(single, singleAvailable);
        displayRoom(doub, doubleAvailable);
        displayRoom(suite, suiteAvailable);
    }

    // Method to display room information
    public static void displayRoom(Room room, int availability) {

        System.out.println("\nRoom Type: " + room.getRoomType());
        System.out.println("Beds: " + room.getBeds());
        System.out.println("Room Size: " + room.getSize() + " sq ft");
        System.out.println("Price per night: ₹" + room.getPrice());
        System.out.println("Available Rooms: " + availability);
    }
}