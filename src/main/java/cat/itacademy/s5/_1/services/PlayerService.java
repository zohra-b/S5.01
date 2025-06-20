package cat.itacademy.s5._1.services;

import cat.itacademy.s5._1.cache.PlayerCache;
import cat.itacademy.s5._1.dtos.PlayerDTO;
import cat.itacademy.s5._1.entities.Player;
import cat.itacademy.s5._1.exceptions.PlayerNotFoundException;
import cat.itacademy.s5._1.repositories.PlayerRepository;
import cat.itacademy.s5._1.validations.ValidateInputs;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;
import org.springframework.stereotype.Service;


/// //////////////////////////////////////// enunciado : cambiar nombre durante el partido :
/// is it : UPDATE NAME ? OR CHANGE PLAYER ???  normalement : update name : todo;

@Service
public class PlayerService {
    private final PlayerRepository playerRepo;
    private final PlayerCache playerCache;

    public PlayerService(PlayerRepository playerRepo, PlayerCache playerCache) {
        this.playerRepo = playerRepo;
        this.playerCache = playerCache;
    }

    public Mono<PlayerDTO> createNewPlayer(String name, String email) {
        return validatePlayerInputs(name, email)
                .map(validEmail -> Player.builder()
                        .playerEmail(validEmail)
                        .playerName(name)
                        .build())
                .flatMap(playerRepo::save)
                .map(PlayerDTO::fromEntity);
    }

    public Flux<Player> findAll() {
        return playerRepo.findAll();
    }

    public Mono<PlayerDTO> findByID(UUID playerID) {
        return playerRepo.findById(playerID)
                .switchIfEmpty(Mono.error(new PlayerNotFoundException("id not found : " + playerID)))
                .map(PlayerDTO::fromEntity);
    }

    public Mono<UUID> getIdByPlayerEmail(String playerEmail) {
        return playerRepo.findByPlayerEmail(playerEmail)
                .switchIfEmpty(Mono.error(new PlayerNotFoundException("email not found : " + playerEmail)))
                .map(Player::getPlayerID);
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

    public Mono<Void> deletePlayerByEmail(String playerEmail) {
        return getIdByPlayerEmail(playerEmail)
                .flatMap(playerRepo::deleteById);
    }

    public Flux<Player> getPlayersSortedByScore(){
        return playerRepo.findAllByOrderByTotalScoreDesc();
    }

    public Mono<Boolean> existsByEmail(String playerEmail) {
        return playerRepo.findByPlayerEmail(playerEmail).hasElement();
    }

    public Mono<String> validatePlayerInputs(String name, String email) {
        return Mono.just(name)
                .filter(ValidateInputs::isValidFieldNotEmpty)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("you must enter a name")))
                .then(Mono.just(email))
                .filter(mail -> ValidateInputs.isValidFieldNotEmpty(mail) && ValidateInputs.isValidEmail(mail))
                .switchIfEmpty(Mono.error(new IllegalArgumentException("you must enter an email")));
    }

}
