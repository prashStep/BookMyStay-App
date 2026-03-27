import java.util.*;

/* Reservation class */

class Reservation {

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
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

    public void displayReservation() {
        System.out.println("Reservation ID: " + reservationId +
                " | Guest: " + guestName +
                " | Room Type: " + roomType);
    }
}


/* Booking History */

class BookingHistory {

    private List<Reservation> history;

    public BookingHistory() {
        history = new ArrayList<>();
    }

    // Store confirmed reservation
    public void addReservation(Reservation reservation) {
        history.add(reservation);
        System.out.println("Reservation stored in history: " + reservation.getReservationId());
    }

    // Retrieve all reservations
    public List<Reservation> getReservations() {
        return history;
    }
}


/* Booking Report Service */

class BookingReportService {

    public void generateReport(List<Reservation> reservations) {

        System.out.println("\n===== Booking History Report =====");

        for (Reservation r : reservations) {
            r.displayReservation();
        }

        System.out.println("\nTotal Bookings: " + reservations.size());

        // Room type summary
        Map<String, Integer> roomSummary = new HashMap<>();

        for (Reservation r : reservations) {
            roomSummary.put(r.getRoomType(),
                    roomSummary.getOrDefault(r.getRoomType(), 0) + 1);
        }

        System.out.println("\nRoom Type Summary:");

        for (Map.Entry<String, Integer> entry : roomSummary.entrySet()) {
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

        // Initialize booking history
        BookingHistory history = new BookingHistory();

        // Simulate confirmed reservations
        Reservation r1 = new Reservation("RES-101", "Alice", "Single Room");
        Reservation r2 = new Reservation("RES-102", "Bob", "Double Room");
        Reservation r3 = new Reservation("RES-103", "Charlie", "Suite Room");

        // Store reservations
        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);

        // Generate report
        BookingReportService reportService = new BookingReportService();
        reportService.generateReport(history.getReservations());
    }
}