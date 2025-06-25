package cat.itacademy.s5._1.services;

import cat.itacademy.s5._1.dtos.GameDTO;
import cat.itacademy.s5._1.entities.Deck;
import cat.itacademy.s5._1.entities.Game;
import cat.itacademy.s5._1.entities.enums.GameStatus;
import cat.itacademy.s5._1.repositories.GameRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.stereotype.Service;
import org.bson.types.ObjectId;

@Service
public class GameService {
    private final GameRepository gameRepo;
    private final GameLogic gameLogic;

    public GameService(GameRepository gameRepo, GameLogic gameLogic) {
        this.gameRepo = gameRepo;
        this.gameLogic = gameLogic;
    }

    public Mono<Game> startNewGame(String playerId){
        Game game = new Game();
        game.setGameId(new ObjectId());
        game.setPlayerID(playerId);
        game.setStartedAt(new Date());
        game.setStatus(GameStatus.IN_PROGRESS);
        game.setDeck(new Deck());
        game.setPlayerHand(new ArrayList<>());
        game.setDealerHand(new ArrayList<>());

        game.getPlayerHand().add(game.getDeck().drawCard());
        game.getDealerHand().add(game.getDeck().drawCard());
        game.getPlayerHand().add(game.getDeck().drawCard());
        game.getDealerHand().add(game.getDeck().drawCard());

       game.setStatus(gameLogic.evaluateInitialStatus(game));
        return gameRepo.save(game);
    }

    public Mono<Game> getGameById(ObjectId gameId){
        return gameRepo.findById(gameId);
    }

    public Flux<GameDTO> findByPlayerId(String playerId){
        return gameRepo.findByPlayerId(playerId);
    }

    public Mono<Game> hit(ObjectId gameId) {
        return getGameById(gameId)
                .flatMap(game -> {
                    game.getPlayerHand().add(game.getDeck().drawCard());
                    int handValue = gameLogic.calculateHandValue(game.getPlayerHand());
                    if (handValue > 21) {
                        game.setStatus(GameStatus.DEALER_WINS);
                        game.setEndedAt(new Date());
                    }
                    return gameRepo.save(game);
                });
    }

    public Mono<Game> stand(ObjectId gameId){
        return getGameById(gameId)
                .flatMap(game -> {
                    gameLogic.dealerLogic(game);
                    gameLogic.evaluateFinalGameStatus(game);
                    return gameRepo.save(game);
                });

    }
       public Mono<Game> endGame(ObjectId gameId){
            return getGameById(gameId)
                    .flatMap(game -> {
                        game.setEndedAt(new Date());
                        return gameRepo.save(game);
                    });
       }

    // save(T entity)	Sauvegarde (insert ou update)	Mono<T>
    // saveAll(Iterable<T> entities)	Sauvegarde plusieurs éléments	Flux<T>
    // findById(ID id)	Cherche par identifiant	Mono<T>
    // findAll()	Renvoie tous les documents de la collection	Flux<T>
    // existsById(ID id)	Vérifie si un ID existe	Mono<Boolean>
    // count()	Compte tous les documents	Mono<Long>
    // deleteById(ID id)	Supprime un document par ID	Mono<Void>
    // delete(T entity)	Supprime un document	Mono<Void>
    // deleteAll()	Supprime toute la collection	Mono<Void>
    // deleteAll(Iterable<? extends T>)	Supprime plusieurs documents	Mono<Void>
}
