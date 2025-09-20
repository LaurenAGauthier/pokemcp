package ai.pokemcp.app.service;

import ai.pokemcp.app.dto.HealthResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skaro.pokeapi.client.PokeApiClient;
import skaro.pokeapi.resource.pokemon.Pokemon;

import java.time.Duration;

@Slf4j
@Service
public class HealthService {
    private final PokeApiClient pokeApiClient;

    @Autowired
    public HealthService(PokeApiClient pokeApiClient) {
        log.info("HealthService initialized with PokeApiClient");
        this.pokeApiClient = pokeApiClient;
    }

    @Tool(name = "getHealth", description = "Check server and PokeAPI health")
    public HealthResponse getHealth() {
        return HealthResponse.builder()
                .server(HealthResponse.HealthStatus.HEALTHY)
                .pokeApi(isPokeApiAvailable() ?
                        HealthResponse.HealthStatus.HEALTHY :
                        HealthResponse.HealthStatus.UNHEALTHY)
                .build();
    }

    private boolean isPokeApiAvailable() {
        try {
            Pokemon pokemon = pokeApiClient.getResource(Pokemon.class, "pikachu")
                    .timeout(Duration.ofSeconds(10))
                    .block();
            return pokemon != null;
        } catch (Exception e) {
            log.atWarn().setCause(e).log("Health check failed, marking PokeAPI as unhealthy");
            return false;
        }
    }
}
