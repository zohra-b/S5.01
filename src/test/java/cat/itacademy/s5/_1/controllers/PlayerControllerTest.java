package cat.itacademy.s5._1.controllers;

import cat.itacademy.s5._1.dtos.PlayerDTO;
import cat.itacademy.s5._1.services.PlayerService;
import reactor.core.publisher.Mono;

import org.springframework.test.web.reactive.server.WebTestClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;



import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerControllerTest {
    private WebTestClient webTestClient;
    private PlayerService playerService;

    @BeforeEach
    void setUp(){
        playerService = mock(PlayerService.class);
        webTestClient = WebTestClient.bindToController(new PlayerController(playerService)).build();
    }

    @Test
    void getPlayerById_ShouldReturnPlayerDto(){
        //GIVEN
        String playerId = "Id123456";
        PlayerDTO playerDTO = new PlayerDTO(playerId, "Zohra", "zocat@me.com", 5);

        when(playerService.findByID(playerId)).thenReturn(Mono.just(playerDTO));

        //WHEN+THEN
        webTestClient.get()
                .uri("/players/playerById/Id123456")
                .exchange()
                .expectStatus().isOk()
                .expectBody(PlayerDTO.class)
                .value(playerDTO1 ->
                {
                    assertEquals(playerDTO1.playerEmail(), playerDTO.playerEmail() );
                    assertEquals(playerDTO1.playerName(), playerDTO.playerName() );
                    assertEquals(playerDTO1.totalScore(), playerDTO.totalScore());
                });
    }
}
