package ticketmachine.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;
import ticketmachine.model.Destination;
import ticketmachine.model.TicketType;

import java.time.LocalDate;
import java.util.List;

public class HomeView {
    
    private final VBox root;
    private final ToggleGroup ticketTypeGroup;
    private final ComboBox<String> originCombo;
    private final ComboBox<Destination> destinationCombo;
    private final DatePicker datePicker;
    private final Spinner<Integer> passengerSpinner;
    private final ComboBox<String> classCombo;
    private final Button searchButton;
    
    public HomeView() {
        this.ticketTypeGroup = new ToggleGroup();
        this.originCombo = new ComboBox<>();
        this.destinationCombo = new ComboBox<>();
        this.datePicker = new DatePicker(LocalDate.now());
        this.passengerSpinner = new Spinner<>(1, 10, 1);
        this.classCombo = new ComboBox<>();
        this.searchButton = new Button();
        
        this.root = buildLayout();
    }
    
    private VBox buildLayout() {
        VBox layout = new VBox(0);
        layout.getStyleClass().add("home-view");
        
        VBox header = buildHeader();
        VBox bookingCard = buildBookingCard();
        VBox.setVgrow(bookingCard, Priority.ALWAYS);
        
        layout.getChildren().addAll(header, bookingCard);
        return layout;
    }
    
    private VBox buildHeader() {
        VBox header = new VBox(8);
        header.getStyleClass().add("home-header");
        header.setPadding(new Insets(40, 20, 60, 20));
        
        Label greeting = new Label("Welcome to");
        greeting.getStyleClass().add("greeting-text");
        
        Label title = new Label("Pakistan Railways & Bus");
        title.getStyleClass().add("header-title");
        
        header.getChildren().addAll(greeting, title);
        return header;
    }
    
    private VBox buildBookingCard() {
        VBox card = new VBox(16);
        card.getStyleClass().add("booking-card");
        card.setPadding(new Insets(24));
        
        HBox typeSelector = buildTypeSelector();
        VBox locationFields = buildLocationFields();
        HBox datePassengerRow = buildDatePassengerRow();
        VBox classSelection = buildClassSelection();
        Button searchBtn = buildSearchButton();
        
        VBox.setVgrow(locationFields, Priority.SOMETIMES);
        
        card.getChildren().addAll(typeSelector, locationFields, datePassengerRow, classSelection, searchBtn);
        return card;
    }
    
    private HBox buildTypeSelector() {
        HBox container = new HBox(16);
        container.getStyleClass().add("type-selector-container");
        container.setPadding(new Insets(0, 0, 8, 0));
        
        ToggleButton trainBtn = createTypeButton("Train", TicketType.TRAIN, IconFactory.createTrainIcon());
        ToggleButton busBtn = createTypeButton("Bus", TicketType.BUS, IconFactory.createBusIcon());
        
        trainBtn.setToggleGroup(ticketTypeGroup);
        busBtn.setToggleGroup(ticketTypeGroup);
        
        HBox.setHgrow(trainBtn, Priority.ALWAYS);
        HBox.setHgrow(busBtn, Priority.ALWAYS);
        trainBtn.setMaxWidth(Double.MAX_VALUE);
        busBtn.setMaxWidth(Double.MAX_VALUE);
        
        container.getChildren().addAll(trainBtn, busBtn);
        return container;
    }
    
    private ToggleButton createTypeButton(String text, TicketType type, SVGPath icon) {
        ToggleButton btn = new ToggleButton();
        btn.getStyleClass().add("ticket-type-btn");
        btn.setUserData(type);
        
        HBox content = new HBox(8);
        content.setAlignment(Pos.CENTER);
        
        IconFactory.scaleIcon(icon, 20);
        Label label = new Label(text);
        
        content.getChildren().addAll(icon, label);
        btn.setGraphic(content);
        
        return btn;
    }
    
    private VBox buildLocationFields() {
        VBox container = new VBox(0);
        
        HBox originRow = buildLocationRow("From", originCombo, IconFactory.createLocationIcon(), true);
        
        Region connector = new Region();
        connector.getStyleClass().add("route-connector");
        connector.setPrefHeight(24);
        connector.setPrefWidth(2);
        VBox.setMargin(connector, new Insets(0, 0, 0, 11));
        
        HBox destRow = buildLocationRow("To", destinationCombo, IconFactory.createDestinationIcon(), false);
        
        container.getChildren().addAll(originRow, connector, destRow);
        return container;
    }
    
