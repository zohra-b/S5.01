package cat.itacademy.s5._1.entities;

import cat.itacademy.s5._1.entities.enums.CardRank;
import cat.itacademy.s5._1.entities.enums.CardSuit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor

public class Deck {
    private List<Card> deck;

    public Deck() {
        this.deck = loadDeck();
        shuffleDeck();
    }

    public List<Card> loadDeck() {
        deck = new ArrayList<>();
        for (CardSuit cardSuit : CardSuit.values() ){
            for (CardRank cardRank : CardRank.values()){
                deck.add(new Card(cardSuit,cardRank));
            }
        }
        return deck;
    }

    public void shuffleDeck(){
        Collections.shuffle(deck);
    }

}
