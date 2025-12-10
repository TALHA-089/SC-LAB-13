package ticketmachine.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.SVGPath;
import ticketmachine.model.Destination;
import ticketmachine.model.TicketType;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ResultsView {
    
    private final VBox root;
    private final Button backButton;
    private final Label routeLabel;
    private final Label detailsLabel;
    private final VBox resultsContainer;
    private final List<Button> bookButtons;
    
    private TicketType currentType;
    private String currentOrigin;
    private Destination currentDestination;
    private int passengerCount;
    private String travelClass;
    
    public ResultsView() {
        this.backButton = new Button();
        this.routeLabel = new Label();
        this.detailsLabel = new Label();
        this.resultsContainer = new VBox(12);
        this.bookButtons = new ArrayList<>();
        
        this.root = buildLayout();
    }
    
    private VBox buildLayout() {
        VBox layout = new VBox(0);
        layout.getStyleClass().add("results-view");
        
        VBox header = buildHeader();
        HBox filterTabs = buildFilterTabs();
        ScrollPane scrollPane = new ScrollPane(resultsContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.getStyleClass().add("results-scroll");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        
        resultsContainer.setPadding(new Insets(16, 20, 16, 20));
        resultsContainer.getStyleClass().add("results-container");
        
        layout.getChildren().addAll(header, filterTabs, scrollPane);
        return layout;
    }
    
    private VBox buildHeader() {
        VBox header = new VBox(8);
        header.getStyleClass().add("results-header");
        header.setPadding(new Insets(16, 20, 20, 20));
        
        HBox topRow = new HBox(12);
        topRow.setAlignment(Pos.CENTER_LEFT);
        
        backButton.getStyleClass().add("back-btn");
        SVGPath backIcon = IconFactory.createBackIcon();
        IconFactory.scaleIcon(backIcon, 20);
        backButton.setGraphic(backIcon);
        
        routeLabel.getStyleClass().add("route-title");
        HBox.setHgrow(routeLabel, Priority.ALWAYS);
        
        topRow.getChildren().addAll(backButton, routeLabel);
        
        detailsLabel.getStyleClass().add("route-details");
        
        header.getChildren().addAll(topRow, detailsLabel);
        return header;
    }
    
    private HBox buildFilterTabs() {
        HBox tabs = new HBox(8);
        tabs.getStyleClass().add("filter-tabs");
        tabs.setPadding(new Insets(12, 20, 12, 20));
        
        Button allBtn = new Button("All Times");
        allBtn.getStyleClass().addAll("filter-tab", "filter-tab-primary");
        
        Button morningBtn = new Button("Morning");
        morningBtn.getStyleClass().add("filter-tab");
        
        Button eveningBtn = new Button("Evening");
        eveningBtn.getStyleClass().add("filter-tab");
        
        tabs.getChildren().addAll(allBtn, morningBtn, eveningBtn);
        return tabs;
    }
    
    public void showResults(TicketType type, String origin, Destination destination, 
                           int passengers, String travelClass, double basePrice) {
        this.currentType = type;
        this.currentOrigin = origin;
        this.currentDestination = destination;
        this.passengerCount = passengers;
        this.travelClass = travelClass;
        
        routeLabel.setText(origin + " → " + destination.getName());
        detailsLabel.setText(passengers + " Adult(s) • " + travelClass + " Class");
        
        resultsContainer.getChildren().clear();
        bookButtons.clear();
        
        double[] multipliers = { 1.0, 1.1, 0.95, 1.15 };
        String[] times = { "08:30", "11:00", "14:30", "18:00" };
        
        for (int i = 0; i < 4; i++) {
            double price = Math.round(basePrice * multipliers[i]);
            VBox card = createResultCard(type, origin, destination, times[i], 
                    travelClass, price, i < 2);
            resultsContainer.getChildren().add(card);
        }
    }
    
    private VBox createResultCard(TicketType type, String origin, Destination destination,
                                  String departureTime, String travelClass, double price, boolean available) {
        VBox card = new VBox(12);
        card.getStyleClass().add("result-card");
        card.setPadding(new Insets(16));
        
        HBox topRow = new HBox();
        topRow.setAlignment(Pos.CENTER_LEFT);
        
        VBox routeInfo = new VBox(4);
        HBox.setHgrow(routeInfo, Priority.ALWAYS);
        
        Label codeLabel = new Label(type.getDisplayName() + " Express");
        codeLabel.getStyleClass().add("route-code");
        
        Label classLabel = new Label(travelClass + " Class");
        classLabel.getStyleClass().add("class-label");
        
        routeInfo.getChildren().addAll(codeLabel, classLabel);
        
        VBox priceBox = new VBox(2);
        priceBox.setAlignment(Pos.CENTER_RIGHT);
        
        Label priceLabel = new Label(Destination.formatPKR(price));
        priceLabel.getStyleClass().add("price-label");
        
        Label availLabel = new Label(available ? "Available" : "Limited Seats");
        availLabel.getStyleClass().add(available ? "available-label" : "limited-label");
        
        priceBox.getChildren().addAll(priceLabel, availLabel);
        
        topRow.getChildren().addAll(routeInfo, priceBox);
        
        Separator sep = new Separator();
        
        HBox timeRow = new HBox();
        timeRow.setAlignment(Pos.CENTER);
        timeRow.setPadding(new Insets(8, 0, 8, 0));
        
        VBox depBox = new VBox(4);
        depBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(depBox, Priority.ALWAYS);
        
        Label depTime = new Label(departureTime);
        depTime.getStyleClass().add("time-label");
        
        Label depStation = new Label(origin);
        depStation.getStyleClass().add("station-label");
        
        depBox.getChildren().addAll(depTime, depStation);
        
        VBox durBox = new VBox(4);
        durBox.setAlignment(Pos.CENTER);
        durBox.setPadding(new Insets(0, 16, 0, 16));
        
        HBox durLine = new HBox(4);
        durLine.setAlignment(Pos.CENTER);
        Line line1 = new Line(0, 0, 30, 0);
        line1.setStroke(Color.web("#E5E7EB"));
        line1.setStrokeWidth(2);
        SVGPath arrow = IconFactory.createArrowRightIcon();
        IconFactory.scaleIcon(arrow, 14);
        Line line2 = new Line(0, 0, 30, 0);
        line2.setStroke(Color.web("#E5E7EB"));
        line2.setStrokeWidth(2);
        durLine.getChildren().addAll(line1, arrow, line2);
        
        double avgSpeed = type == TicketType.TRAIN ? 80.0 : 60.0;
        int travelMins = (int) Math.round((destination.getDistanceKm() / avgSpeed) * 60);
        String duration = (travelMins / 60) + "h " + (travelMins % 60) + "m";
        Label durLabel = new Label(duration);
        durLabel.getStyleClass().add("duration-label");
        
        durBox.getChildren().addAll(durLine, durLabel);
        
        LocalTime depT = LocalTime.parse(departureTime);
        LocalTime arrT = depT.plusMinutes(travelMins);
        
        VBox arrBox = new VBox(4);
        arrBox.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(arrBox, Priority.ALWAYS);
        
        Label arrTime = new Label(arrT.toString());
        arrTime.getStyleClass().add("time-label");
        
        Label arrStation = new Label(destination.getName());
        arrStation.getStyleClass().add("station-label");
        
        arrBox.getChildren().addAll(arrTime, arrStation);
        
        timeRow.getChildren().addAll(depBox, durBox, arrBox);
        
        Button bookBtn = new Button("Book Now");
        bookBtn.getStyleClass().add("book-btn");
        bookBtn.setMaxWidth(Double.MAX_VALUE);
        bookBtn.setUserData(price * passengerCount);
        bookButtons.add(bookBtn);
        
        card.getChildren().addAll(topRow, sep, timeRow, bookBtn);
        return card;
    }
    
    public VBox getRoot() { return root; }
    public Button getBackButton() { return backButton; }
    public List<Button> getBookButtons() { return bookButtons; }
    public TicketType getCurrentType() { return currentType; }
    public String getCurrentOrigin() { return currentOrigin; }
    public Destination getCurrentDestination() { return currentDestination; }
    public int getPassengerCount() { return passengerCount; }
    public String getTravelClass() { return travelClass; }
}
