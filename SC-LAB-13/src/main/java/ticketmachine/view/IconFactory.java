package ticketmachine.view;

import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

public class IconFactory {
    
    public static SVGPath createTrainIcon() {
        SVGPath icon = new SVGPath();
        icon.setContent("M12 2C8 2 4 3 4 7v10c0 1.1.9 2 2 2l-1 1v1h2l1.5-1.5h7L17 21h2v-1l-1-1c1.1 0 2-.9 2-2V7c0-4-4-5-8-5zm0 2c3 0 5 .5 5 3H7c0-2.5 2-3 5-3zM6 15V9h12v6H6zm2 0c.83 0 1.5-.67 1.5-1.5S8.83 12 8 12s-1.5.67-1.5 1.5S7.17 15 8 15zm8 0c.83 0 1.5-.67 1.5-1.5S16.83 12 16 12s-1.5.67-1.5 1.5.67 1.5 1.5 1.5z");
        icon.setFill(Color.web("#2E5BFF"));
        return icon;
    }
    
    public static SVGPath createBusIcon() {
        SVGPath icon = new SVGPath();
        icon.setContent("M4 16c0 .88.39 1.67 1 2.22V20c0 .55.45 1 1 1h1c.55 0 1-.45 1-1v-1h8v1c0 .55.45 1 1 1h1c.55 0 1-.45 1-1v-1.78c.61-.55 1-1.34 1-2.22V6c0-3.5-3.58-4-8-4s-8 .5-8 4v10zm3.5 1c-.83 0-1.5-.67-1.5-1.5S6.67 14 7.5 14s1.5.67 1.5 1.5S8.33 17 7.5 17zm9 0c-.83 0-1.5-.67-1.5-1.5s.67-1.5 1.5-1.5 1.5.67 1.5 1.5-.67 1.5-1.5 1.5zm1.5-6H6V6h12v5z");
        icon.setFill(Color.web("#2E5BFF"));
        return icon;
    }
    
    public static SVGPath createLocationIcon() {
        SVGPath icon = new SVGPath();
        icon.setContent("M12 2C8.13 2 5 5.13 5 9c0 5.25 7 13 7 13s7-7.75 7-13c0-3.87-3.13-7-7-7zm0 9.5c-1.38 0-2.5-1.12-2.5-2.5s1.12-2.5 2.5-2.5 2.5 1.12 2.5 2.5-1.12 2.5-2.5 2.5z");
        icon.setFill(Color.web("#10B981"));
        return icon;
    }
    
    public static SVGPath createDestinationIcon() {
        SVGPath icon = new SVGPath();
        icon.setContent("M12 2C8.13 2 5 5.13 5 9c0 5.25 7 13 7 13s7-7.75 7-13c0-3.87-3.13-7-7-7zm0 9.5c-1.38 0-2.5-1.12-2.5-2.5s1.12-2.5 2.5-2.5 2.5 1.12 2.5 2.5-1.12 2.5-2.5 2.5z");
        icon.setFill(Color.web("#EF4444"));
        return icon;
    }
    
    public static SVGPath createCalendarIcon() {
        SVGPath icon = new SVGPath();
        icon.setContent("M19 4h-1V2h-2v2H8V2H6v2H5c-1.11 0-1.99.9-1.99 2L3 20c0 1.1.89 2 2 2h14c1.1 0 2-.9 2-2V6c0-1.1-.9-2-2-2zm0 16H5V10h14v10zM9 14H7v-2h2v2zm4 0h-2v-2h2v2zm4 0h-2v-2h2v2zm-8 4H7v-2h2v2zm4 0h-2v-2h2v2zm4 0h-2v-2h2v2z");
        icon.setFill(Color.web("#6B7280"));
        return icon;
    }
    
    public static SVGPath createPassengerIcon() {
        SVGPath icon = new SVGPath();
        icon.setContent("M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z");
        icon.setFill(Color.web("#6B7280"));
        return icon;
    }
    
    public static SVGPath createSearchIcon() {
        SVGPath icon = new SVGPath();
        icon.setContent("M15.5 14h-.79l-.28-.27C15.41 12.59 16 11.11 16 9.5 16 5.91 13.09 3 9.5 3S3 5.91 3 9.5 5.91 16 9.5 16c1.61 0 3.09-.59 4.23-1.57l.27.28v.79l5 4.99L20.49 19l-4.99-5zm-6 0C7.01 14 5 11.99 5 9.5S7.01 5 9.5 5 14 7.01 14 9.5 11.99 14 9.5 14z");
        icon.setFill(Color.WHITE);
        return icon;
    }
    
