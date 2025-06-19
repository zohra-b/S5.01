package cat.itacademy.s5._1.services;

import cat.itacademy.s5._1.cache.PlayerCache;
import cat.itacademy.s5._1.entities.Player;
import cat.itacademy.s5._1.exceptions.PlayerNotFoundException;
import cat.itacademy.s5._1.repositories.PlayerRepository;
import cat.itacademy.s5._1.validations.ValidateInputs;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {
    private PlayerRepository playerRepo;
    @Autowired
    private PlayerCache playerCache;
    @Autowired
    public PlayerService(PlayerRepository playerRepo) {
        this.playerRepo = playerRepo;
    }


    public Mono<Player> createNewPlayer(String name, String email) {
        Player newPlayer = Player.builder()
                .playerEmail(email)
                .playerName(name)
                .build();
        validatePlayer(newPlayer);
        return playerRepo.save(newPlayer);
    }


    public Flux<Player> findAll() {
        return playerRepo.findAll();
    }

    public Mono<Player> findByID(UUID playerID) {
        return playerRepo.findById(playerID);
    }

    public Mono<UUID> getIdByPlayerEmail(String playerEmail) {
        return playerRepo.findByPlayerEmail(playerEmail)
                .switchIfEmpty(Mono.error(new PlayerNotFoundException("email not found : " + playerEmail)))
                .map(Player::getPlayerID);
    }

    public Mono<Void> deletePlayerByEmail(String playerEmail) {
        return getIdByPlayerEmail(playerEmail)
                .flatMap(playerRepo::deleteById);

    }

    public Mono<Player> updatePlayerScore(UUID playerID, int newScore) {
        return playerRepo.findById(playerID)
                .flatMap(player -> {
                    int updatedScore = player.getTotalScore() + newScore;
                    player.setTotalScore(updatedScore);
                    return playerRepo.save(player);
                })
                .doOnSuccess(updatedPlayer -> playerCache.refreshPlayer(playerID).subscribe())
                .switchIfEmpty(Mono.error(new PlayerNotFoundException(playerID.toString())));
    }


    public Flux<Player> showPlayersByTotalScore(){
        return playerRepo.findAllByOrderByTotalScoreDesc();
    }



    public void validatePlayer(Player newPlayer) {
        ValidateInputs.validateFieldNotEmpty(newPlayer.getPlayerName());
        ValidateInputs.validateFieldNotEmpty(newPlayer.getPlayerEmail());

        ValidateInputs.validateEmail(newPlayer.getPlayerEmail());
    }

    public Mono<Boolean> existsByEmail(String playerEmail) {
        return playerRepo.findByPlayerEmail(playerEmail).hasElement();
    }
    // existsById(ID id)	Vérifie si un enregistrement existe par ID	Mono<Boolean>
    // count()	Retourne le nombre total d’enregistrements	Mono<Long>
    // deleteById(ID id)	Supprime un enregistrement par ID	Mono<Void>
    // delete(T entity)	Supprime un enregistrement donné	Mono<Void>
    // deleteAll()	Supprime tous les enregistrements	Mono<Void>
    // deleteAll(Iterable<? extends T>)
}
