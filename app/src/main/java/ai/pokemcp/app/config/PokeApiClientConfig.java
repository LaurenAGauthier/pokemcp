package ai.pokemcp.app.config;

import java.time.Duration;

import io.netty.resolver.DefaultAddressResolverGroup;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;
import skaro.pokeapi.PokeApiReactorCachingConfiguration;

@Configuration
@Import(PokeApiReactorCachingConfiguration.class)
@EnableCaching
public class PokeApiClientConfig {
    @Bean
    public ConnectionProvider connectionProvider() {
        return ConnectionProvider.builder("PokeAPI")
                .maxIdleTime(Duration.ofSeconds(15))
                .maxConnections(50)
                .build();
    }

    @Bean
    public HttpClient httpClient(ConnectionProvider connectionProvider) {
        return HttpClient.create(connectionProvider)
                .compress(true)
                .resolver(DefaultAddressResolverGroup.INSTANCE);
    }
}
