package cat.itacademy.s5._1.dtos;

import java.util.List;

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
