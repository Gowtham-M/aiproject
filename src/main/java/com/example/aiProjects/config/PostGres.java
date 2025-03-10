    package com.example.aiProjects.config;

    import com.fasterxml.jackson.databind.introspect.AnnotatedConstructor;
    import com.google.api.client.util.Value;
    import org.springframework.ai.embedding.EmbeddingModel;
    import org.springframework.ai.embedding.EmbeddingRequest;
    import org.springframework.ai.embedding.EmbeddingResponse;
    import org.springframework.ai.postgresml.PostgresMlEmbeddingModel;
    import org.springframework.ai.postgresml.PostgresMlEmbeddingOptions;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.jdbc.core.JdbcTemplate;
    import org.springframework.jdbc.datasource.DriverManagerDataSource;

    import javax.sql.DataSource;
    import java.util.List;
    import java.util.Map;

    @Configuration
    public class PostGres {
        @Value("${spring.datasource.url}")
        private String DB_URL;
        @Value("${spring.datasource.username}")
        private String DB_USERNAME;
        @Value("${spring.datasource.password}")
        private String DB_PASSWORD;
        @Value("${spring.ai.postgresml.embedding.options.transformer}")
        private String EMBEDDING_MODEL;

        // Database connection properties (replace with your actual values)


        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }


        @Bean
        public EmbeddingResponse embeddingResponse(EmbeddingModel embeddingModel) {
//            return embeddingModel.call(
//                    new EmbeddingRequest(
//                            List.of("Hello World", "World is big and salvation is near"),
//                            PostgresMlEmbeddingOptions.builder()
//                                    .transformer("intfloat/e5-small")
//                                    .vectorType(PostgresMlEmbeddingModel.VectorType.PG_ARRAY)
//                                    .kwargs(Map.of("device", "gpu"))
//                                    .build()
//                    )
//            );
            return null;
        }
    }