package cat.itacademy.s5._1.entities.enums;

public enum CardRank {
    A("A", 11),    // Often 1 or 11 in Blackjack – we'll handle that in logic
    TWO("2", 2),
    THREE("3", 3),
    FOUR("4", 4),
    FIVE("5", 5),
    SIX("6", 6),
    SEVEN("7", 7),
    EIGHT("8", 8),
    NINE("9", 9),
    TEN("10", 10),
    J("J", 10),
    Q("Q", 10),
    K("K", 10);

    private final String cardSymbol;
    private final int cardValue;

    CardRank(String cardSymbol, int cardValue){
        this.cardSymbol = cardSymbol;
        this.cardValue = cardValue;
    }

    public String getCardSymbol() {
        return cardSymbol;
    }

    public int getCardValue() {
        return cardValue;
    }
}


