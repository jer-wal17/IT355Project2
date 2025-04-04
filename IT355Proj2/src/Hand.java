import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Hand {
    private List<Card> cards;

    public Hand() {
        cards = new ArrayList<Card>();
    }

    public int getHandSize() {
        return cards.size();
    }

    public Card getCardAtIndex(int index) {
        if(index < cards.size()) {
            return cards.get(index);
        }
        else {
            return null;
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
                    possibleValues.set(i, possibleValues.get(i) + getCardValue(card));
                }
            }
        }
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
    }

    private int getCardValue(Card card) {
        switch(card.getRank()) {
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
}
