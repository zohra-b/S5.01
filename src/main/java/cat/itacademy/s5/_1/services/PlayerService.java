package cat.itacademy.s5._1.services;

import cat.itacademy.s5._1.cache.PlayerCache;
import cat.itacademy.s5._1.dtos.PlayerDTO;
import cat.itacademy.s5._1.entities.Player;
import cat.itacademy.s5._1.exceptions.PlayerNotFoundException;
import cat.itacademy.s5._1.repositories.PlayerRepository;
import cat.itacademy.s5._1.validations.ValidateInputs;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
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

//    public Mono<PlayerDTO> createNewPlayer(String name, String email) {
//        return validatePlayerInputs(name, email)
//                .map(validEmail -> {
//                    Player newPlayer = new Player(name, email);
//                    return newPlayer;
//                })
//                .flatMap(playerRepo::save)
//                .map(PlayerDTO::fromEntity);
//    }
//    public Mono<PlayerDTO> createNewPlayer(String name, String email) {
//        return validatePlayerInputs(name, email)
//                .map(validEmail -> Player.builder()
//                        .playerId(UUID.randomUUID().toString())
//                        .playerName(name)
//                        .playerEmail(validEmail)
//                        .totalScore(0)
//                        .build())
//                .flatMap(player -> {
//                return playerRepo.save(player);})
//                .map(PlayerDTO::fromEntity);
//    }

    public Mono<PlayerDTO> createNewPlayer(String name, String email) {
        return validatePlayerInputs(name, email)
                .map(validEmail -> {
                    // Utiliser le constructeur qui marque automatiquement isNewEntity = true
                    Player newPlayer = new Player(name, validEmail);
                    return newPlayer;
                })
                .flatMap(playerRepo::save)
                .doOnNext(Player::markNotNew) // Marquer comme sauvegardé
                .map(PlayerDTO::fromEntity);
    }

    public Flux<Player> findAll() {
        return playerRepo.findAll();
    }

    public Mono<PlayerDTO> findByID(String playerId) {
        return playerRepo.findById(playerId)
                .switchIfEmpty(Mono.error(new PlayerNotFoundException("id not found : " + playerId)))
                .map(PlayerDTO::fromEntity);
    }

    public Mono<String> getIdByPlayerEmail(String playerEmail) {
        return playerRepo.findByPlayerEmail(playerEmail)
                .switchIfEmpty(Mono.error(new PlayerNotFoundException("email not found : " + playerEmail)))
                .map(Player::getPlayerId);
    }

    public Mono<PlayerDTO> findByPlayerEmail(String playerEmail){
        ValidateInputs.isValidEmail(playerEmail);
        return playerRepo.findByPlayerEmail(playerEmail)
                .map(PlayerDTO::fromEntity);

    }

    public Mono<PlayerDTO> updatePlayerName(String playerId, String newName) {
        if (!ValidateInputs.isValidFieldNotEmpty(newName)) {
            return Mono.error(new IllegalArgumentException("Name must not be empty"));
        }

        return playerRepo.findById(playerId)
                .switchIfEmpty(Mono.error(new PlayerNotFoundException("Player not found: " + playerId)))
                .flatMap(player -> {
                    player.setPlayerName(newName);
                    return playerRepo.save(player);
                })
                .map(PlayerDTO::fromEntity);
    }
    public Mono<Player> updatePlayerScore(String playerId, int newScore) {
        return playerRepo.findById(playerId)
                .flatMap(player -> {
                    int updatedScore = player.getTotalScore() + newScore;
                    player.setTotalScore(updatedScore);
                    return playerRepo.save(player);
                })
                .doOnSuccess(updatedPlayer -> playerCache.refreshPlayer(playerId).subscribe())
                .switchIfEmpty(Mono.error(new PlayerNotFoundException(playerId.toString())));
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
