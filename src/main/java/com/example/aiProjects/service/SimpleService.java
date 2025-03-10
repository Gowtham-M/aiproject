package com.example.aiProjects.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimpleService {
    @Autowired
    VectorStore vectorStore;



    List<Document> documents = List.of(
            new Document("Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!!"),
            new Document("The World is Big and Salvation Lurks Around the Corner"),
            new Document("You walk forward facing the past and you turn back toward the future."));

    public void processDocuments(List<Document> documents) {
        vectorStore.add(documents);
    };

    public List<Document> getDocuments(String query) {
        List<Document> results = this.vectorStore.similaritySearch(SearchRequest.builder().query(query).topK(5).build());
        return results;
    }

}
