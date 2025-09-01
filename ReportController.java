package com.example.cybershield.controller;

import com.example.cybershield.model.DetectionEvent;
import com.example.cybershield.repository.DetectionEventRepository;
import com.example.cybershield.service.PdfReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/report")
public class ReportController {
    @Autowired
    private DetectionEventRepository eventRepo;
    @Autowired
    private PdfReportService pdfReportService;

    @GetMapping(value = "/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> getPdfReport() throws Exception {
        List<DetectionEvent> events = eventRepo.findAll();
        byte[] pdf = pdfReportService.generateReport(events);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=report.pdf");
        return ResponseEntity.ok().headers(headers).body(pdf);
    }
}
