package ticketmachine.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TicketMachine {
    
    private final List<Destination> trainDestinations;
    private final List<Destination> busDestinations;
    private final List<String> originStations;
    private final List<Ticket> transactionHistory;
    
    private TicketType selectedTicketType;
    private String selectedOrigin;
    private Destination selectedDestination;
    private int passengerCount;
    private String selectedClass;
    private double insertedAmount;
    private double lastChangeAmount;
    
    public TicketMachine() {
        this.originStations = initializeOriginStations();
        this.trainDestinations = initializeTrainDestinations();
        this.busDestinations = initializeBusDestinations();
        this.transactionHistory = new ArrayList<>();
        resetTransaction();
    }
    
    private List<String> initializeOriginStations() {
        List<String> stations = new ArrayList<>();
        stations.add("Lahore");
        stations.add("Karachi");
        stations.add("Islamabad");
        stations.add("Rawalpindi");
        stations.add("Peshawar");
        stations.add("Quetta");
        stations.add("Multan");
        stations.add("Faisalabad");
        return stations;
    }
    
    private List<Destination> initializeTrainDestinations() {
        List<Destination> destinations = new ArrayList<>();
        destinations.add(new Destination("Karachi", 1211));
        destinations.add(new Destination("Islamabad", 375));
        destinations.add(new Destination("Rawalpindi", 368));
        destinations.add(new Destination("Peshawar", 480));
        destinations.add(new Destination("Quetta", 870));
        destinations.add(new Destination("Multan", 346));
        destinations.add(new Destination("Faisalabad", 128));
        destinations.add(new Destination("Hyderabad", 1055));
        destinations.add(new Destination("Sukkur", 680));
        destinations.add(new Destination("Bahawalpur", 420));
        return Collections.unmodifiableList(destinations);
    }
    
    private List<Destination> initializeBusDestinations() {
        List<Destination> destinations = new ArrayList<>();
        destinations.add(new Destination("Multan", 346));
        destinations.add(new Destination("Faisalabad", 128));
        destinations.add(new Destination("Sialkot", 125));
        destinations.add(new Destination("Gujranwala", 68));
        destinations.add(new Destination("Sargodha", 186));
        destinations.add(new Destination("Sahiwal", 175));
        destinations.add(new Destination("Gujrat", 142));
        destinations.add(new Destination("Jhelum", 195));
        destinations.add(new Destination("Sheikhupura", 38));
        destinations.add(new Destination("Kasur", 55));
        return Collections.unmodifiableList(destinations);
    }
    
    public List<String> getOriginStations() {
        return Collections.unmodifiableList(originStations);
    }
    
    public List<Destination> getTrainDestinations() {
        return trainDestinations;
    }
    
    public List<Destination> getBusDestinations() {
        return busDestinations;
    }
    
    public List<Destination> getDestinationsForType(TicketType type) {
        if (type == null) return Collections.emptyList();
        return type == TicketType.TRAIN ? trainDestinations : busDestinations;
    }
    
    public List<String> getTravelClasses() {
        List<String> classes = new ArrayList<>();
        classes.add("Economy");
        classes.add("Business");
        classes.add("AC Standard");
        classes.add("AC Sleeper");
        return classes;
    }
    
    public void selectTicketType(TicketType type) {
        if (this.selectedTicketType != type) {
            this.selectedTicketType = type;
            this.selectedDestination = null;
        }
    }
    
    public void selectOrigin(String origin) {
        this.selectedOrigin = origin;
    }
    
    public void selectDestination(Destination destination) {
        this.selectedDestination = destination;
    }
    
    public void setPassengerCount(int count) {
        if (count < 1 || count > 10) {
            throw new IllegalArgumentException("Passenger count must be between 1 and 10");
        }
        this.passengerCount = count;
    }
    
    public void selectClass(String travelClass) {
        this.selectedClass = travelClass;
    }
    
    public TicketType getSelectedTicketType() { return selectedTicketType; }
    public String getSelectedOrigin() { return selectedOrigin; }
    public Destination getSelectedDestination() { return selectedDestination; }
    public int getPassengerCount() { return passengerCount; }
    public String getSelectedClass() { return selectedClass; }
    public double getInsertedAmount() { return insertedAmount; }
    public double getLastChangeAmount() { return lastChangeAmount; }
    
    public double getPricePerTicket() {
        if (selectedTicketType == null || selectedDestination == null) {
            return 0.0;
        }
        double basePrice = selectedDestination.calculatePrice(selectedTicketType);
        double classMultiplier = getClassMultiplier();
        return Math.round(basePrice * classMultiplier);
    }
    
    private double getClassMultiplier() {
        if (selectedClass == null) return 1.0;
        switch (selectedClass) {
            case "Business": return 1.5;
            case "AC Standard": return 1.75;
            case "AC Sleeper": return 2.0;
            default: return 1.0;
        }
    }
    
    public double getTotalPrice() {
        return Math.round(getPricePerTicket() * passengerCount);
    }
    
    public void insertMoney(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        this.insertedAmount = Math.round(this.insertedAmount + amount);
    }
    
    public double getRemainingAmount() {
        double remaining = getTotalPrice() - insertedAmount;
        return remaining > 0 ? Math.round(remaining) : 0;
    }
    
    public boolean canCompletePurchase() {
        return selectedTicketType != null 
                && selectedOrigin != null 
                && selectedDestination != null 
                && passengerCount >= 1 
                && insertedAmount >= getTotalPrice();
    }
    
    public boolean hasValidSelections() {
        return selectedTicketType != null 
                && selectedOrigin != null 
                && selectedDestination != null;
    }
    
    public Ticket completePurchase() {
        if (!canCompletePurchase()) {
            throw new IllegalStateException("Cannot complete purchase");
        }
        
        Ticket ticket = new Ticket(
                selectedTicketType, 
                selectedOrigin, 
                selectedDestination, 
                passengerCount,
                selectedClass
        );
        
        this.lastChangeAmount = Math.round(insertedAmount - ticket.getTotalPrice());
        transactionHistory.add(ticket);
        resetTransaction();
        
        return ticket;
    }
    
    public double cancelTransaction() {
        double refund = insertedAmount;
        resetTransaction();
        return refund;
    }
    
    public void resetTransaction() {
        this.selectedTicketType = null;
        this.selectedOrigin = null;
        this.selectedDestination = null;
        this.passengerCount = 1;
        this.selectedClass = "Economy";
        this.insertedAmount = 0.0;
    }
    
    public List<Ticket> getTransactionHistory() {
        return Collections.unmodifiableList(transactionHistory);
    }
    
    public Ticket getLastPurchasedTicket() {
        if (transactionHistory.isEmpty()) {
            return null;
        }
        return transactionHistory.get(transactionHistory.size() - 1);
    }
}
