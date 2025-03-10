package com.example.aiProjects.service;
import com.example.aiProjects.models.MarketDataResponse;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Service
public class ExternalApiService {
    private final WebClient webClient;

    public ExternalApiService() {
        this.webClient = WebClient.builder()
                .baseUrl("https://www.alphavantage.co")
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1024 * 1024)) // Set to 1 MB
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create()))
                .build();
    }

    public Mono<MarketDataResponse> fetchDataFromApi() {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/query")
                        .queryParam("function", "TIME_SERIES_WEEKLY")
                        .queryParam("symbol", "IBM")
                        .queryParam("apikey", "DEMO") // Replace with your real API key
                        .build())
                .retrieve()
                .bodyToMono(MarketDataResponse.class)
                .onErrorMap(ex -> new RuntimeException("Failed to fetch data: " + ex.getMessage()));
    }
}
