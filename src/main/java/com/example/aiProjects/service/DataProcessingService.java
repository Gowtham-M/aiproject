package com.example.aiProjects.service;

import com.example.aiProjects.models.MarketDataResponse;
import com.example.aiProjects.models.MarketData;

import org.springframework.ai.document.Document; // Spring AI Document
import org.springframework.ai.vectorstore.VectorStore; // Your vector store
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class DataProcessingService {
    private final ExternalApiService apiService;
    private final VectorStore vectorStore;

    @Autowired
    public DataProcessingService(ExternalApiService apiService, VectorStore vectorStore) {
        this.apiService = apiService;
        this.vectorStore = vectorStore;
    }

    public void processAndStoreData() {
        apiService.fetchDataFromApi()
                .subscribe(
                        response -> {
                            Map<String, MarketData> dataMap = response.getWeeklyTimeSeries();
                            if (dataMap != null) {
                                dataMap.forEach((date, data) -> {
                                    try {
                                        // Create a 5D vector from the numbers
                                        List<Double> embedding = List.of(
                                                Double.parseDouble(data.getOpen()),
                                                Double.parseDouble(data.getHigh()),
                                                Double.parseDouble(data.getLow()),
                                                Double.parseDouble(data.getClose()),
                                                Double.parseDouble(data.getVolume())
                                        );

                                        // Create content string
                                        String content = data.toString();

                                        // Create document
                                        Document document = new Document(
                                                data.getDate(),  // ID
                                                content,         // Content
                                                Map.of("type", "market-data", "date", data.getDate()) // Metadata
                                        );

                                        // Save to vector store
                                        vectorStore.add(List.of(document));
                                        System.out.println("Saved data for " + data.getDate());
                                    } catch (NumberFormatException e) {
                                        System.err.println("Error parsing numbers for " + data.getDate() + ": " + e.getMessage());
                                    }
                                });
                            }
                        },
                        error -> System.err.println("Error fetching data: " + error.getMessage()),
                        () -> System.out.println("Processing complete")
                );
    }
}
