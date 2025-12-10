package ticketmachine.controller;

import javafx.scene.control.Toggle;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ticketmachine.model.*;
import ticketmachine.view.*;
import ticketmachine.view.TicketMachineView.Page;
import ticketmachine.util.PdfGenerator;

import java.io.File;

public class TicketController {
    
    private final TicketMachine model;
    private final TicketMachineView view;
    
    public TicketController(TicketMachine model, TicketMachineView view) {
        if (model == null || view == null) {
            throw new IllegalArgumentException("Model and view cannot be null");
        }
        this.model = model;
        this.view = view;
        
        initializeBindings();
        initializeData();
    }
    
    private void initializeBindings() {
        HomeView home = view.getHomeView();
        
        home.getTicketTypeGroup().selectedToggleProperty().addListener(
                (obs, oldVal, newVal) -> handleTicketTypeChange(newVal)
        );
        
        home.getOriginCombo().valueProperty().addListener(
                (obs, oldVal, newVal) -> {
                    if (newVal != null) {
                        model.selectOrigin(newVal);
                        updateDestinations();
                    }
                }
        );
        
        home.getDestinationCombo().valueProperty().addListener(
                (obs, oldVal, newVal) -> {
                    if (newVal != null) {
                        model.selectDestination(newVal);
                    }
                }
        );
        
        home.getPassengerSpinner().valueProperty().addListener(
                (obs, oldVal, newVal) -> {
                    if (newVal != null) {
                        model.setPassengerCount(newVal);
                    }
                }
        );
        
        home.getClassCombo().valueProperty().addListener(
                (obs, oldVal, newVal) -> {
                    if (newVal != null) {
                        model.selectClass(newVal);
                    }
                }
        );
        
        home.getSearchButton().setOnAction(e -> handleSearch());
        
        ResultsView results = view.getResultsView();
        results.getBackButton().setOnAction(e -> navigateTo(Page.HOME));
        
        PaymentView payment = view.getPaymentView();
        payment.getBackButton().setOnAction(e -> navigateTo(Page.RESULTS));
        payment.getPay100Button().setOnAction(e -> handlePayment(100));
        payment.getPay500Button().setOnAction(e -> handlePayment(500));
        payment.getPay1000Button().setOnAction(e -> handlePayment(1000));
        payment.getPay5000Button().setOnAction(e -> handlePayment(5000));
        payment.getConfirmButton().setOnAction(e -> handleConfirmPayment());
        payment.getCancelButton().setOnAction(e -> handleCancelTransaction());
        
        TicketView ticket = view.getTicketView();
        ticket.getBackButton().setOnAction(e -> navigateTo(Page.HOME));
        ticket.getNewBookingButton().setOnAction(e -> handleNewBooking());
        ticket.getDownloadButton().setOnAction(e -> handleDownloadTickets());
    }
    
    private void initializeData() {
        HomeView home = view.getHomeView();
        home.setOrigins(model.getOriginStations());
        home.setTravelClasses(model.getTravelClasses());
        
        if (!model.getOriginStations().isEmpty()) {
            model.selectOrigin(model.getOriginStations().get(0));
        }
        model.selectClass("Economy");
    }
    
    private void handleTicketTypeChange(Toggle newToggle) {
        if (newToggle == null) return;
        
        TicketType type = (TicketType) newToggle.getUserData();
        model.selectTicketType(type);
        updateDestinations();
    }
    
    private void updateDestinations() {
        HomeView home = view.getHomeView();
        TicketType type = model.getSelectedTicketType();
        String origin = model.getSelectedOrigin();
        
        if (type != null) {
            home.setDestinations(model.getDestinationsForType(type), origin);
            home.getDestinationCombo().setValue(null);
            model.selectDestination(null);
        }
    }
    
