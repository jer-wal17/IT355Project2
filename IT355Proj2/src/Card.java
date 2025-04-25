public class Card implements Cloneable {
    private Rank rank;
    private Suit suit;

    /**
     * Get the display text for this card
     * @return A string representation of this card
     */
    public String getDisplayText() {
        return rank.name() + " of " + suit.name();
    }

    /**
     * Set the rank of this card
     * @param rank Enum value representing the new rank of this card
     * @return This card object
     */
    public Card setRank(Rank rank) {
        this.rank = rank;
        return this;
    }

    /**
     * Get the rank of this card
     * @return The Enum value representing the rank of this card
     */
    public Rank getRank() {
        return rank;
    }

    /**
     * Set the suit of this card
     * @param suit Enum value representing the new suit of this card
     * @return This card object
     */
    public Card setSuit(Suit suit) {
        this.suit = suit;
        return this;
    }
    
    /**
     * Get the suit of this card
     * @return The Enum value representing the suit of this card
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * Get the hand value of this card
     * Do not use for Ace, the Ace can have 2 different values (1 or 11)!
     * @return The hand value of this card
     */
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

    public Card clone() throws CloneNotSupportedException {
        Card clonedCard = (Card)super.clone();
        return clonedCard;
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
