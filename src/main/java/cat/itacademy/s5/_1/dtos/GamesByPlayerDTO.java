package cat.itacademy.s5._1.dtos;


import cat.itacademy.s5._1.entities.Game;
import cat.itacademy.s5._1.entities.Player;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Flux;

import java.util.List;

import org.bson.types.ObjectId;

public record GamesByPlayerDTO(String playerName,
                               String playerEmail,
                               List<GameSummaryDTO> playerGames
                               ) {

//    public static GamesByPlayerDTO(Player player){
//        return new GamesByPlayerDTO(
//                player.getPlayerName()
//                player.getPlayerName(),
//
//
//        )
//    }

//    public static Flux<GameSummaryDTO> gamesList(Player player){
//        return new Flux<GameSummaryDTO>() {
//            Flux.
//
//            }
//        }

 //   }

}
