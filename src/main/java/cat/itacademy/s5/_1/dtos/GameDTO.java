package cat.itacademy.s5._1.dtos;

import cat.itacademy.s5._1.entities.Card;
import cat.itacademy.s5._1.entities.Game;
import cat.itacademy.s5._1.entities.enums.GameStatus;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

public record GameDTO (
                        @JsonSerialize(using = ToStringSerializer.class)
                        ObjectId gameId,
                       String playerName,
                       String playerEmail,
                       Date startedAt,
                       GameStatus status,
                       List<CardDTO> playerHand,
                       List<CardDTO> dealerHand)         {


    // Factory method that accepts the player directly
    public static GameDTO gameDtoFromGameAndPlayer(Game game, PlayerDTO playerDTO){
        List<CardDTO> playerHand = game.getPlayerHand()
                .stream().map(card -> new CardDTO(card.getCardSuit().name(), card.getCardRank().name()))
                .toList();

        List<CardDTO> dealerHand = game.getDealerHand()
                .stream().map(card -> new CardDTO(card.getCardSuit().name(), card.getCardRank().name()))
                .toList();

        return new GameDTO(

                game.getGameId(),
                playerDTO.playerName(),
                playerDTO.playerEmail(),
                game.getStartedAt(),
                game.getStatus(),
                playerHand,
                dealerHand
        );
    }


    public static Mono<GameDTO> gameDtoFromGameAndPlayerMono(Game game, Mono<PlayerDTO> playerMono){
        return playerMono.map(player -> gameDtoFromGameAndPlayer(game, player));
    }

}




