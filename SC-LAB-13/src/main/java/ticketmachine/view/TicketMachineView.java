package ticketmachine.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;

public class TicketMachineView {
    
    public enum Page { HOME, RESULTS, PAYMENT, TICKET }
    
    private final StackPane root;
    private final HomeView homeView;
    private final ResultsView resultsView;
    private final PaymentView paymentView;
    private final TicketView ticketView;
    
    public TicketMachineView() {
        this.homeView = new HomeView();
        this.resultsView = new ResultsView();
        this.paymentView = new PaymentView();
        this.ticketView = new TicketView();
        
        this.root = new StackPane();
        root.getStyleClass().add("root-container");
        
        root.getChildren().addAll(
                homeView.getRoot(),
                resultsView.getRoot(),
                paymentView.getRoot(),
                ticketView.getRoot()
        );
        
        showPage(Page.HOME);
    }
    
    public void showPage(Page page) {
        homeView.getRoot().setVisible(page == Page.HOME);
        resultsView.getRoot().setVisible(page == Page.RESULTS);
        paymentView.getRoot().setVisible(page == Page.PAYMENT);
        ticketView.getRoot().setVisible(page == Page.TICKET);
    }
    
    public StackPane getRoot() { return root; }
    public HomeView getHomeView() { return homeView; }
    public ResultsView getResultsView() { return resultsView; }
    public PaymentView getPaymentView() { return paymentView; }
    public TicketView getTicketView() { return ticketView; }
}
