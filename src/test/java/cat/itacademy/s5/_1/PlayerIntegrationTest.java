package cat.itacademy.s5._1;


import cat.itacademy.s5._1.dtos.PlayerDTO;
import cat.itacademy.s5._1.entities.Player;
import cat.itacademy.s5._1.repositories.PlayerRepository;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//demarre spring comme si c'etait l'appli, et demarre un serveur sur un port aleatoire)
@AutoConfigureWebTestClient
//active l’injection d’un WebTestClient pour envoyer des requêtes HTTP
// simule des appels HTTP comme un Postman
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
// garde les mêmes instances de classe entre les tests (utilse pour beforeAll)
@ActiveProfiles("test") /// TRES IMPORTANT POUR NE PAS FLINGUER LA BASE DE DONNEES : CF application-test.properties

public class PlayerIntegrationTest {
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private PlayerRepository playerRepo;
    //on injecte le repo reel

    private Player savedPlayer;

    @BeforeEach
    void setUp()
    {
        playerRepo.deleteAll().block(); // nettoie la base avant chaque test

        Player player = new Player(
                "id123456",
                "Zohra",
                "zohra@example.com",
                LocalDateTime.now(),
                10,
                true
        );

        savedPlayer = playerRepo.save(player).block(); // sauvegarder synchro
        System.out.println(savedPlayer.getPlayerId());
    }

    @Test
    void getPlayerById_ShouldReturnPlayerDto(){
        webTestClient.get()
                .uri("/players/playerById/" + savedPlayer.getPlayerId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(PlayerDTO.class)
                .value(playerDTO1 ->
                {
                    assertEquals(playerDTO1.playerEmail(), savedPlayer.getPlayerEmail());
                    assertEquals(playerDTO1.playerName(), savedPlayer.getPlayerName() );
                    assertEquals(playerDTO1.totalScore(), savedPlayer.getTotalScore());
                });
    }

}

