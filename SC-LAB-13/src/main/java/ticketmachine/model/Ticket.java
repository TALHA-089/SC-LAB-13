package ticketmachine.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Random;

public class Ticket {
    
    private static int ticketCounter = 0;
    private static final Object COUNTER_LOCK = new Object();
    
    private static final DateTimeFormatter DISPLAY_FORMATTER = 
            DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm");
    
    private static final DateTimeFormatter DATE_ONLY_FORMATTER = 
            DateTimeFormatter.ofPattern("dd MMM yyyy");
    
    private static final DateTimeFormatter TIME_FORMATTER = 
            DateTimeFormatter.ofPattern("HH:mm");
    
    private final String ticketId;
    private final TicketType ticketType;
    private final String origin;
    private final Destination destination;
    private final int quantity;
    private final String travelClass;
    private final double pricePerTicket;
    private final double totalPrice;
    private final LocalDateTime purchaseTime;
    private final LocalDateTime departureTime;
    private final LocalDateTime arrivalTime;
    private final String[] seatNumbers;
    
    public Ticket(TicketType ticketType, String origin, Destination destination, 
                  int quantity, String travelClass) {
        if (ticketType == null) {
            throw new IllegalArgumentException("Ticket type cannot be null");
        }
        if (origin == null || origin.trim().isEmpty()) {
            throw new IllegalArgumentException("Origin cannot be null or empty");
        }
        if (destination == null) {
            throw new IllegalArgumentException("Destination cannot be null");
        }
        if (quantity < 1) {
            throw new IllegalArgumentException("Quantity must be at least 1");
        }
        
        this.ticketType = ticketType;
        this.origin = origin.trim();
        this.destination = destination;
        this.quantity = quantity;
        this.travelClass = travelClass != null ? travelClass : "Economy";
        this.purchaseTime = LocalDateTime.now();
        this.ticketId = generateTicketId();
        this.pricePerTicket = destination.calculatePrice(ticketType);
        this.totalPrice = Math.round(pricePerTicket * quantity);
        
        this.departureTime = generateDepartureTime();
        
        double avgSpeed = ticketType == TicketType.TRAIN ? 80.0 : 60.0;
        int travelMinutes = (int) Math.round((destination.getDistanceKm() / avgSpeed) * 60);
        this.arrivalTime = departureTime.plusMinutes(travelMinutes);
        
        this.seatNumbers = generateSeatNumbers(quantity);
    }
    
    private String generateTicketId() {
        synchronized (COUNTER_LOCK) {
            ticketCounter++;
            return String.format("PK%04d", ticketCounter);
        }
    }
    
    private LocalDateTime generateDepartureTime() {
        LocalDateTime now = LocalDateTime.now();
        int minute = now.getMinute();
        int roundedMinute = (minute < 30) ? 30 : 0;
        LocalDateTime rounded = now.withMinute(roundedMinute).withSecond(0).withNano(0);
        if (roundedMinute == 0) {
            rounded = rounded.plusHours(1);
        }
        return rounded.plusHours(1);
    }
    
    private String[] generateSeatNumbers(int count) {
        String[] seats = new String[count];
        Random rand = new Random();
        for (int i = 0; i < count; i++) {
            int row = rand.nextInt(20) + 1;
            char seat = (char) ('A' + rand.nextInt(4));
            seats[i] = row + "-" + seat;
        }
        return seats;
    }
    
    public String getSeatNumber() {
        return seatNumbers.length > 0 ? seatNumbers[0] : "N/A";
    }
    
    public String[] getAllSeatNumbers() {
        return seatNumbers.clone();
    }
    
    public String getSeatNumber(int passengerIndex) {
        if (passengerIndex >= 0 && passengerIndex < seatNumbers.length) {
            return seatNumbers[passengerIndex];
        }
        return "N/A";
    }
    
    public String getFormattedSeats() {
        return String.join(", ", seatNumbers);
    }
    
    public String getTicketId() { return ticketId; }
    public TicketType getTicketType() { return ticketType; }
    public String getOrigin() { return origin; }
    public Destination getDestination() { return destination; }
    public int getQuantity() { return quantity; }
    public String getTravelClass() { return travelClass; }
    public double getPricePerTicket() { return pricePerTicket; }
    public double getTotalPrice() { return totalPrice; }
    public LocalDateTime getPurchaseTime() { return purchaseTime; }
    public LocalDateTime getDepartureTime() { return departureTime; }
    public LocalDateTime getArrivalTime() { return arrivalTime; }
    
    public String getFormattedDepartureTime() {
        return departureTime.format(TIME_FORMATTER);
    }
    
    public String getFormattedArrivalTime() {
        return arrivalTime.format(TIME_FORMATTER);
    }
    
    public String getFormattedDate() {
        return departureTime.format(DATE_ONLY_FORMATTER);
    }
    
    public String getFormattedDuration() {
        long minutes = java.time.Duration.between(departureTime, arrivalTime).toMinutes();
        long hours = minutes / 60;
        long mins = minutes % 60;
        if (hours > 0) {
            return String.format("%dh %dm", hours, mins);
        }
        return String.format("%dm", mins);
    }
    
    public String getFormattedPrice() {
        return Destination.formatPKR(totalPrice);
    }
    
    public String generateReceipt() {
        StringBuilder receipt = new StringBuilder();
        String border = "=".repeat(48);
        String line = "-".repeat(48);
        
        receipt.append(border).append("\n");
        receipt.append("              BOARDING PASS\n");
        receipt.append(border).append("\n\n");
        
        receipt.append(String.format("  %s (%s)\n", ticketType.getDisplayName().toUpperCase(), ticketId));
        receipt.append(String.format("  %s  -->  %s\n", origin, destination.getName()));
        receipt.append(String.format("  %s\n\n", getFormattedDate()));
        
        receipt.append(line).append("\n");
        receipt.append(String.format("  Departure: %-12s  Arrival: %s\n", 
                getFormattedDepartureTime(), getFormattedArrivalTime()));
        receipt.append(String.format("  Duration:  %s\n", getFormattedDuration()));
        receipt.append(line).append("\n\n");
        
        receipt.append(String.format("  Passenger(s): %d Adult(s)\n", quantity));
        receipt.append(String.format("  Class:        %s\n", travelClass));
        receipt.append(String.format("  Seat(s):      %s\n\n", getFormattedSeats()));
        
        receipt.append(line).append("\n");
        receipt.append(String.format("  TOTAL FARE:   %s\n", getFormattedPrice()));
        receipt.append(border).append("\n");
        receipt.append("         Thank you for traveling with us!\n");
        receipt.append(border);
        
        return receipt.toString();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Ticket ticket = (Ticket) obj;
        return Objects.equals(ticketId, ticket.ticketId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(ticketId);
    }
    
    @Override
    public String toString() {
        return String.format("Ticket[%s, %s, %s -> %s, %s]",
                ticketId, ticketType, origin, destination.getName(), getFormattedPrice());
    }
}
