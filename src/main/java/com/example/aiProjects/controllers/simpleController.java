package com.example.aiProjects.controllers;


import org.springframework.ai.document.Document;
import org.springframework.web.bind.annotation.*;
import com.example.aiProjects.service.SimpleService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/simple")
public class simpleController {
    private final SimpleService simpleService;
    public simpleController(SimpleService simpleService) {
        this.simpleService = simpleService;
    }

    @GetMapping("/adddocs")
    public String addDocs() {
//        simpleService.processDocuments();
        return "Done";
    }

    @PostMapping("/addmapping")
    public String getdocs(@RequestBody String query) {
        List<Document> results  = simpleService.getDocuments(query);
        return results.toString();
    }

    @PostMapping("/add")
    public String addDocuments(@RequestBody List<String> texts) {
        // Convert input texts into Spring AI Document objects
        List<Document> documents = texts.stream()
                .map(Document::new)
                .collect(Collectors.toList());

        // Store documents in PgVector
        simpleService.processDocuments(documents);

        return "Documents stored successfully!";
    }


}
