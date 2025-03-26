public class DeckBuilder {
    public static Deck createStandardDeck() {
        Deck newDeck = new Deck();
        Card.Rank[] ranks = Card.Rank.values();
        Card.Suit[] suits = Card.Suit.values();
        for(int i = 0; i < ranks.length; i++) {
            for(int j = 0; j < suits.length; j++) {
                newDeck.insertCard(new Card().setRank(ranks[i]).setSuit(suits[j]));
            }
        }
        return newDeck;
    }
}
