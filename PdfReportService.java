package com.example.cybershield.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import com.example.cybershield.model.DetectionEvent;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class PdfReportService {
    public byte[] generateReport(List<DetectionEvent> events) throws IOException {
        try (PDDocument doc = new PDDocument(); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PDPage page = new PDPage(PDRectangle.A4);
            doc.addPage(page);
            PDPageContentStream content = new PDPageContentStream(doc, page);
            content.setFont(PDType1Font.HELVETICA_BOLD, 16);
            content.beginText();
            content.newLineAtOffset(50, 750);
            content.showText("Detection Report");
            content.endText();
            content.setFont(PDType1Font.HELVETICA, 12);
            int y = 720;
            for (DetectionEvent e : events) {
                content.beginText();
                content.newLineAtOffset(50, y);
                content.showText(e.toString());
                content.endText();
                y -= 20;
                if (y < 50) break; // simple page limit
            }
            content.close();
            doc.save(baos);
            return baos.toByteArray();
        }
    }
}
