import java.util.LinkedList;
import java.util.Queue;

/* Reservation class representing a guest booking request */
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

    public void displayReservation() {
        System.out.println("Guest: " + guestName + " | Requested Room: " + roomType);
    }
}


/* Booking Request Queue */
class BookingRequestQueue {

    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    // Add booking request
    public void addRequest(Reservation reservation) {
        queue.add(reservation);
        System.out.println("Booking request added for " + reservation.getGuestName());
    }

    // Display all queued requests
    public void displayQueue() {

        System.out.println("\nCurrent Booking Request Queue:");

        for (Reservation r : queue) {
            r.displayReservation();
        }
    }

    // Peek next request (without removing)
    public Reservation nextRequest() {
        return queue.peek();
    }
}


/* Main Application */

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("        Book My Stay App         ");
        System.out.println("   Hotel Booking System v1.0     ");
        System.out.println("=================================");

        // Initialize booking request queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Guests submit booking requests
        Reservation r1 = new Reservation("Alice", "Single Room");
        Reservation r2 = new Reservation("Bob", "Double Room");
        Reservation r3 = new Reservation("Charlie", "Suite Room");

        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        // Display queue order
        bookingQueue.displayQueue();

        // Show next request to be processed
        System.out.println("\nNext request to process:");
        Reservation next = bookingQueue.nextRequest();

        if (next != null) {
            next.displayReservation();
        }
    }
}