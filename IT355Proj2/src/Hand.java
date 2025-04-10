import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Hand {
    private List<Card> cards;
    private String identity;
    public boolean busted = false;
    public boolean blackjack = false;
    public boolean standing = false;

    public Hand(String identity) {
        cards = new ArrayList<Card>();
        this.identity = identity;
    }

    public String getIdentity() {
        return identity;
    }

    public int getHandSize() {
        return cards.size();
    }

    public Card getCardAtIndex(int index) {
        if(index >= 0 && index < cards.size()) {
            return cards.get(index);
        } else {
            return new Card().setRank(Card.Rank.Ace).setSuit(Card.Suit.Hearts);
        }
    }

    public void insertCard(Card newCard) {
        cards.add(newCard);
    }

    public void removeCard(int index) {
        if(index < getHandSize()) {
            cards.remove(index);
        }
    }
    
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
        /*
        int result = -1;
        // sort possible values (ascending)
        Collections.sort(possibleValues);
        for(int i = 0; i < possibleValues.size(); i++) {
            int currentValue = possibleValues.get(i);
            if(currentValue > 21 && result == -1) {
                // we busted, return
                result = currentValue;
                break;
            }
            else if(currentValue < 22) {
                // we did not bust
                result = currentValue;
            }
        }
        return result;
        */
        // changed this because I saw it was possibly going to give a always false but if it doesn't work than change it back and it's not a problem
        int result = possibleValues.get(possibleValues.size()-1); // Start with highest
        for(int i = possibleValues.size()-1; i >= 0; i--) {
            int currentValue = possibleValues.get(i);
            if(currentValue <= 21) {
                result = currentValue;
                break;
            }
        }
        return result;
    }
}
