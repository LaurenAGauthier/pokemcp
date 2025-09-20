package ai.pokemcp.app.config;

import ai.pokemcp.app.service.HealthService;
import ai.pokemcp.app.service.PokeApiService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import skaro.pokeapi.client.PokeApiClient;

@Configuration
public class PokeMcpServerConfig {
    private final PokeApiClient pokeApiClient;

    @Autowired
    public PokeMcpServerConfig(PokeApiClient apiClient) {
        pokeApiClient = apiClient;
    }

    @Bean
    public PokeApiService pokeApiService() {
        return new PokeApiService(pokeApiClient);
    }

    @Bean
    public HealthService healthService() {
        return new HealthService(pokeApiClient);
    }

    @Bean
    public ToolCallbackProvider pokeApiTools() {
        return MethodToolCallbackProvider.builder()
                .toolObjects(pokeApiService(), healthService())
                .build();
    }
}
