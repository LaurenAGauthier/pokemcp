package ai.pokemcp.app.controller;

import ai.pokemcp.app.service.PokeApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Controller;
import skaro.pokeapi.resource.NamedApiResourceList;
import skaro.pokeapi.resource.pokemon.Pokemon;
import skaro.pokeapi.resource.pokemonform.PokemonForm;
import skaro.pokeapi.resource.pokemonspecies.PokemonSpecies;

@Slf4j
@Controller
public class PokeController {
    private final PokeApiService pokeApiService;

    public PokeController(PokeApiService pokeApiService) {
        this.pokeApiService = pokeApiService;
    }

    @Tool(name = "getAllPokemon", description = "Get list of Pokemon in pages of 20 by page number." +
            "Page number starts at 0. If unable to parse page number, defaults to 0.")
    public NamedApiResourceList<Pokemon> getAllPokemon(@ToolParam(description = "page number") String pageNumber) {
        return pokeApiService.getAllPokemon(pageNumber);
    }

    @Tool(name = "getSpecificPokemon", description = "Get information about a specific Pokemon by name or ID")
    public Pokemon getSpecificPokemon(@ToolParam(description = "name or ID of the pokemon") String name) {
        return pokeApiService.getSpecificPokemon(name);
    }

    @Tool(name = "getSpecificSpecies", description = "Get information about a specific Pokemon species by name or ID")
    public PokemonSpecies getSpecificSpecies(@ToolParam(description = "name or ID of the pokemon species") String name) {
        return pokeApiService.getSpecificSpecies(name);
    }

    @Tool(name = "getSpecificForm", description = "Get information about a specific Pokemon form by name or ID")
    public PokemonForm getSpecificForm(@ToolParam(description = "name or ID of the pokemon form") String name) {
        return pokeApiService.getSpecificForm(name);
    }
}
