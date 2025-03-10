package com.example.aiProjects.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.aiProjects.service.DataProcessingService;

@RestController
@RequestMapping("/api")
public class DataController {
    private final DataProcessingService processingService;

    @Autowired
    public DataController(DataProcessingService processingService) {
        this.processingService = processingService;
    }

    @GetMapping("/process")
    public ResponseEntity<String> processData() {
        processingService.processAndStoreData();
        return ResponseEntity.ok("Data processing started");
    }
}
