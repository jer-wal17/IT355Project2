public class DeckBuilder {
    /**
     * Build a fresh standard 52-card deck
     * @return The new Deck object
     */
    public static Deck createStandardDeck() {
        Deck newDeck = new Deck();
        Card.Rank[] ranks = Card.Rank.values();
        Card.Suit[] suits = Card.Suit.values();
        // add all possible combinations of ranks and suits
        for(int i = 0; i < ranks.length; i++) {
            for(int j = 0; j < suits.length; j++) {
                newDeck.insertCard(new Card().setRank(ranks[i]).setSuit(suits[j]));
            }
        }
        return newDeck;
    }
}
