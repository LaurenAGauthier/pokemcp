package ai.pokemcp.app.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class HealthResponse {
    private HealthStatus server;
    private HealthStatus pokeApi;

    public enum HealthStatus {
        HEALTHY,
        UNHEALTHY
    }
}
