package ai.pokemcp.app.service;

import ai.pokemcp.app.dto.PokemonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skaro.pokeapi.client.PokeApiClient;
import skaro.pokeapi.resource.pokemon.Pokemon;

@Slf4j
@Service
public class PokeApiService {
    private final PokeApiClient pokeApiClient;

    @Autowired
    public PokeApiService(PokeApiClient pokeApiClient) {
        log.info("PokeApiService initialized with PokeApiClient");
        this.pokeApiClient = pokeApiClient;
    }

    @Tool(name = "getPokemon", description = "Get information about a specific Pokemon by name or ID")
    public PokemonResponse getPokemon(String name) {
        try {
            log.info("Fetching Pokemon: {}", name);
            Pokemon pokemon = pokeApiClient.getResource(Pokemon.class, name).block();
            if (pokemon == null) {
                throw new RuntimeException("Pokemon not found: " + name);
            }
            return new PokemonResponse(pokemon);
        } catch (Exception e) {
            log.atError().setCause(e).log("Error fetching Pokemon: {}", name);
            throw new RuntimeException("Failed to fetch Pokemon: " + name, e);
        }
    }
}
