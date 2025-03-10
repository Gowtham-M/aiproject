package com.example.aiProjects.config;


import com.google.api.client.util.Value;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Map;

import static org.springframework.ai.vectorstore.pgvector.PgVectorStore.PgDistanceType.COSINE_DISTANCE;
import static org.springframework.ai.vectorstore.pgvector.PgVectorStore.PgIndexType.HNSW;


@Configuration
public class PgVectorConfig {

    private final JdbcTemplate jdbcTemplate;

    @Value("${open-ai-embeddingkey}")
    private String openAiEmbeddingKey;

    public PgVectorConfig(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

//    @Bean
//    public EmbeddingModel embeddingModel() {
//        return new PostgresMlEmbeddingModel(this.jdbcTemplate,
//                PostgresMlEmbeddingOptions.builder()
//                        .transformer("distilbert-base-uncased") // huggingface transformer model name.
//                        .vectorType(PostgresMlEmbeddingModel.VectorType.PG_VECTOR) //vector type in PostgreSQL.
//                        .kwargs(Map.of("device", "cpu")) // optional arguments.
//                        .metadataMode(MetadataMode.EMBED) // Document metadata mode.
//                        .build());
//    }

    @Bean
    public EmbeddingModel embeddingModel() {
        // Load from application.properties
        OpenAiApi openAiApi = new OpenAiApi(openAiEmbeddingKey);
        return new OpenAiEmbeddingModel(openAiApi); // Implements EmbeddingModel
    }



    @Bean
    public VectorStore vectorStore(JdbcTemplate jdbcTemplate, EmbeddingModel embeddingModel) {
        return PgVectorStore.builder(jdbcTemplate, embeddingModel)
                .dimensions(1536)                    // Optional: defaults to model dimensions or 1536
                .distanceType(COSINE_DISTANCE)       // Optional: defaults to COSINE_DISTANCE
                .indexType(HNSW)                     // Optional: defaults to HNSW
                .initializeSchema(true)              // Optional: defaults to false
                .schemaName("public")                // Optional: defaults to "public"
                .vectorTableName("vector_store")     // Optional: defaults to "vector_store"
                .maxDocumentBatchSize(10000)         // Optional: defaults to 10000
                .build();
    }
}