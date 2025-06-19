package cat.itacademy.s5._1.dtos;

import cat.itacademy.s5._1.entities.Card;
import cat.itacademy.s5._1.entities.Game;
import cat.itacademy.s5._1.entities.Player;
import cat.itacademy.s5._1.entities.enums.GameStatus;
import cat.itacademy.s5._1.services.PlayerService;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.bson.types.ObjectId;

public record GameDTO (ObjectId gameId,
                       String playerName,
                       String playerEmail,
                       Date startedAt,
                       GameStatus status,
                       List<Card> playerHand,
                       List<Card> dealerHand)         {


    // Factory method that accepts the player directly
    public static GameDTO gameDtoFromGameAndPlayer(Game game, Player player){
        return new GameDTO(
                game.getGameId(),
                player.getPlayerName(),
                player.getPlayerEmail(),
                game.getStartedAt(),
                game.getStatus(),
                game.getPlayerHand(),
                game.getDealerHand()
        );
    }

    public static Mono<GameDTO> gameDtoFromGameAndPlayerMono(Game game, Mono<Player> playerMono){
        return playerMono.map(player -> gameDtoFromGameAndPlayer(game, player));
    }

}




