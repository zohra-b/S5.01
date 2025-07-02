package cat.itacademy.s5._1.entities;

import cat.itacademy.s5._1.entities.enums.CardRank;
import cat.itacademy.s5._1.entities.enums.CardSuit;
import cat.itacademy.s5._1.exceptions.ValueOutOfRangeException;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data


public class Deck {
    private List<Card> deck;

    public Deck(List<Card> deck) {
        this.deck = deck;
    }

    // Obligatoire pour la désérialisation par Spring Data MongoDB
    public Deck() {}

    public void initialize() {
        this.deck = new ArrayList<>();
        loadDeck();
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



    public boolean isEmpty() {
        return deck == null || deck.isEmpty();
    }

    public Card drawCard(){
        if (isEmpty()){
            throw new ValueOutOfRangeException("The draw is empty");
        }
        return deck.remove(0);
    }


}
