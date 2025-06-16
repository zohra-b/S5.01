package cat.itacademy.s5._1.entities;

import cat.itacademy.s5._1.entities.enums.CardRank;
import cat.itacademy.s5._1.entities.enums.CardSuit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Card {
    private CardSuit cardSuit;
    private CardRank cardRank;


    public Card(CardSuit cardSuit, CardRank cardRank) {
        this.cardRank = cardRank;
        this.cardSuit = cardSuit;
    }
}
