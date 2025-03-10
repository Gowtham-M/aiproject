package com.example.aiProjects.controllers;

import com.example.aiProjects.service.GroqService;
import com.example.aiProjects.service.SimpleService;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TestController {

    private  final GroqService groqService;

    @Autowired
    public TestController(GroqService groqService) {
        this.groqService = groqService;
    }

    @Autowired
    public SimpleService simpleService;

    public String createPrompt(List<Document> documents, String prompt) {
        String template = """
                You are an AI assistant that can answer questions about the following documents:" +
                documents:{documents}
                question :{prompt}
                """;
        PromptTemplate promptTemplate = new PromptTemplate(template);
        return promptTemplate.render(Map.of("documents", documents, "prompt", prompt));
    }

    @PostMapping("chatwithrag")
    public String chat(@RequestBody ChatRequest request) {
        System.out.println(request.getMessage());
        List<Document> docs = simpleService.getDocuments(request.getMessage());
        String prompt = createPrompt(docs, request.getMessage());
        groqService.chat(prompt);
        return groqService.chat(prompt);
    }
    @PostMapping("/chatwithgroq")
    public String chatWithGroq(@RequestBody ChatRequest request) {
        System.out.println("Hello");
        return groqService.chat(request.getMessage());
    }

    @PostMapping("/ragbot")
    public String ragbot(@RequestBody ChatRequest request) {
        List<Document> docs = simpleService.getDocuments(request.getMessage());
        return docs.toString();
    }
    // Request class for deserialization
    public static class ChatRequest {
        private String message;

        // Getters and setters
        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
