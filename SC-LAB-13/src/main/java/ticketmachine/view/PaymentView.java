package ticketmachine.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.SVGPath;
import ticketmachine.model.Destination;

public class PaymentView {
    
    private final VBox root;
    private final Button backButton;
    private final Label routeLabel;
    private final Label passengersLabel;
    private final Label classLabel;
    private final Label totalLabel;
    private final Button pay100Button;
    private final Button pay500Button;
    private final Button pay1000Button;
    private final Button pay5000Button;
    private final Label insertedLabel;
    private final Label remainingLabel;
    private final Button cancelButton;
    private final Button confirmButton;
    
    public PaymentView() {
        this.backButton = new Button();
        this.routeLabel = new Label();
        this.passengersLabel = new Label();
        this.classLabel = new Label();
        this.totalLabel = new Label();
        this.pay100Button = new Button("Rs. 100");
        this.pay500Button = new Button("Rs. 500");
        this.pay1000Button = new Button("Rs. 1,000");
        this.pay5000Button = new Button("Rs. 5,000");
        this.insertedLabel = new Label("Rs. 0");
        this.remainingLabel = new Label();
        this.cancelButton = new Button("Cancel");
        this.confirmButton = new Button("Confirm Payment");
        
        this.root = buildLayout();
    }
    
    private VBox buildLayout() {
        VBox layout = new VBox(0);
        layout.getStyleClass().add("payment-view");
        
        HBox header = buildHeader();
        ScrollPane scrollPane = new ScrollPane(buildContent());
        scrollPane.setFitToWidth(true);
        scrollPane.getStyleClass().add("results-scroll");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        
        HBox buttons = buildButtons();
        
        layout.getChildren().addAll(header, scrollPane, buttons);
        return layout;
    }
    
    private HBox buildHeader() {
        HBox header = new HBox(12);
        header.getStyleClass().add("payment-header");
        header.setPadding(new Insets(16, 20, 16, 20));
        header.setAlignment(Pos.CENTER_LEFT);
        
        backButton.getStyleClass().add("back-btn");
        SVGPath backIcon = IconFactory.createBackIcon();
        IconFactory.scaleIcon(backIcon, 20);
        backButton.setGraphic(backIcon);
        
        Label title = new Label("Payment");
        title.getStyleClass().add("page-title");
        
        header.getChildren().addAll(backButton, title);
        return header;
    }
    
    private VBox buildContent() {
        VBox content = new VBox(16);
        content.setPadding(new Insets(16, 20, 16, 20));
        
        VBox orderSummary = buildOrderSummary();
        VBox paymentSection = buildPaymentSection();
        
        content.getChildren().addAll(orderSummary, paymentSection);
        return content;
    }
    
    private VBox buildOrderSummary() {
        VBox card = new VBox(12);
        card.getStyleClass().add("summary-card");
        card.setPadding(new Insets(16));
        
        Label header = new Label("Order Summary");
        header.getStyleClass().add("summary-header");
        
        Separator sep = new Separator();
        
        GridPane details = new GridPane();
        details.setHgap(12);
        details.setVgap(8);
        
        details.add(createSummaryLabel("Route:"), 0, 0);
        details.add(routeLabel, 1, 0);
        routeLabel.getStyleClass().add("summary-value");
        
        details.add(createSummaryLabel("Passengers:"), 0, 1);
        details.add(passengersLabel, 1, 1);
        passengersLabel.getStyleClass().add("summary-value");
        
        details.add(createSummaryLabel("Class:"), 0, 2);
        details.add(classLabel, 1, 2);
        classLabel.getStyleClass().add("summary-value");
        
        Separator sep2 = new Separator();
        
        HBox totalRow = new HBox();
        totalRow.setAlignment(Pos.CENTER_LEFT);
        Label totalText = new Label("Total Amount");
        totalText.getStyleClass().add("total-text");
        HBox.setHgrow(totalText, Priority.ALWAYS);
        totalLabel.getStyleClass().add("total-amount");
        totalRow.getChildren().addAll(totalText, totalLabel);
        
        card.getChildren().addAll(header, sep, details, sep2, totalRow);
        return card;
    }
    
    private Label createSummaryLabel(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("summary-label");
        return label;
    }
    
