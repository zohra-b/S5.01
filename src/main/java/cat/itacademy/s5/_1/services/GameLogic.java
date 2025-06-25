package cat.itacademy.s5._1.services;

import cat.itacademy.s5._1.entities.Card;
import cat.itacademy.s5._1.entities.Game;
import cat.itacademy.s5._1.entities.enums.CardRank;
import cat.itacademy.s5._1.entities.enums.GameStatus;

import java.util.List;

public class GameLogic {

    public int calculateHandValue(List<Card> hand) {
        int value = 0;
        int aceCount = 0;

        for (Card card : hand) {
            value += card.getCardRank().getCardValue();
            if (card.getCardRank() == CardRank.A) {
                aceCount++;
            }
        }

     while (value > 21 && aceCount > 0) {
            value -= 10;
            aceCount--;
        }
        return value;
    }

    public GameStatus evaluateInitialStatus(Game game){
        int playerHandValue = calculateHandValue(game.getPlayerHand());
        int dealerHandValue = calculateHandValue(game.getDealerHand());

        if (playerHandValue == 21 && dealerHandValue == 21)
            return GameStatus.TIE;
        else if (playerHandValue == 21 )
            return GameStatus.PLAYER_BLACKJACK;
        else if (dealerHandValue == 21)
            return GameStatus.DEALER_BLACKJACK;
        else
            return GameStatus.IN_PROGRESS;

    }

    public GameStatus evaluateFinalGameStatus(Game game){
        int playerHandValue = calculateHandValue(game.getPlayerHand());
        int dealerHandValue = calculateHandValue(game.getDealerHand());

        if (playerHandValue > 21)
            return GameStatus.DEALER_WINS;
        else if (playerHandValue > dealerHandValue || dealerHandValue > 21)
            return GameStatus.PLAYER_WINS;
        else
            return GameStatus.TIE;
    }

    public void dealerLogic(Game game){
        int dealerHandValue = calculateHandValue(game.getDealerHand());
        while (dealerHandValue < 17)
            game.getDealerHand().add(game.getDeck().drawCard());

    }

}
