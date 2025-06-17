package cat.itacademy.s5._1.services;

import cat.itacademy.s5._1.entities.Player;
import cat.itacademy.s5._1.repositories.PlayerRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {
    private PlayerRepository playerRepo;

    @Autowired
    public PlayerService(PlayerRepository playerRepo){
        this.playerRepo = playerRepo;
    }

    public void createPlayer(Player newPlayer){
        playerRepo.save(newPlayer);
    }

    public Flux<Player> findAll(){
        return playerRepo.findAll();
    }

    public Mono<Player> findByID(UUID playerID){
        return playerRepo.findById(playerID);
    }

    public Mono<UUID> getIdByPlayerEmail(String playerEmail){
        return playerRepo.findByPlayerEmail(playerEmail)
                    .map(Player::getPlayerID);
    }

    public void deletePlayerById(String playerEmail){
        Mono<UUID> playerId = getIdByPlayerEmail(playerEmail);
        playerRepo.deleteById(playerId);
    }
    // existsById(ID id)	Vérifie si un enregistrement existe par ID	Mono<Boolean>
    // count()	Retourne le nombre total d’enregistrements	Mono<Long>
    // deleteById(ID id)	Supprime un enregistrement par ID	Mono<Void>
    // delete(T entity)	Supprime un enregistrement donné	Mono<Void>
    // deleteAll()	Supprime tous les enregistrements	Mono<Void>
    // deleteAll(Iterable<? extends T>)
}