    private VBox buildPaymentSection() {
        VBox section = new VBox(16);
        section.getStyleClass().add("payment-section");
        section.setPadding(new Insets(16));
        
        Label title = new Label("Insert Money");
        title.getStyleClass().add("payment-section-title");
        
        GridPane denominations = new GridPane();
        denominations.setHgap(12);
        denominations.setVgap(12);
        
        pay100Button.getStyleClass().add("payment-denomination-btn");
        pay500Button.getStyleClass().add("payment-denomination-btn");
        pay1000Button.getStyleClass().add("payment-denomination-btn");
        pay5000Button.getStyleClass().add("payment-denomination-btn");
        
        pay100Button.setMaxWidth(Double.MAX_VALUE);
        pay500Button.setMaxWidth(Double.MAX_VALUE);
        pay1000Button.setMaxWidth(Double.MAX_VALUE);
        pay5000Button.setMaxWidth(Double.MAX_VALUE);
        
        GridPane.setHgrow(pay100Button, Priority.ALWAYS);
        GridPane.setHgrow(pay500Button, Priority.ALWAYS);
        GridPane.setHgrow(pay1000Button, Priority.ALWAYS);
        GridPane.setHgrow(pay5000Button, Priority.ALWAYS);
        
        denominations.add(pay100Button, 0, 0);
        denominations.add(pay500Button, 1, 0);
        denominations.add(pay1000Button, 0, 1);
        denominations.add(pay5000Button, 1, 1);
        
        HBox amountDisplay = new HBox(20);
        amountDisplay.getStyleClass().add("amount-display-box");
        amountDisplay.setPadding(new Insets(16));
        amountDisplay.setAlignment(Pos.CENTER);
        
        VBox insertedBox = new VBox(4);
        insertedBox.setAlignment(Pos.CENTER);
        HBox.setHgrow(insertedBox, Priority.ALWAYS);
        Label insertedTitle = new Label("Inserted");
        insertedTitle.getStyleClass().add("amount-label");
        insertedLabel.getStyleClass().add("amount-value-inserted");
        insertedBox.getChildren().addAll(insertedTitle, insertedLabel);
        
        VBox remainingBox = new VBox(4);
        remainingBox.setAlignment(Pos.CENTER);
        HBox.setHgrow(remainingBox, Priority.ALWAYS);
        Label remainingTitle = new Label("Remaining");
        remainingTitle.getStyleClass().add("amount-label");
        remainingLabel.getStyleClass().add("amount-value-remaining");
        remainingBox.getChildren().addAll(remainingTitle, remainingLabel);
        
        amountDisplay.getChildren().addAll(insertedBox, remainingBox);
        
        section.getChildren().addAll(title, denominations, amountDisplay);
        return section;
    }
    
    private HBox buildButtons() {
        HBox buttons = new HBox(12);
        buttons.setPadding(new Insets(16, 20, 20, 20));
        
        cancelButton.getStyleClass().add("cancel-btn");
        HBox.setHgrow(cancelButton, Priority.ALWAYS);
        cancelButton.setMaxWidth(Double.MAX_VALUE);
        
        confirmButton.getStyleClass().add("confirm-btn");
        HBox.setHgrow(confirmButton, Priority.ALWAYS);
        confirmButton.setMaxWidth(Double.MAX_VALUE);
        
        buttons.getChildren().addAll(cancelButton, confirmButton);
        return buttons;
    }
    
    public void setOrderDetails(String route, int passengers, String travelClass, double total) {
        routeLabel.setText(route);
        passengersLabel.setText(passengers + " Adult(s)");
        classLabel.setText(travelClass);
        totalLabel.setText(Destination.formatPKR(total));
    }
    
    public void updatePaymentDisplay(double inserted, double remaining) {
        insertedLabel.setText(Destination.formatPKR(inserted));
        remainingLabel.setText(Destination.formatPKR(remaining));
        confirmButton.setDisable(remaining > 0);
    }
    
    public VBox getRoot() { return root; }
    public Button getBackButton() { return backButton; }
    public Button getPay100Button() { return pay100Button; }
    public Button getPay500Button() { return pay500Button; }
    public Button getPay1000Button() { return pay1000Button; }
    public Button getPay5000Button() { return pay5000Button; }
    public Button getCancelButton() { return cancelButton; }
    public Button getConfirmButton() { return confirmButton; }
}