    private <T> HBox buildLocationRow(String label, ComboBox<T> combo, SVGPath icon, boolean isOrigin) {
        HBox row = new HBox(12);
        row.getStyleClass().add("location-row");
        row.setAlignment(Pos.CENTER_LEFT);
        
        StackPane iconContainer = new StackPane();
        iconContainer.getStyleClass().add("location-icon-container");
        IconFactory.scaleIcon(icon, 18);
        iconContainer.getChildren().add(icon);
        
        VBox fieldBox = new VBox(4);
        HBox.setHgrow(fieldBox, Priority.ALWAYS);
        
        Label fieldLabel = new Label(label);
        fieldLabel.getStyleClass().add("field-label");
        
        combo.getStyleClass().add("location-combo");
        combo.setMaxWidth(Double.MAX_VALUE);
        combo.setPromptText("Select " + label.toLowerCase());
        
        fieldBox.getChildren().addAll(fieldLabel, combo);
        
        row.getChildren().addAll(iconContainer, fieldBox);
        return row;
    }
    
    private HBox buildDatePassengerRow() {
        HBox row = new HBox(16);
        row.setPadding(new Insets(8, 0, 0, 0));
        
        VBox dateBox = new VBox(8);
        HBox.setHgrow(dateBox, Priority.ALWAYS);
        
        HBox dateHeader = new HBox(8);
        dateHeader.setAlignment(Pos.CENTER_LEFT);
        SVGPath calIcon = IconFactory.createCalendarIcon();
        IconFactory.scaleIcon(calIcon, 16);
        Label dateLabel = new Label("Date");
        dateLabel.getStyleClass().add("field-label");
        dateHeader.getChildren().addAll(calIcon, dateLabel);
        
        datePicker.getStyleClass().add("date-picker");
        datePicker.setMaxWidth(Double.MAX_VALUE);
        datePicker.setPrefHeight(40);
        
        dateBox.getChildren().addAll(dateHeader, datePicker);
        
        VBox passengerBox = new VBox(8);
        HBox.setHgrow(passengerBox, Priority.ALWAYS);
        
        HBox passengerHeader = new HBox(8);
        passengerHeader.setAlignment(Pos.CENTER_LEFT);
        SVGPath passIcon = IconFactory.createPassengerIcon();
        IconFactory.scaleIcon(passIcon, 16);
        Label passengerLabel = new Label("Passengers");
        passengerLabel.getStyleClass().add("field-label");
        passengerHeader.getChildren().addAll(passIcon, passengerLabel);
        
        passengerSpinner.getStyleClass().add("passenger-spinner");
        passengerSpinner.setMaxWidth(Double.MAX_VALUE);
        passengerSpinner.setPrefHeight(40);
        passengerSpinner.setEditable(false);
        
        passengerBox.getChildren().addAll(passengerHeader, passengerSpinner);
        
        row.getChildren().addAll(dateBox, passengerBox);
        return row;
    }
    
    private VBox buildClassSelection() {
        VBox container = new VBox(8);
        container.setPadding(new Insets(8, 0, 0, 0));
        
        Label label = new Label("Travel Class");
        label.getStyleClass().add("field-label");
        
        classCombo.getStyleClass().add("class-combo");
        classCombo.setMaxWidth(Double.MAX_VALUE);
        classCombo.setValue("Economy");
        
        container.getChildren().addAll(label, classCombo);
        return container;
    }
    
    private Button buildSearchButton() {
        HBox content = new HBox(8);
        content.setAlignment(Pos.CENTER);
        
        SVGPath searchIcon = IconFactory.createSearchIcon();
        IconFactory.scaleIcon(searchIcon, 20);
        
        Label label = new Label("Search Tickets");
        label.getStyleClass().add("search-btn-text");
        
        content.getChildren().addAll(searchIcon, label);
        
        searchButton.setGraphic(content);
        searchButton.getStyleClass().add("search-btn");
        searchButton.setMaxWidth(Double.MAX_VALUE);
        VBox.setMargin(searchButton, new Insets(16, 0, 0, 0));
        
        return searchButton;
    }
    
    public void setOrigins(List<String> origins) {
        originCombo.getItems().clear();
        originCombo.getItems().addAll(origins);
    }
    
    public void setDestinations(List<Destination> destinations) {
        destinationCombo.getItems().clear();
        destinationCombo.getItems().addAll(destinations);
    }
    
    public void setDestinations(List<Destination> destinations, String selectedOrigin) {
        destinationCombo.getItems().clear();
        for (Destination dest : destinations) {
            if (selectedOrigin == null || !dest.getName().equalsIgnoreCase(selectedOrigin)) {
                destinationCombo.getItems().add(dest);
            }
        }
    }
    
    public void setTravelClasses(List<String> classes) {
        classCombo.getItems().clear();
        classCombo.getItems().addAll(classes);
        if (!classes.isEmpty()) {
            classCombo.setValue(classes.get(0));
        }
    }
    
    public VBox getRoot() { return root; }
    public ToggleGroup getTicketTypeGroup() { return ticketTypeGroup; }
    public ComboBox<String> getOriginCombo() { return originCombo; }
    public ComboBox<Destination> getDestinationCombo() { return destinationCombo; }
    public DatePicker getDatePicker() { return datePicker; }
    public Spinner<Integer> getPassengerSpinner() { return passengerSpinner; }
    public ComboBox<String> getClassCombo() { return classCombo; }
    public Button getSearchButton() { return searchButton; }
}
