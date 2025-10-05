package ai.pokemcp.app.service;

import java.time.Duration;
import java.util.Optional;

import com.google.common.primitives.Ints;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skaro.pokeapi.client.PokeApiClient;
import skaro.pokeapi.query.PageQuery;
import skaro.pokeapi.resource.NamedApiResourceList;
import skaro.pokeapi.resource.pokemon.Pokemon;

@Slf4j
@Service
public class PokeApiService {
    private final PokeApiClient pokeApiClient;

    private final static int PAGE_SIZE = 20;

    public PokeApiService(PokeApiClient pokeApiClient) {
        this.pokeApiClient = pokeApiClient;
    }

    public NamedApiResourceList<Pokemon> getAllPokemon(String pageNumber) {
        int pageNum = Optional.ofNullable(pageNumber).map(Ints::tryParse).orElse(0);

        try {
            log.atInfo().log("Fetching all Pokemon");
            return pokeApiClient.getResource(Pokemon.class, new PageQuery(PAGE_SIZE, pageNumber == null
                            ? 0 : pageNum * PAGE_SIZE))
                    .timeout(Duration.ofSeconds(30))
                    .retry(3)
                    .block();
        } catch (Exception e) {
            log.atError().setCause(e).log("Error fetching all Pokemon");
            throw new RuntimeException("Failed to fetch all Pokemon", e);
        }
    }

    public Pokemon getSpecificPokemon(String name) {
        try {
            log.atInfo().addArgument(name).log("Fetching Pokemon: {}");
            return pokeApiClient.getResource(Pokemon.class, name)
                    .timeout(Duration.ofSeconds(15))
                    .retry(3)
                    .block();
        } catch (Exception e) {
            log.atError().setCause(e).log("Error fetching Pokemon: {}", name);
            throw new RuntimeException("Failed to fetch Pokemon: " + name, e);
        }
    }
}
