package ai.pokemcp.app.service;

import java.time.Duration;

import ai.pokemcp.app.dto.HealthResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skaro.pokeapi.client.PokeApiClient;
import skaro.pokeapi.resource.pokemon.Pokemon;

@Slf4j
@Service
public class HealthService {
    private final PokeApiClient pokeApiClient;

    public HealthService(PokeApiClient pokeApiClient) {
        this.pokeApiClient = pokeApiClient;
    }

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
