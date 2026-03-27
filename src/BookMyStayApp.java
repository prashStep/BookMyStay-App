import java.util.*;

/* Custom Exception */

class InvalidBookingException extends Exception {

    public InvalidBookingException(String message) {
        super(message);
    }
}


/* Reservation Class */

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


/* Booking Validator */

class InvalidBookingValidator {

    private Set<String> validRoomTypes;

    public InvalidBookingValidator() {
        validRoomTypes = new HashSet<>();

        // Valid room types
        validRoomTypes.add("Single");
        validRoomTypes.add("Double");
        validRoomTypes.add("Suite");
    }

    // Validate booking input
    public void validateBooking(String guestName, String roomType, int inventory)
            throws InvalidBookingException {

        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        if (!validRoomTypes.contains(roomType)) {
            throw new InvalidBookingException("Invalid room type selected.");
        }

        if (inventory <= 0) {
            throw new InvalidBookingException("No rooms available for booking.");
        }
    }
}


/* Inventory Service */

class InventoryService {

    private Map<String, Integer> roomInventory;

    public InventoryService() {

        roomInventory = new HashMap<>();

        roomInventory.put("Single", 2);
        roomInventory.put("Double", 2);
        roomInventory.put("Suite", 1);
    }

    public int getAvailableRooms(String roomType) {
        return roomInventory.getOrDefault(roomType, 0);
    }

    public void allocateRoom(String roomType) {
        int current = roomInventory.get(roomType);
        roomInventory.put(roomType, current - 1);
    }
}


/* Booking Service */

class BookingService {

    private InvalidBookingValidator validator;
    private InventoryService inventory;

    public BookingService() {
        validator = new InvalidBookingValidator();
        inventory = new InventoryService();
    }

    public void createBooking(String reservationId, String guestName, String roomType) {

        try {

            int available = inventory.getAvailableRooms(roomType);

            // Validate booking before allocation
            validator.validateBooking(guestName, roomType, available);

            // Allocate room
            inventory.allocateRoom(roomType);

            Reservation reservation = new Reservation(reservationId, guestName, roomType);

            System.out.println("Booking Confirmed:");
            reservation.displayReservation();

        } catch (InvalidBookingException e) {

            System.out.println("Booking Failed: " + e.getMessage());
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

        BookingService bookingService = new BookingService();

        // Valid booking
        bookingService.createBooking("RES-201", "Alice", "Single");

        // Invalid room type
        bookingService.createBooking("RES-202", "Bob", "Luxury");

        // Empty guest name
        bookingService.createBooking("RES-203", "", "Double");

        // Exceeding inventory
        bookingService.createBooking("RES-204", "Charlie", "Suite");
        bookingService.createBooking("RES-205", "David", "Suite");
    }
}