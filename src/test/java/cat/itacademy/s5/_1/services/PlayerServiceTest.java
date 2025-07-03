package cat.itacademy.s5._1.services;

import cat.itacademy.s5._1.cache.PlayerCache;
import cat.itacademy.s5._1.dtos.PlayerDTO;
import cat.itacademy.s5._1.entities.Player;
import cat.itacademy.s5._1.repositories.PlayerRepository;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerServiceTest {
    private PlayerService playerService;
    private PlayerRepository playerRepo;
    private PlayerCache playerCache;


    @BeforeEach
    void setUp(){
        playerRepo = mock(PlayerRepository.class);
        playerCache = mock(PlayerCache.class);
        playerService = new PlayerService(playerRepo, playerCache);
    }

    @Test
    void findById_GivenAnId_ShouldReturnPlayerDto(){
        String id = "id123456";
        LocalDateTime creationDate = LocalDateTime.parse("2025-07-02T23:53:38.571");

        Player player = new Player(id, "zohra", "zohra@zo.com", creationDate, 12, false);

        when(playerRepo.findById(id)).thenReturn(Mono.just(player));

        Mono<PlayerDTO> expectedPlayerDTOMono = playerService.findByID(id);

        StepVerifier.create(expectedPlayerDTOMono)
                .expectNextMatches(playerDTO ->
                        playerDTO.playerName().equals("zohra") &&
                        playerDTO.playerEmail().equals("zohra@zo.com") &&
                                playerDTO.playerId().equals("id123456") &&
                                playerDTO.totalScore() == 12)
                .verifyComplete();
    }
}
