package ticketmachine.model;

import java.util.Objects;

public class Destination {
    
    private static final double BASE_FARE_PKR = 150.0;
    private static final double RATE_PER_KM_PKR = 15.0;
    
    private final String name;
    private final double distanceKm;
    
    public Destination(String name, double distanceKm) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Destination name cannot be null or empty");
        }
        if (distanceKm <= 0) {
            throw new IllegalArgumentException("Distance must be positive");
        }
        this.name = name.trim();
        this.distanceKm = distanceKm;
    }
    
    public String getName() { return name; }
    public double getDistanceKm() { return distanceKm; }
    
    public double calculatePrice(TicketType ticketType) {
        if (ticketType == null) {
            throw new IllegalArgumentException("Ticket type cannot be null");
        }
        double basePrice = BASE_FARE_PKR + (distanceKm * RATE_PER_KM_PKR);
        return Math.round(basePrice * ticketType.getFareMultiplier());
    }
    
    public static String formatPKR(double amount) {
        return String.format("Rs. %,.0f", amount);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Destination that = (Destination) obj;
        return Double.compare(that.distanceKm, distanceKm) == 0 && Objects.equals(name, that.name);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(name, distanceKm);
    }
    
    @Override
    public String toString() {
        return name + " (" + (int) distanceKm + " km)";
    }
}
