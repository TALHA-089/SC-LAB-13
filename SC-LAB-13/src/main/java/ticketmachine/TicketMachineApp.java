package ticketmachine;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ticketmachine.controller.TicketController;
import ticketmachine.model.TicketMachine;
import ticketmachine.view.TicketMachineView;

public class TicketMachineApp extends Application {
    
    private TicketMachine model;
    private TicketMachineView view;
    private TicketController controller;
    
    @Override
    public void start(Stage primaryStage) {
        model = new TicketMachine();
        view = new TicketMachineView();
        controller = new TicketController(model, view);
        
        Scene scene = new Scene(view.getRoot(), 480, 800);
        scene.getStylesheets().add(
                getClass().getResource("/styles/ticket-machine.css").toExternalForm()
        );
        
        primaryStage.setTitle("Ticket Machine - Pakistan Railways & Bus");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.setMinWidth(400);
        primaryStage.setMinHeight(700);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
