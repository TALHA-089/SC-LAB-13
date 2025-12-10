# JavaFX Ticket Machine Application

A professional ticket booking application for Pakistani train and bus routes with a modern multi-page interface.

## Features

- **Multi-Page Flow**: Home → Search Results → Payment → Boarding Pass
- **Professional UI**: Clean blue/white theme with proper SVG icons
- **Pakistani Routes**: Major cities like Lahore, Karachi, Islamabad, etc.
- **PKR Currency**: All prices in Pakistani Rupees
- **Travel Classes**: Economy, Business, AC Standard, AC Sleeper

## Project Structure

```
src/main/java/ticketmachine/
├── TicketMachineApp.java           # Main application
├── controller/
│   └── TicketController.java       # Page navigation & events
├── model/
│   ├── Destination.java            # City destinations
│   ├── Ticket.java                 # Boarding pass data
│   ├── TicketMachine.java          # Business logic
│   └── TicketType.java             # Train/Bus enum
└── view/
    ├── HomeView.java               # Booking form
    ├── IconFactory.java            # SVG icons
    ├── PaymentView.java            # Payment screen
    ├── ResultsView.java            # Available tickets
    ├── TicketMachineView.java      # Page container
    └── TicketView.java             # Boarding pass

src/main/resources/styles/
└── ticket-machine.css              # Professional styling
```

## Running the Application

### Prerequisites
- Java 11+
- JavaFX SDK

### Command Line
```bash
export JAVAFX_PATH=/path/to/javafx-sdk/lib

# Compile
javac --module-path $JAVAFX_PATH --add-modules javafx.controls \
    -d out src/main/java/module-info.java \
    src/main/java/ticketmachine/*.java \
    src/main/java/ticketmachine/model/*.java \
    src/main/java/ticketmachine/view/*.java \
    src/main/java/ticketmachine/controller/*.java

# Copy resources
cp -r src/main/resources/* out/

# Run
java --module-path $JAVAFX_PATH:out --add-modules javafx.controls \
    -m ticketmachine/ticketmachine.TicketMachineApp
```

## Pricing

```
Price = Rs. 150 (base) + (Distance × Rs. 15/km × Type Multiplier × Class Multiplier)
```

| Type | Multiplier |
|------|------------|
| Train | 1.5× |
| Bus | 1.0× |

| Class | Multiplier |
|-------|------------|
| Economy | 1.0× |
| Business | 1.5× |
| AC Standard | 1.75× |
| AC Sleeper | 2.0× |
