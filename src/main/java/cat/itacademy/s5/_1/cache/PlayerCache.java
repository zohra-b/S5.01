package cat.itacademy.s5._1.cache;

import cat.itacademy.s5._1.entities.Player;
import cat.itacademy.s5._1.repositories.PlayerRepository;
import jakarta.annotation.PostConstruct;
import reactor.core.publisher.Mono;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlayerCache {
    private final ConcurrentMap<String, Player> cache = new ConcurrentHashMap<>();

    @Autowired
    private PlayerRepository playerRepo;

    @PostConstruct
    public Mono<Void> init() {
        return playerRepo
                .findAll()
                .doOnNext(player -> cache.put(player.getPlayerId(), player))
                .then();
    }

    public Mono<Void> refreshPlayer(String playerId) {
        return playerRepo
                .findById(playerId)
                .doOnNext(player -> cache.put(playerId, player))
                .then();
    }
}
