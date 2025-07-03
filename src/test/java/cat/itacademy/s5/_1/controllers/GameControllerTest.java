package cat.itacademy.s5._1.controllers;

import cat.itacademy.s5._1.dtos.CardDTO;
import cat.itacademy.s5._1.dtos.GameDTO;
import cat.itacademy.s5._1.dtos.StartGameRequest;
import cat.itacademy.s5._1.entities.enums.GameStatus;
import cat.itacademy.s5._1.services.GameService;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.test.web.reactive.server.WebTestClient;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameControllerTest {
    private WebTestClient webTestClient;
    private GameService gameService;

    @BeforeEach
    void setUp(){
        gameService = mock(GameService.class);
        webTestClient = WebTestClient.bindToController(new GameController(gameService)).build();
    }

    @Test
    void startNewGame_ShouldReturnGameDTO() throws ParseException {
        //given
        ObjectId gameId = new ObjectId();
        Date startDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
                .parse("2025-07-02T23:53:38.571+00:00");
        List<CardDTO> dealerCards = List.of(
                new CardDTO("SPADE", "SIX"),
                new CardDTO("CLUB", "FIVE")
        );
        List<CardDTO> playerCards = List.of(
                new CardDTO("HEART", "FIVE"),
                new CardDTO("HEART", "A")
        );
        GameDTO expectedGameDto = new GameDTO(
                gameId,
                "Zohra",
                "zocat@me.com",
                startDate,
                GameStatus.IN_PROGRESS,
                playerCards,
                dealerCards );

        //WHEN / THEN
        when(gameService.startNewGame("id123456")).thenReturn(Mono.just(expectedGameDto));

        webTestClient.post()
                .uri("/games/new")
                .bodyValue(new StartGameRequest("id123456"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(GameDTO.class)
                .value(gameDTO ->
                {
                    assertEquals(gameDTO.gameId(), expectedGameDto.gameId());
                    assertEquals(gameDTO.status(), expectedGameDto.status());
                    assertEquals(expectedGameDto.startedAt(), gameDTO.startedAt());
                    assertEquals(expectedGameDto.playerHand(), gameDTO.playerHand());
                    assertEquals(expectedGameDto.dealerHand(), gameDTO.dealerHand());
                });
    }
}
