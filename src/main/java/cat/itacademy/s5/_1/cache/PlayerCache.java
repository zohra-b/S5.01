package cat.itacademy.s5._1.cache;

import cat.itacademy.s5._1.entities.Player;
import cat.itacademy.s5._1.repositories.PlayerRepository;
import cat.itacademy.s5._1.services.PlayerService;
import jakarta.annotation.PostConstruct;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlayerCache {
    //Stocke les joueurs en mémoire sous forme de Map thread-safe (prend comme clé l'uuid et comme valeur le player)
    private final ConcurrentMap<String, Player> cache = new ConcurrentHashMap<>();

    // Injection du repository (accès à la base de données)
    @Autowired
    private PlayerRepository playerRepo;


    //@PostConstruct // annotation qui marque une méthode qui doit être exécutée :
                            // + Une seule fois
                            // ++ Juste après que Spring ait créé le bean (instance de la classe)
                            // +++ Avant que le bean ne soit utilisé par d'autres composants
                            // +++ Avant que le bean ne soit utilisé par d'autres composants
    @PostConstruct
    public Mono<Void> init(){ // Méthode appelée automatiquement après la création du bean par Spring
        return playerRepo.findAll()   // 1. Récupère TOUS les joueurs depuis MongoDB
                .doOnNext(player -> cache.put(player.getPlayerId(), player)) // 2. Pour chaque joueur trouvé, l'ajoute au cache
                .then(); // 3. Termine le flux sans retourner de valeur (équivalent à .thenReturn(null)) : Convertit le Flux en Mono<Void> (signal de fin)

    }

    public Mono<Void> refreshPlayer(String playerId){
        return playerRepo.findById(playerId)
                .doOnNext(player -> cache.put(playerId, player))
                .then();
    }
}
