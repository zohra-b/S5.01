package cat.itacademy.s5._1.controllers;

import cat.itacademy.s5._1.dtos.GameDTO;
import cat.itacademy.s5._1.dtos.PlayRequest;
import cat.itacademy.s5._1.dtos.StartGameRequest;
import cat.itacademy.s5._1.entities.Game;
import cat.itacademy.s5._1.exceptions.GameNotFoundException;
import cat.itacademy.s5._1.exceptions.InvalidMoveException;
import cat.itacademy.s5._1.services.GameService;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.bson.types.ObjectId;

@RestController
@RequestMapping("/game")
public class GameController {
    private final GameService gameService;

@Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }


    @PostMapping("/new")
    public Mono<ResponseEntity<GameDTO>> startNewGame(@RequestBody StartGameRequest request){
        return gameService.startNewGame(request.getPlayerId())
                .map(gameDTO
                        -> ResponseEntity.status(HttpStatus.CREATED).body(gameDTO) );
    }

    @PostMapping("/{id}/play")
    public Mono<ResponseEntity<GameDTO>> playGame(@PathVariable String id, @RequestParam PlayRequest request){
        ObjectId gameId = new ObjectId(id);
        return gameService.play(gameId, request.getMoveType())
                .map(gameDto -> ResponseEntity.ok().body(gameDto))
                .onErrorResume(InvalidMoveException.class, ex ->
                        Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).build()));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<GameDTO>> getGameById(@PathVariable String id){
        ObjectId gameId = new ObjectId(id);
        return gameService.getGameById(gameId)
            .map(gameDTO
                    -> ResponseEntity.status(HttpStatus.OK).body(gameDTO) )
                .onErrorResume(GameNotFoundException.class,
                e -> Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).build()));
    }

    @DeleteMapping("/{id}/delete")
    public Mono<ResponseEntity<Object>> deleteGameById(@PathVariable String id){
        ObjectId gameId = new ObjectId(id);
        return gameService.deleteGameById(gameId)
                .map(game -> ResponseEntity.status(HttpStatus.NO_CONTENT).body((Object)"Game deleted"));
    }





}
