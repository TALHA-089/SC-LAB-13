package ticketmachine.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import ticketmachine.model.Destination;
import ticketmachine.model.Ticket;

public class TicketView {
    
    private final VBox root;
    private final Button backButton;
    private final Button downloadButton;
    private final Button newBookingButton;
    private final VBox ticketsContainer;
    private final Label totalPassengersLabel;
    private final Label totalPriceLabel;
    private final Label changeAmountLabel;
    
    public TicketView() {
        this.backButton = new Button();
        this.downloadButton = new Button("Download All Tickets");
        this.newBookingButton = new Button("New Booking");
        this.ticketsContainer = new VBox(16);
        this.totalPassengersLabel = new Label();
        this.totalPriceLabel = new Label();
        this.changeAmountLabel = new Label();
        
        this.root = buildLayout();
    }
    
    private VBox buildLayout() {
        VBox layout = new VBox(0);
        layout.getStyleClass().add("ticket-view");
        
        HBox header = buildHeader();
        HBox summarySection = buildSummarySection();
        
        ScrollPane scrollPane = new ScrollPane(ticketsContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.getStyleClass().add("tickets-scroll");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        
        ticketsContainer.setPadding(new Insets(16, 20, 16, 20));
        ticketsContainer.getStyleClass().add("tickets-container");
        
        VBox buttons = buildButtons();
        
        layout.getChildren().addAll(header, summarySection, scrollPane, buttons);
        return layout;
    }
    
    private HBox buildHeader() {
        HBox header = new HBox(12);
        header.getStyleClass().add("ticket-header");
        header.setPadding(new Insets(16, 20, 16, 20));
        header.setAlignment(Pos.CENTER_LEFT);
        
        backButton.getStyleClass().add("back-btn");
        SVGPath backIcon = IconFactory.createBackIcon();
        IconFactory.scaleIcon(backIcon, 20);
        backButton.setGraphic(backIcon);
        
        Label title = new Label("Boarding Passes");
        title.getStyleClass().add("page-title");
        
        header.getChildren().addAll(backButton, title);
        return header;
    }
    
    private HBox buildSummarySection() {
        HBox section = new HBox(20);
        section.getStyleClass().add("booking-summary-section");
        section.setPadding(new Insets(12, 20, 12, 20));
        section.setAlignment(Pos.CENTER_LEFT);
        
        VBox passengersBox = new VBox(2);
        Label passengersTitle = new Label("Passengers");
        passengersTitle.getStyleClass().add("summary-title-small");
        totalPassengersLabel.getStyleClass().add("summary-value-small");
        passengersBox.getChildren().addAll(passengersTitle, totalPassengersLabel);
        
        VBox priceBox = new VBox(2);
        HBox.setHgrow(priceBox, Priority.ALWAYS);
        Label priceTitle = new Label("Total Fare");
        priceTitle.getStyleClass().add("summary-title-small");
        totalPriceLabel.getStyleClass().add("summary-price-value");
        priceBox.getChildren().addAll(priceTitle, totalPriceLabel);
        
        VBox changeBox = new VBox(2);
        changeBox.setAlignment(Pos.CENTER_RIGHT);
        Label changeTitle = new Label("Change");
        changeTitle.getStyleClass().add("summary-title-small");
        changeAmountLabel.getStyleClass().add("summary-change-value");
        changeBox.getChildren().addAll(changeTitle, changeAmountLabel);
        
        section.getChildren().addAll(passengersBox, priceBox, changeBox);
        return section;
    }
    
    private VBox buildButtons() {
        VBox buttons = new VBox(12);
        buttons.setPadding(new Insets(20));
        
        HBox downloadContent = new HBox(10);
        downloadContent.setAlignment(Pos.CENTER);
        SVGPath downloadIcon = IconFactory.createDownloadIcon();
        IconFactory.scaleIcon(downloadIcon, 18);
        IconFactory.setIconColor(downloadIcon, Color.WHITE);
        Label downloadLabel = new Label("Download All Tickets");
        downloadLabel.setStyle("-fx-text-fill: white;");
        downloadContent.getChildren().addAll(downloadIcon, downloadLabel);
        downloadButton.setGraphic(downloadContent);
        downloadButton.setText("");
        downloadButton.getStyleClass().add("download-btn");
        downloadButton.setMaxWidth(Double.MAX_VALUE);
        
        newBookingButton.getStyleClass().add("new-booking-btn");
        newBookingButton.setMaxWidth(Double.MAX_VALUE);
        
        buttons.getChildren().addAll(downloadButton, newBookingButton);
        return buttons;
    }
    
    public void showTicket(Ticket ticket, double change) {
        ticketsContainer.getChildren().clear();
        
        int passengerCount = ticket.getQuantity();
        
        totalPassengersLabel.setText(passengerCount + " Adult(s)");
        totalPriceLabel.setText(ticket.getFormattedPrice());
        changeAmountLabel.setText(Destination.formatPKR(change));
        
        for (int i = 0; i < passengerCount; i++) {
            VBox ticketCard = createTicketCard(ticket, i + 1, ticket.getSeatNumber(i));
            ticketsContainer.getChildren().add(ticketCard);
        }
    }
    
    private VBox createTicketCard(Ticket ticket, int passengerNumber, String seatNumber) {
        VBox card = new VBox(0);
        card.getStyleClass().add("boarding-pass");
        
        VBox topSection = new VBox(8);
        topSection.getStyleClass().add("ticket-top-section");
        topSection.setPadding(new Insets(20));
        
        Label routeCode = new Label(ticket.getTicketType().getDisplayName() + 
                " (" + ticket.getTicketId() + "-P" + passengerNumber + ")");
        routeCode.getStyleClass().add("ticket-route-code");
        
        Label routeTitle = new Label(ticket.getOrigin() + " → " + ticket.getDestination().getName());
        routeTitle.getStyleClass().add("ticket-route-title");
        
        Label dateLabel = new Label(ticket.getFormattedDate());
        dateLabel.getStyleClass().add("ticket-date");
        
        topSection.getChildren().addAll(routeCode, routeTitle, dateLabel);
        
        HBox divider = createTicketDivider();
        
        HBox timeSection = new HBox();
        timeSection.getStyleClass().add("ticket-time-section");
        timeSection.setPadding(new Insets(16, 20, 16, 20));
        
        VBox depBox = new VBox(4);
        depBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(depBox, Priority.ALWAYS);
        Label depTime = new Label(ticket.getFormattedDepartureTime());
        depTime.getStyleClass().add("ticket-time");
        Label depStation = new Label(ticket.getOrigin());
        depStation.getStyleClass().add("ticket-time-date");
        depBox.getChildren().addAll(depTime, depStation);
        
        VBox durBox = new VBox(4);
        durBox.setAlignment(Pos.CENTER);
        durBox.setPadding(new Insets(0, 16, 0, 16));
        HBox durLine = new HBox(8);
        durLine.setAlignment(Pos.CENTER);
        Line line1 = new Line(0, 0, 25, 0);
        line1.setStroke(Color.web("#E5E7EB"));
        line1.setStrokeWidth(2);
        SVGPath arrow = IconFactory.createArrowRightIcon();
        IconFactory.scaleIcon(arrow, 14);
        Line line2 = new Line(0, 0, 25, 0);
        line2.setStroke(Color.web("#E5E7EB"));
        line2.setStrokeWidth(2);
        durLine.getChildren().addAll(line1, arrow, line2);
        Label durLabel = new Label(ticket.getFormattedDuration());
        durLabel.getStyleClass().add("ticket-duration");
        durBox.getChildren().addAll(durLine, durLabel);
        
        VBox arrBox = new VBox(4);
        arrBox.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(arrBox, Priority.ALWAYS);
        Label arrTime = new Label(ticket.getFormattedArrivalTime());
        arrTime.getStyleClass().add("ticket-time");
        Label arrStation = new Label(ticket.getDestination().getName());
        arrStation.getStyleClass().add("ticket-time-date");
        arrBox.getChildren().addAll(arrTime, arrStation);
        
        timeSection.getChildren().addAll(depBox, durBox, arrBox);
        
        Separator sep = new Separator();
        VBox.setMargin(sep, new Insets(0, 20, 0, 20));
        
        GridPane detailsGrid = new GridPane();
        detailsGrid.getStyleClass().add("ticket-details-grid");
        detailsGrid.setPadding(new Insets(12, 20, 12, 20));
        detailsGrid.setHgap(30);
        detailsGrid.setVgap(10);
        
        detailsGrid.add(createDetailItem("Passenger", "Adult " + passengerNumber), 0, 0);
        detailsGrid.add(createDetailItem("Ticket #", ticket.getTicketId() + "-P" + passengerNumber), 1, 0);
        
        detailsGrid.add(createDetailItem("Class", ticket.getTravelClass()), 0, 1);
        detailsGrid.add(createDetailItem("Seat", seatNumber), 1, 1);
        
        VBox barcodeSection = new VBox(6);
        barcodeSection.getStyleClass().add("barcode-section");
        barcodeSection.setPadding(new Insets(12, 20, 16, 20));
        barcodeSection.setAlignment(Pos.CENTER);
        
        HBox barcode = createBarcode();
        Label barcodeNumber = new Label(ticket.getTicketId().replace("-", "") + 
                String.format("P%d%05d", passengerNumber, (int)(Math.random() * 99999)));
        barcodeNumber.getStyleClass().add("barcode-number");
        
        barcodeSection.getChildren().addAll(barcode, barcodeNumber);
        
        card.getChildren().addAll(topSection, divider, timeSection, sep, detailsGrid, barcodeSection);
        return card;
    }
    
    private HBox createTicketDivider() {
        HBox divider = new HBox();
        divider.getStyleClass().add("ticket-divider");
        divider.setAlignment(Pos.CENTER);
        
        Region leftCutout = new Region();
        leftCutout.getStyleClass().add("cutout-left");
        leftCutout.setPrefSize(14, 14);
        
        Region dashedLine = new Region();
        dashedLine.getStyleClass().add("dashed-line");
        HBox.setHgrow(dashedLine, Priority.ALWAYS);
        dashedLine.setMaxHeight(1);
        
        Region rightCutout = new Region();
        rightCutout.getStyleClass().add("cutout-right");
        rightCutout.setPrefSize(14, 14);
        
        divider.getChildren().addAll(leftCutout, dashedLine, rightCutout);
        return divider;
    }
    
    private VBox createDetailItem(String label, String value) {
        VBox item = new VBox(2);
        
        Label labelNode = new Label(label);
        labelNode.getStyleClass().add("detail-label");
        
        Label valueNode = new Label(value);
        valueNode.getStyleClass().add("detail-value");
        
        item.getChildren().addAll(labelNode, valueNode);
        return item;
    }
    
    private HBox createBarcode() {
        HBox barcode = new HBox(2);
        barcode.setAlignment(Pos.CENTER);
        barcode.getStyleClass().add("barcode");
        
        int[] widths = {2, 1, 3, 1, 2, 1, 1, 3, 2, 1, 3, 1, 2, 1, 1, 2, 3, 1, 2, 1, 
                       1, 3, 1, 2, 1, 3, 2, 1, 1, 2, 1, 3, 1, 2, 1, 1, 3, 2, 1, 2};
        
        for (int width : widths) {
            Rectangle bar = new Rectangle(width, 40, Color.web("#1A1A2E"));
            barcode.getChildren().add(bar);
        }
        
        return barcode;
    }
    
    public VBox getRoot() { return root; }
    public Button getBackButton() { return backButton; }
    public Button getDownloadButton() { return downloadButton; }
    public Button getNewBookingButton() { return newBookingButton; }
}
