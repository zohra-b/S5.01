package cat.itacademy.s5._1.dtos;

import cat.itacademy.s5._1.entities.Game;
import cat.itacademy.s5._1.entities.enums.GameStatus;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.util.Date;

import org.bson.types.ObjectId;

public record GameSummaryDTO(
                                @JsonSerialize(using = ToStringSerializer.class)
                                ObjectId gameId,
                             Date startedAt,
                             GameStatus status,
                             Date endedAt
                             ) {

    public static GameSummaryDTO gameSummaryDtoFromGame(Game game){
        Date actualEnd = null;
        actualEnd = (game.getStatus() == GameStatus.IN_PROGRESS) ? null : game.getEndedAt();
        return new GameSummaryDTO(
                game.getGameId(),
                game.getStartedAt(),
                game.getStatus(),
                actualEnd
        );
    }
}
