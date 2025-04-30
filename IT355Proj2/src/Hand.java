import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Hand {
    private List<Card> cards;
    private String identity;
    public boolean busted = false;
    public boolean blackjack = false;
    public boolean standing = false;

    /**
     * Create a new hand
     * @param identity The name of this new hand (ex. "Dealer", "Player 1")
     */
    public Hand(String identity) {
        cards = new ArrayList<Card>();
        this.identity = identity;
    }

    /**
     * Get the identity of this hand
     * @return The string representing the hand owner
     */
    public String getIdentity() {
        return identity;
    }

    /**
     * Get the size (quantity) of this hand
     * @return The size of this hand
     */
    public int getHandSize() {
        return cards.size();
    }

    /**
     * Clones the card found at the input index
     * Prerequisite: The index is not out of bounds!
     * @param index The requested index to be cloned
     * @return A clone of the card
     */
    public Card getCardAtIndex(int index) {
        if(index >= 0 && index < cards.size()) {
            Card returnCard = cards.get(index);
            try{
                return returnCard.clone(); 
            }
            catch (CloneNotSupportedException e){
                //base case if for some reason doesn't clone properly
                return cards.get(index);
            }
        } else { // index is out of bounds!
            return new Card().setRank(Card.Rank.Ace).setSuit(Card.Suit.Hearts);
        }
    }

    /**
     * Add a card into this hand
     * @param newCard The card object to add
     */
    public void insertCard(Card newCard) {
        cards.add(newCard);
    }

    /**
     * Remove a card from this hand at the specified index
     * Prerequisite: The index is not out of bounds!
     * @param index The requested index to be removed
     */
    public void removeCard(int index) {
        if(index < getHandSize()) {
            cards.remove(index);
        }
    }
    
    /**
     * Get the value of this hand
     * @return The highest possible value of the hand if it is not busted, otherwise the lowest possible value
     */
    public int getHandValue() {
        List<Integer> possibleValues = new ArrayList<Integer>();
        possibleValues.add(0);
        // aces are the only cards with more than 1 possible value.
        // loop through all aces.
        // the size of the possible values of a hand is equal to the number of aces it has plus 1.
        for(Card card : cards) {
            if(card.getRank() == Card.Rank.Ace) {
                int startingSize = possibleValues.size();
                for(int i = 0; i < startingSize; i++) {
                    possibleValues.add(possibleValues.get(i) + 11);
                    possibleValues.set(i, possibleValues.get(i) + 1);
                }
            }
        }
        // loop through all cards, for all non-aces add their values
        for(Card card : cards) {
            if(card.getRank() != Card.Rank.Ace) {
                for(int i = 0; i < possibleValues.size(); i++) {
                    possibleValues.set(i, possibleValues.get(i) + card.getValue());
                }
            }
        }
        // sort possible values (ascending)
        Collections.sort(possibleValues);
        int result = possibleValues.get(possibleValues.size() - 1); // Start with highest
        for(int i = possibleValues.size() - 1; i >= 0; i--) {
            result = possibleValues.get(i);
            if(result <= 21) {
                // we did not bust
                break;
            }
        }
        return result;
    }
}
