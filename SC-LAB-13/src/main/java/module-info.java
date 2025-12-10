module ticketmachine {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.base;
    
    exports ticketmachine;
    exports ticketmachine.model;
    exports ticketmachine.view;
    exports ticketmachine.controller;
    exports ticketmachine.util;
    
    opens ticketmachine to javafx.graphics;
    opens ticketmachine.model to javafx.base;
    opens ticketmachine.view to javafx.graphics;
    opens ticketmachine.controller to javafx.base;
}
