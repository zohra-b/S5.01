package cat.itacademy.s5._1.dtos;

import cat.itacademy.s5._1.entities.Player;

import java.util.UUID;

public record PlayerDTO(String playerId,
                        String playerName,
                        String playerEmail,
                        int totalScore) {


    public static PlayerDTO fromEntity(Player player) {
        return new PlayerDTO(
                player.getPlayerId(),
                player.getPlayerName(),
                player.getPlayerEmail(),
                player.getTotalScore());
    }

}