    private void handleSearch() {
        if (model.getSelectedTicketType() == null) {
            showAlert("Please select a ticket type (Train or Bus)");
            return;
        }
        
        if (model.getSelectedOrigin() == null) {
            showAlert("Please select an origin station");
            return;
        }
        
        if (model.getSelectedDestination() == null) {
            showAlert("Please select a destination");
            return;
        }
        
        ResultsView results = view.getResultsView();
        results.showResults(
                model.getSelectedTicketType(),
                model.getSelectedOrigin(),
                model.getSelectedDestination(),
                model.getPassengerCount(),
                model.getSelectedClass(),
                model.getPricePerTicket()
        );
        
        for (Button bookBtn : results.getBookButtons()) {
            bookBtn.setOnAction(e -> handleBookSelection((Double) bookBtn.getUserData()));
        }
        
        navigateTo(Page.RESULTS);
    }
    
    private void handleBookSelection(double price) {
        PaymentView payment = view.getPaymentView();
        
        String route = model.getSelectedOrigin() + " -> " + model.getSelectedDestination().getName();
        payment.setOrderDetails(route, model.getPassengerCount(), model.getSelectedClass(), price);
        
        model.resetTransaction();
        model.selectTicketType(view.getResultsView().getCurrentType());
        model.selectOrigin(view.getResultsView().getCurrentOrigin());
        model.selectDestination(view.getResultsView().getCurrentDestination());
        model.setPassengerCount(view.getResultsView().getPassengerCount());
        model.selectClass(view.getResultsView().getTravelClass());
        
        payment.updatePaymentDisplay(0, price);
        
        navigateTo(Page.PAYMENT);
    }
    
    private void handlePayment(double amount) {
        model.insertMoney(amount);
        
        double inserted = model.getInsertedAmount();
        double remaining = model.getRemainingAmount();
        
        view.getPaymentView().updatePaymentDisplay(inserted, remaining);
    }
    
    private void handleConfirmPayment() {
        if (!model.canCompletePurchase()) {
            showAlert("Insufficient payment. Please insert more money.");
            return;
        }
        
        try {
            Ticket ticket = model.completePurchase();
            double change = model.getLastChangeAmount();
            
            view.getTicketView().showTicket(ticket, change);
            navigateTo(Page.TICKET);
            
        } catch (Exception e) {
            showAlert("Error completing purchase: " + e.getMessage());
        }
    }
    
    private void handleCancelTransaction() {
        double refund = model.cancelTransaction();
        if (refund > 0) {
            showAlert("Transaction cancelled. " + Destination.formatPKR(refund) + " returned.");
        }
        navigateTo(Page.HOME);
    }
    
    private void handleNewBooking() {
        model.resetTransaction();
        
        HomeView home = view.getHomeView();
        home.getTicketTypeGroup().selectToggle(null);
        home.getOriginCombo().getSelectionModel().clearSelection();
        home.getOriginCombo().setValue(null);
        home.getDestinationCombo().getItems().clear();
        home.getDestinationCombo().setValue(null);
        home.getPassengerSpinner().getValueFactory().setValue(1);
        home.getClassCombo().setValue("Economy");
        
        navigateTo(Page.HOME);
    }
    
    private void handleDownloadTickets() {
        Ticket lastTicket = model.getLastPurchasedTicket();
        if (lastTicket == null) {
            showAlert("No ticket to download.");
            return;
        }
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Boarding Pass PDF");
        fileChooser.setInitialFileName("BoardingPass_" + lastTicket.getTicketId() + ".pdf");
        
        FileChooser.ExtensionFilter pdfFilter = 
                new FileChooser.ExtensionFilter("PDF Files (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(pdfFilter);
        
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        
        Stage stage = (Stage) view.getRoot().getScene().getWindow();
        
        File file = fileChooser.showSaveDialog(stage);
        
        if (file != null) {
            try {
                if (!file.getName().toLowerCase().endsWith(".pdf")) {
                    file = new File(file.getAbsolutePath() + ".pdf");
                }
                
                PdfGenerator.generateTicketPdf(lastTicket, file);
                
                showAlert("Tickets saved successfully!\n\nLocation: " + file.getAbsolutePath());
            } catch (Exception e) {
                showAlert("Error saving tickets: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    private void navigateTo(Page page) {
        view.showPage(page);
    }
    
    private void showAlert(String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                javafx.scene.control.Alert.AlertType.INFORMATION
        );
        alert.setTitle("Ticket Machine");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public TicketMachine getModel() { return model; }
    public TicketMachineView getView() { return view; }
}
