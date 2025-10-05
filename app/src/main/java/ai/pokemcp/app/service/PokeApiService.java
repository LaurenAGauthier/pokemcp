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
import skaro.pokeapi.resource.pokemonform.PokemonForm;
import skaro.pokeapi.resource.pokemonspecies.PokemonSpecies;

@Slf4j
@Service
public class PokeApiService {
    private final PokeApiClient pokeApiClient;

    private static final int SINGLETON_TIMEOUT = 15;
    private static final int LIST_TIMEOUT = 30;
    private static final int PAGE_SIZE = 20;
    private static final int MAX_RETRIES = 3;

    public PokeApiService(PokeApiClient pokeApiClient) {
        this.pokeApiClient = pokeApiClient;
    }

    public NamedApiResourceList<Pokemon> getAllPokemon(String pageNumber) {
        int pageNum = Optional.ofNullable(pageNumber).map(Ints::tryParse).orElse(0);

        try {
            log.atInfo().log("Fetching all Pokemon");
            return pokeApiClient.getResource(Pokemon.class, new PageQuery(PAGE_SIZE, pageNumber == null
                            ? 0 : pageNum * PAGE_SIZE))
                    .timeout(Duration.ofSeconds(LIST_TIMEOUT))
                    .retry(MAX_RETRIES)
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
                    .timeout(Duration.ofSeconds(SINGLETON_TIMEOUT))
                    .retry(MAX_RETRIES)
                    .block();
        } catch (Exception e) {
            log.atError().setCause(e).log("Error fetching Pokemon: {}", name);
            throw new RuntimeException("Failed to fetch Pokemon: " + name, e);
        }
    }

    public PokemonSpecies getSpecificSpecies(String name) {
        try {
            log.atInfo().addArgument(name).log("Fetching Pokemon Species: {}");
            return pokeApiClient.getResource(PokemonSpecies.class, name)
                    .timeout(Duration.ofSeconds(SINGLETON_TIMEOUT))
                    .retry(MAX_RETRIES)
                    .block();
        } catch (Exception e) {
            log.atError().setCause(e).log("Error fetching Pokemon Species: {}", name);
            throw new RuntimeException("Failed to fetch Pokemon Species: " + name, e);
        }
    }

    public PokemonForm getSpecificForm(String name) {
        try {
            log.atInfo().addArgument(name).log("Fetching Pokemon Form: {}");
            return pokeApiClient.getResource(PokemonForm.class, name)
                    .timeout(Duration.ofSeconds(SINGLETON_TIMEOUT))
                    .retry(MAX_RETRIES)
                    .block();
        } catch (Exception e) {
            log.atError().setCause(e).log("Error fetching Pokemon Form: {}", name);
            throw new RuntimeException("Failed to fetch Pokemon Form: " + name, e);
        }
    }
}
