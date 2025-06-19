package cat.itacademy.s5._1.dtos;


import cat.itacademy.s5._1.entities.Game;

import java.util.List;

import org.bson.types.ObjectId;

public record GamesByPlayerDTO(String playerName,
                               String playerEmail,
                               List<GameSummaryDTO> playerGames
                               ) {

}
