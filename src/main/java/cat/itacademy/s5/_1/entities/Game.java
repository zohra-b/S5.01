package cat.itacademy.s5._1.entities;

import cat.itacademy.s5._1.entities.enums.GameStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.bson.types.ObjectId;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "games")
public class Game {
    private ObjectId gameId;
    private String playerId;
    private Date startedAt;
    private Date endedAt;
    private GameStatus status;
    private Deck deck = new Deck();
    private List<Card> playerHand;
    private List<Card> dealerHand;


}
