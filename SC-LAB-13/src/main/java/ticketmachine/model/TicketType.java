package ticketmachine.model;

public enum TicketType {
    TRAIN("Train", 1.0),
    BUS("Bus", 0.8);
    
    private final String displayName;
    private final double fareMultiplier;
    
    TicketType(String displayName, double fareMultiplier) {
        this.displayName = displayName;
        this.fareMultiplier = fareMultiplier;
    }
    
    public String getDisplayName() { return displayName; }
    public double getFareMultiplier() { return fareMultiplier; }
    
    @Override
    public String toString() { return displayName; }
}
