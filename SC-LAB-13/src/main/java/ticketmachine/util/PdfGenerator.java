package ticketmachine.util;

import ticketmachine.model.Ticket;
import ticketmachine.model.Destination;

import java.io.*;

public class PdfGenerator {
    
    private static final int PAGE_WIDTH = 400;
    private static final int PAGE_HEIGHT = 600;
    private static final int MARGIN = 30;
    
    public static void generateTicketPdf(Ticket ticket, File file) throws IOException {
        int passengerCount = ticket.getQuantity();
        int fontObjNum = 3 + passengerCount * 2;
        int totalObjects = fontObjNum + 1;
        
        StringBuilder pdfContent = new StringBuilder();
        int[] objectOffsets = new int[totalObjects + 1];
        int currentOffset = 0;
        
        String header = "%PDF-1.4\n%\u00E2\u00E3\u00CF\u00D3\n";
        currentOffset = header.length();
        
        objectOffsets[1] = currentOffset;
        String obj1 = "1 0 obj\n<< /Type /Catalog /Pages 2 0 R >>\nendobj\n";
        pdfContent.append(obj1);
        currentOffset += obj1.length();
        
        objectOffsets[2] = currentOffset;
        StringBuilder pagesKids = new StringBuilder();
        for (int i = 0; i < passengerCount; i++) {
            pagesKids.append((3 + i * 2)).append(" 0 R ");
        }
        String obj2 = "2 0 obj\n<< /Type /Pages /Kids [" + pagesKids + "] /Count " + passengerCount + " >>\nendobj\n";
        pdfContent.append(obj2);
        currentOffset += obj2.length();
        
        int nextObj = 3;
        for (int p = 0; p < passengerCount; p++) {
            int pageObj = nextObj;
            int contentObj = nextObj + 1;
            
            objectOffsets[pageObj] = currentOffset;
            String pageObjStr = pageObj + " 0 obj\n<< /Type /Page /Parent 2 0 R " +
                    "/MediaBox [0 0 " + PAGE_WIDTH + " " + PAGE_HEIGHT + "] " +
                    "/Contents " + contentObj + " 0 R " +
                    "/Resources << /Font << /F1 " + fontObjNum + " 0 R >> >> >>\nendobj\n";
            pdfContent.append(pageObjStr);
            currentOffset += pageObjStr.length();
            
            objectOffsets[contentObj] = currentOffset;
            String content = generatePageContent(ticket, p + 1, ticket.getSeatNumber(p));
            String contentObjStr = contentObj + " 0 obj\n<< /Length " + content.length() + " >>\nstream\n" +
                    content + "endstream\nendobj\n";
            pdfContent.append(contentObjStr);
            currentOffset += contentObjStr.length();
            
            nextObj += 2;
        }
        
        objectOffsets[fontObjNum] = currentOffset;
        String fontObj = fontObjNum + " 0 obj\n<< /Type /Font /Subtype /Type1 /BaseFont /Helvetica >>\nendobj\n";
        pdfContent.append(fontObj);
        currentOffset += fontObj.length();
        
        int xrefOffset = currentOffset;
        StringBuilder xref = new StringBuilder();
        xref.append("xref\n");
        xref.append("0 ").append(totalObjects).append("\n");
        xref.append("0000000000 65535 f \n");
        for (int i = 1; i < totalObjects; i++) {
            xref.append(String.format("%010d 00000 n \n", objectOffsets[i]));
        }
        
        xref.append("trailer\n");
        xref.append("<< /Size ").append(totalObjects);
        xref.append(" /Root 1 0 R >>\n");
        xref.append("startxref\n");
        xref.append(xrefOffset).append("\n");
        xref.append("%%EOF\n");
        
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(header.getBytes("ISO-8859-1"));
            fos.write(pdfContent.toString().getBytes("ISO-8859-1"));
            fos.write(xref.toString().getBytes("ISO-8859-1"));
        }
    }
    
    private static String generatePageContent(Ticket ticket, int passengerNum, String seatNumber) {
        StringBuilder content = new StringBuilder();
        
        int y = PAGE_HEIGHT - MARGIN;
        int centerX = PAGE_WIDTH / 2;
        
        content.append("q\n");
        content.append("0.180 0.357 1.000 rg\n");
        content.append("0 ").append(PAGE_HEIGHT - 150).append(" ").append(PAGE_WIDTH).append(" 150 re f\n");
        content.append("Q\n");
        
        content.append("BT\n");
        content.append("1 1 1 rg\n");
        content.append("/F1 12 Tf\n");
        content.append(MARGIN).append(" ").append(y - 30).append(" Td\n");
        content.append("(").append(escapeText(ticket.getTicketType().getDisplayName())).append(" - ");
        content.append(escapeText(ticket.getTicketId())).append("-P").append(passengerNum).append(") Tj\n");
        content.append("ET\n");
        
        content.append("BT\n");
        content.append("/F1 18 Tf\n");
        content.append(MARGIN).append(" ").append(y - 60).append(" Td\n");
        String route = ticket.getOrigin() + " -> " + ticket.getDestination().getName();
        content.append("(").append(escapeText(route)).append(") Tj\n");
        content.append("ET\n");
        
        content.append("BT\n");
        content.append("/F1 10 Tf\n");
        content.append(MARGIN).append(" ").append(y - 80).append(" Td\n");
        content.append("(").append(escapeText(ticket.getFormattedDate())).append(") Tj\n");
        content.append("ET\n");
        
        y = PAGE_HEIGHT - 180;
        content.append("q\n");
        content.append("0.9 0.9 0.9 RG\n");
        content.append(MARGIN).append(" ").append(y).append(" m ");
        content.append(PAGE_WIDTH - MARGIN).append(" ").append(y).append(" l S\n");
        content.append("Q\n");
        
        y -= 30;
        content.append("BT\n");
        content.append("0 0 0 rg\n");
        content.append("/F1 20 Tf\n");
        content.append(MARGIN).append(" ").append(y).append(" Td\n");
        content.append("(").append(escapeText(ticket.getFormattedDepartureTime())).append(") Tj\n");
        content.append("ET\n");
        
        content.append("BT\n");
        content.append("/F1 20 Tf\n");
        content.append(PAGE_WIDTH - MARGIN - 50).append(" ").append(y).append(" Td\n");
        content.append("(").append(escapeText(ticket.getFormattedArrivalTime())).append(") Tj\n");
        content.append("ET\n");
        
        content.append("BT\n");
        content.append("0.4 0.4 0.4 rg\n");
        content.append("/F1 10 Tf\n");
        content.append(centerX - 30).append(" ").append(y).append(" Td\n");
        content.append("(").append(escapeText(ticket.getFormattedDuration())).append(") Tj\n");
        content.append("ET\n");
        
        y -= 20;
        content.append("BT\n");
        content.append("/F1 10 Tf\n");
        content.append(MARGIN).append(" ").append(y).append(" Td\n");
        content.append("(").append(escapeText(ticket.getOrigin())).append(") Tj\n");
        content.append("ET\n");
        
        content.append("BT\n");
        content.append(PAGE_WIDTH - MARGIN - 60).append(" ").append(y).append(" Td\n");
        content.append("(").append(escapeText(ticket.getDestination().getName())).append(") Tj\n");
        content.append("ET\n");
        
        y -= 50;
        addDetailLabel(content, "Passenger", MARGIN, y);
        addDetailValue(content, "Adult " + passengerNum, MARGIN, y - 15);
        
        addDetailLabel(content, "Ticket Number", centerX, y);
        addDetailValue(content, ticket.getTicketId() + "-P" + passengerNum, centerX, y - 15);
        
        y -= 50;
        addDetailLabel(content, "Class", MARGIN, y);
        addDetailValue(content, ticket.getTravelClass(), MARGIN, y - 15);
        
        addDetailLabel(content, "Seat", centerX, y);
        addDetailValue(content, seatNumber, centerX, y - 15);
        
        y -= 60;
        addDetailLabel(content, "Fare per Ticket", MARGIN, y);
        
        content.append("BT\n");
        content.append("0.180 0.357 1.000 rg\n");
        content.append("/F1 16 Tf\n");
        content.append(MARGIN).append(" ").append(y - 18).append(" Td\n");
        content.append("(").append(escapeText(Destination.formatPKR(ticket.getPricePerTicket()))).append(") Tj\n");
        content.append("ET\n");
        
        y -= 80;
        content.append("q\n");
        content.append("0.1 0.1 0.1 rg\n");
        int barcodeX = centerX - 80;
        for (int i = 0; i < 40; i++) {
            int barWidth = (i % 3 == 0) ? 3 : (i % 2 == 0) ? 2 : 1;
            content.append(barcodeX).append(" ").append(y).append(" ").append(barWidth).append(" 40 re f\n");
            barcodeX += barWidth + 2;
        }
        content.append("Q\n");
        
        content.append("BT\n");
        content.append("0.4 0.4 0.4 rg\n");
        content.append("/F1 10 Tf\n");
        content.append(centerX - 50).append(" ").append(y - 15).append(" Td\n");
        String barcodeNum = ticket.getTicketId() + "P" + passengerNum + String.format("%05d", (int)(Math.random() * 99999));
        content.append("(").append(escapeText(barcodeNum)).append(") Tj\n");
        content.append("ET\n");
        
        y = 30;
        content.append("BT\n");
        content.append("0.6 0.6 0.6 rg\n");
        content.append("/F1 8 Tf\n");
        content.append(centerX - 60).append(" ").append(y).append(" Td\n");
        content.append("(Thank you for traveling with us!) Tj\n");
        content.append("ET\n");
        
        return content.toString();
    }
    
    private static void addDetailLabel(StringBuilder content, String text, int x, int y) {
        content.append("BT\n");
        content.append("0.6 0.6 0.6 rg\n");
        content.append("/F1 9 Tf\n");
        content.append(x).append(" ").append(y).append(" Td\n");
        content.append("(").append(escapeText(text)).append(") Tj\n");
        content.append("ET\n");
    }
    
    private static void addDetailValue(StringBuilder content, String text, int x, int y) {
        content.append("BT\n");
        content.append("0 0 0 rg\n");
        content.append("/F1 12 Tf\n");
        content.append(x).append(" ").append(y).append(" Td\n");
        content.append("(").append(escapeText(text)).append(") Tj\n");
        content.append("ET\n");
    }
    
    private static String escapeText(String text) {
        if (text == null) return "";
        return text.replace("\\", "\\\\")
                   .replace("(", "\\(")
                   .replace(")", "\\)");
    }
}
