package ai.pokemcp.app.controller;

import ai.pokemcp.app.dto.HealthResponse;
import ai.pokemcp.app.service.HealthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class HealthController {
    private final HealthService healthService;

    public HealthController(HealthService healthService) {
        this.healthService = healthService;
    }

    @Tool(name = "getHealth", description = "Check server and PokeAPI health")
    public HealthResponse getHealth() {
        return healthService.getHealth();
    }
}
