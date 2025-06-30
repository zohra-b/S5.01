package cat.itacademy.s5._1.services;

import cat.itacademy.s5._1.dtos.GameDTO;
import cat.itacademy.s5._1.entities.Deck;
import cat.itacademy.s5._1.entities.Game;
import cat.itacademy.s5._1.entities.enums.GameStatus;
import cat.itacademy.s5._1.exceptions.GameNotFoundException;
import cat.itacademy.s5._1.exceptions.InvalidMoveException;
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
    private final PlayerService playerService;

    public GameService(GameRepository gameRepo, GameLogic gameLogic, PlayerService playerService) {
        this.gameRepo = gameRepo;
        this.gameLogic = gameLogic;
        this.playerService = playerService;
    }

    public Mono<GameDTO> startNewGame(String playerId) {
        Game game = new Game();
        game.setGameId(new ObjectId());
        game.setPlayerId(playerId);
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
        return gameRepo.save(game)
                .flatMap(savedGame ->
                        GameDTO.gameDtoFromGameAndPlayerMono(savedGame, playerService.findByID(playerId)
                        ));
    }

    public Mono<GameDTO> getGameById(ObjectId gameId) {
        return gameRepo.findById(gameId)
                .switchIfEmpty(Mono.error(new GameNotFoundException("Game with id " + gameId + " not found")))
                .flatMap(game ->
                        GameDTO.gameDtoFromGameAndPlayerMono(game, playerService.findByID(game.getPlayerId())));
    }


    public Flux<GameDTO> findByPlayerId(String playerId) {
        return gameRepo.findByPlayerId(playerId);
    }

    public Mono<GameDTO> play(ObjectId gameId, String moveType){
        String move = moveType.toLowerCase().trim();
        switch (move) {
            case "hit": return hit(gameId);
            case "stand": return stand(gameId);
            default:
                return Mono.error(new InvalidMoveException("Unsupported move: " + moveType));
        }

    }

    public Mono<GameDTO> hit(ObjectId gameId) {
        return gameRepo.findById(gameId)
                .switchIfEmpty(Mono.error(new GameNotFoundException("Game with id " + gameId + " not found")))
                .flatMap(game -> {
                    game.getPlayerHand().add(game.getDeck().drawCard());
                    int handValue = gameLogic.calculateHandValue(game.getPlayerHand());
                    if (handValue > 21) {
                        game.setStatus(GameStatus.DEALER_WINS);
                        game.setEndedAt(new Date());
                    }
                    return gameRepo.save(game);
                })
                .flatMap(savedGame ->
                        GameDTO.gameDtoFromGameAndPlayerMono(savedGame, playerService.findByID(savedGame.getPlayerId()))
                );
    }

    public Mono<GameDTO> stand(ObjectId gameId) {
        return gameRepo.findById(gameId)
                .switchIfEmpty(Mono.error(new GameNotFoundException("Game with id " + gameId + " not found")))
                .flatMap(game -> {
                    gameLogic.dealerLogic(game);
                    gameLogic.evaluateFinalGameStatus(game);
                    return gameRepo.save(game);
                })
                .flatMap(game ->
                        GameDTO.gameDtoFromGameAndPlayerMono(game, playerService.findByID(game.getPlayerId())));
    }

    public Mono<GameDTO> endGame(ObjectId gameId) {
        return gameRepo.findById(gameId)
                .switchIfEmpty(Mono.error(new GameNotFoundException("Game with id " + gameId + " not found")))
                .flatMap(game -> { game.setEndedAt(new Date());
                    return gameRepo.save(game);
                })
                .flatMap(game ->
                        GameDTO.gameDtoFromGameAndPlayerMono(game, playerService.findByID(game.getPlayerId())));
    }

    public Mono<Void> deleteGameById(ObjectId gameId){
        return gameRepo.deleteById(String.valueOf(gameId));
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
