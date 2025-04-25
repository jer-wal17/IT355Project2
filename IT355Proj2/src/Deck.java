import java.util.Stack;
import java.util.Collections;
import java.util.List;
import java.security.SecureRandom;
import java.util.ArrayList;

public class Deck {
    private Stack<Card> cards;
    private SecureRandom random; // use secure random instead of reg random for seeding

    /**
     * Create a new empty Deck
     */
    public Deck() {
        cards = new Stack<Card>();
        random = new SecureRandom();
        //seed it
        random.nextBytes(new byte[16]);
    }

    /**
    * Shuffles the deck of cards using a combination of Fisher-Yates and riffle shuffles.
    * If there are fewer than 2 cards in the deck, the method returns immediately as no shuffling is needed.
    * The shuffling process consists of:
    * - 5 iterations of Fisher-Yates shuffle
    * - 5 iterations of riffle shuffle
    * The original card collection is cleared and replaced with the shuffled cards.
    */
    public void shuffle() {
        if (cards.size() < 2)
            return;
        List<Card> cardList = new ArrayList<>(cards);
        for (int i = 0; i < 5; i++) {
            fisherYatesShuffle(cardList);
        }
        for (int i = 0; i < 5; i++) {
            riffleShuffle(cardList);
        }
        cards.clear();
        cards.addAll(cardList);
    }

    /**
    * Performs a Fisher-Yates shuffle (also known as the Knuth shuffle) on the given list of cards.
    * This algorithm produces an unbiased permutation of the list, meaning all permutations are equally likely.
    * The shuffle is performed in-place by iterating through the list from last to first element,
    * swapping each element with another randomly chosen element from the remaining unshuffled portion.
    *
    * @param list The list of cards to be shuffled (will be modified in-place)
    */
    private void fisherYatesShuffle(List<Card> list) {
        for (int i = list.size() - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            Collections.swap(list, i, j);
        }
    }

    /**
    * Performs a riffle shuffle (also known as a dovetail shuffle) on the given list of cards.
    * The deck is split at a randomly determined point near the middle (with some variation),
    * then the two halves are interleaved in a random fashion to simulate a real-world riffle shuffle.
    * This method modifies the input list in-place.
    *
    * @param list The list of cards to be shuffled (will be modified in-place)
    */
    private void riffleShuffle(List<Card> list) {
        int splitPoint = list.size() / 2 + random.nextInt(5) - 2;
        List<Card> left = new ArrayList<>(list.subList(0, splitPoint));
        List<Card> right = new ArrayList<>(list.subList(splitPoint, list.size()));
        list.clear();
        while (!left.isEmpty() || !right.isEmpty()) {
            if (!left.isEmpty() && (right.isEmpty() || left.size() > right.size() || random.nextBoolean())) {
                list.add(left.remove(0));
            } else {
                list.add(right.remove(0));
            }
        }
    }

    /**
     * Pop a card off this deck
     * @return The drawn Card object
     */
    public Card drawCard() {
        if(!cards.isEmpty()) {
            return cards.pop();
        } else {
            throw new java.util.EmptyStackException();  
        }
    }

    /**
     * Push a card onto this deck
     * @param newCard The card object to add
     */
    public void insertCard(Card newCard) {
        cards.push(newCard);
    }
}
