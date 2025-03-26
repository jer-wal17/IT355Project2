public class Card {
    private Rank rank;
    private Suit suit;

    public String getDisplayText() {
        return rank.name() + " of " + suit.name();
    }

    public Card setRank(Rank rank) {
        this.rank = rank;
        return this;
    }

    public Rank getRank() {
        return rank;
    }

    public Card setSuit(Suit suit) {
        this.suit = suit;
        return this;
    }
    
    public Suit getSuit() {
        return suit;
    }

    public enum Suit {
        Hearts,
        Diamonds,
        Spades,
        Clubs
    }

    public enum Rank {
        King,
        Queen,
        Jack,
        Ten,
        Nine,
        Eight,
        Seven,
        Six,
        Five,
        Four,
        Three,
        Two,
        Ace
    }
}