    public static SVGPath createTicketIcon() {
        SVGPath icon = new SVGPath();
        icon.setContent("M22 10V6c0-1.11-.9-2-2-2H4c-1.1 0-1.99.89-1.99 2v4c1.1 0 1.99.9 1.99 2s-.89 2-2 2v4c0 1.1.9 2 2 2h16c1.1 0 2-.9 2-2v-4c-1.1 0-2-.9-2-2s.9-2 2-2zm-2-1.46c-1.19.69-2 1.99-2 3.46s.81 2.77 2 3.46V18H4v-2.54c1.19-.69 2-1.99 2-3.46 0-1.48-.8-2.77-1.99-3.46L4 6h16v2.54z");
        icon.setFill(Color.web("#2E5BFF"));
        return icon;
    }
    
    public static SVGPath createHomeIcon() {
        SVGPath icon = new SVGPath();
        icon.setContent("M10 20v-6h4v6h5v-8h3L12 3 2 12h3v8z");
        icon.setFill(Color.web("#2E5BFF"));
        return icon;
    }
    
    public static SVGPath createBackIcon() {
        SVGPath icon = new SVGPath();
        icon.setContent("M20 11H7.83l5.59-5.59L12 4l-8 8 8 8 1.41-1.41L7.83 13H20v-2z");
        icon.setFill(Color.web("#1A1A2E"));
        return icon;
    }
    
    public static SVGPath createCheckIcon() {
        SVGPath icon = new SVGPath();
        icon.setContent("M9 16.17L4.83 12l-1.42 1.41L9 19 21 7l-1.41-1.41z");
        icon.setFill(Color.web("#10B981"));
        return icon;
    }
    
    public static SVGPath createWalletIcon() {
        SVGPath icon = new SVGPath();
        icon.setContent("M21 18v1c0 1.1-.9 2-2 2H5c-1.11 0-2-.9-2-2V5c0-1.1.89-2 2-2h14c1.1 0 2 .9 2 2v1h-9c-1.11 0-2 .9-2 2v8c0 1.1.89 2 2 2h9zm-9-2h10V8H12v8zm4-2.5c-.83 0-1.5-.67-1.5-1.5s.67-1.5 1.5-1.5 1.5.67 1.5 1.5-.67 1.5-1.5 1.5z");
        icon.setFill(Color.web("#2E5BFF"));
        return icon;
    }
    
    public static SVGPath createDownloadIcon() {
        SVGPath icon = new SVGPath();
        icon.setContent("M19 9h-4V3H9v6H5l7 7 7-7zM5 18v2h14v-2H5z");
        icon.setFill(Color.WHITE);
        return icon;
    }
    
    public static SVGPath createSwapIcon() {
        SVGPath icon = new SVGPath();
        icon.setContent("M16 17.01V10h-2v7.01h-3L15 21l4-3.99h-3zM9 3L5 6.99h3V14h2V6.99h3L9 3z");
        icon.setFill(Color.web("#6B7280"));
        return icon;
    }
    
    public static SVGPath createFilterIcon() {
        SVGPath icon = new SVGPath();
        icon.setContent("M10 18h4v-2h-4v2zM3 6v2h18V6H3zm3 7h12v-2H6v2z");
        icon.setFill(Color.web("#6B7280"));
        return icon;
    }
    
    public static SVGPath createArrowRightIcon() {
        SVGPath icon = new SVGPath();
        icon.setContent("M12 4l-1.41 1.41L16.17 11H4v2h12.17l-5.58 5.59L12 20l8-8z");
        icon.setFill(Color.web("#9CA3AF"));
        return icon;
    }
    
    public static void scaleIcon(SVGPath icon, double size) {
        double currentWidth = icon.getBoundsInLocal().getWidth();
        double currentHeight = icon.getBoundsInLocal().getHeight();
        double scaleFactor = size / Math.max(currentWidth, currentHeight);
        icon.setScaleX(scaleFactor);
        icon.setScaleY(scaleFactor);
    }
    
    public static void setIconColor(SVGPath icon, Color color) {
        icon.setFill(color);
    }
}
