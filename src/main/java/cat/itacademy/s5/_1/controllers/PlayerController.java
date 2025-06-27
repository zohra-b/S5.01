package cat.itacademy.s5._1.controllers;

import cat.itacademy.s5._1.dtos.PlayerDTO;
import cat.itacademy.s5._1.dtos.PlayerRegistrationRequestDTO;
import cat.itacademy.s5._1.exceptions.PlayerNotFoundException;
import cat.itacademy.s5._1.services.PlayerService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/players")
public class PlayerController {
    /// / NE PAS OUBLIER SUBSCRIBE() POUR LANCER LES MONO ET FLUX !! SINON IL NE SE PASSE RIEN
    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/getAll")
    public Flux<PlayerDTO> getAllPlayer(){
        return playerService.findAll()
                .map(PlayerDTO::fromEntity);
    }

    @GetMapping("/playerById/{id}")
    public Mono<ResponseEntity<Object>> getPlayerByID (@PathVariable String id){
        return playerService.findByID(id)
                .map(playerDTO -> ResponseEntity.ok().body((Object)playerDTO))
                .onErrorResume(PlayerNotFoundException.class, e -> Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage())));
    }

    @GetMapping("/playerByEmail/{email}")
    public Mono<ResponseEntity<Object>> getPlayerbyEmail(@PathVariable String email) {

        return playerService.findByPlayerEmail(email)
                .map(playerDTO -> ResponseEntity.ok().body((Object) playerDTO))
                .onErrorResume(PlayerNotFoundException.class, e -> Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage())));
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<Object>> playerLogin(@RequestBody  String email){
        if (email== null || email.trim().isEmpty()) {
            return Mono.just( ResponseEntity.badRequest().body("Email field cannot be empty. Enter an email"));
        }

        return playerService.findByPlayerEmail(email)
        .map(playerDTO -> ResponseEntity.ok().body((Object)playerDTO))  // equivaut à new ResponseEntity<>(playerDTO, HTTPStatus.OK)
                .switchIfEmpty(Mono.just(
                        ResponseEntity.status(HttpStatus.NOT_FOUND).body((Object)"Email not found in database, create a new player")));

    }

    @PostMapping("/register")
    public Mono<ResponseEntity<PlayerDTO>> registerPlayer(@RequestBody PlayerRegistrationRequestDTO registrationRequestDTO){
        return playerService.createNewPlayer(registrationRequestDTO.getName(), registrationRequestDTO.getEmail())
                .map(playerDTO -> ResponseEntity.status(HttpStatus.CREATED).body(playerDTO));
    }

    @PutMapping("/{id}/updateScore")
    public Mono<ResponseEntity<Object>> updatePlayerScore(@PathVariable String id, @RequestParam int newScore){
        return playerService.updatePlayerScore(id, newScore)
                .map(player -> ResponseEntity.status(HttpStatus.OK).body((Object)"Score updated successfully"))
                .onErrorResume(PlayerNotFoundException.class, e -> Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage())));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Object>> updatePlayerName(@PathVariable String id, @RequestBody String newName){
        return playerService.updatePlayerName(id, newName)
                .map(playerDTO -> ResponseEntity.status(HttpStatus.OK).body((Object)"Name updated successfully"))
                .onErrorResume(PlayerNotFoundException.class, e -> Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage())));
    }

    @GetMapping("/ranking")
    public Flux<PlayerDTO> getPlayersSortedByScore() {
        return playerService.getPlayersSortedByScore()
                .map(PlayerDTO::fromEntity);
    }
}
