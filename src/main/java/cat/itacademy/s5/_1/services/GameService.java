package cat.itacademy.s5._1.services;

import cat.itacademy.s5._1.dtos.GameDTO;
import cat.itacademy.s5._1.dtos.GameSummaryDTO;
import cat.itacademy.s5._1.dtos.GamesByPlayerDTO;
import cat.itacademy.s5._1.dtos.PlayerDTO;
import cat.itacademy.s5._1.entities.Deck;
import cat.itacademy.s5._1.entities.Game;
import cat.itacademy.s5._1.entities.Player;
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
        Deck deck = new Deck();
        deck.initialize();
        game.setGameId(new ObjectId());
        game.setPlayerId(playerId);
        game.setStartedAt(new Date());
        game.setStatus(GameStatus.IN_PROGRESS);
        game.setDeck(deck);
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



//    public Flux<GameSummaryDTO> findSummariesPlayerId(String playerId) {
//        return gameRepo.findByPlayerId(playerId)
//                .map(GameSummaryDTO::gameSummaryDtoFromGame);
//    }
    public Mono<GamesByPlayerDTO> findSummariesPlayerId(String playerId) {
        return playerService.findByID(playerId)
                .flatMap(playerDTO ->
                        gameRepo.findByPlayerId(playerId)
                                .map(GameSummaryDTO::gameSummaryDtoFromGame)
                                .collectList()
                                .map(gameSummaries ->
                                        new GamesByPlayerDTO(
                                                playerDTO.playerName(),
                                                playerDTO.playerEmail(),
                                                gameSummaries
                                        ))
                        );}


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
                    if (game.getStatus() != GameStatus.IN_PROGRESS) {
                        return Mono.error(new InvalidMoveException(" Cannot hit: the game is already finished"));
                    }

                    game.getPlayerHand().add(game.getDeck().drawCard());
                    GameStatus newStatus = gameLogic.evaluateStatusAfterHit(game);

                    if (newStatus != GameStatus.IN_PROGRESS) {
                        return endGame(game, newStatus); // Blackjack ou bust
                    }

                    return gameRepo.save(game)
                            .flatMap(savedGame ->
                                    GameDTO.gameDtoFromGameAndPlayerMono(savedGame, playerService.findByID(savedGame.getPlayerId()))
                            );
                });
    }

    public Mono<GameDTO> stand(ObjectId gameId) {
        return gameRepo.findById(gameId)
                .switchIfEmpty(Mono.error(new GameNotFoundException("Game with id " + gameId + " not found")))
                .flatMap(game -> {
                    if (game.getStatus() != GameStatus.IN_PROGRESS) {
                        return Mono.error(new InvalidMoveException(" Cannot stand: the game is already finished"));
                    }

                    GameStatus earlyStatus = gameLogic.evaluateStatusAfterHit(game);
                    if (earlyStatus != GameStatus.IN_PROGRESS) {
                        return endGame(game, earlyStatus);
                    }

                    gameLogic.dealerLogic(game);
                    GameStatus finalStatus = gameLogic.evaluateFinalGameStatus(game);
                    return endGame(game, finalStatus);
                });
    }

    private Mono<GameDTO> endGame(Game game, GameStatus status) {
        game.setStatus(status);
        game.setEndedAt(new Date());

        return playerService.updatePlayerScore(game.getPlayerId(), giveScore(status) )
                        .then(gameRepo.save(game))
                .flatMap(savedGame ->
                        GameDTO.gameDtoFromGameAndPlayerMono(savedGame, playerService.findByID(savedGame.getPlayerId()))
                );
    }

    public Mono<Void> deleteGameById(ObjectId gameId){
        return gameRepo.deleteById(String.valueOf(gameId));
    }

    private int giveScore(GameStatus gameStatus){
       int points;
        switch (gameStatus){
           case PLAYER_BLACKJACK :
               points = 3;
               break;
           case PLAYER_WINS:
               points = 2;
               break;
           case TIE:
               points = 1;
               break;
           default:
               points = 0;
               break;
       }
       return points;
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
