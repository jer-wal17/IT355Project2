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

    public int getValue() {
        switch(rank) {
            case King: case Queen: case Jack: case Ten:
                return 10;
            case Nine:
                return 9;
            case Eight:
                return 8;
            case Seven:
                return 7;
            case Six:
                return 6;
            case Five:
                return 5;
            case Four:
                return 4;
            case Three:
                return 3;
            case Two:
                return 2;
            case Ace:
                return 11;
            default:
                return 0;
        }
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
