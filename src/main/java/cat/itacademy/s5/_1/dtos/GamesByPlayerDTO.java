package cat.itacademy.s5._1.dtos;

import java.util.List;

public record GamesByPlayerDTO(String playerName,
                               String playerEmail,

                               List<GameSummaryDTO> playerGames) {}
