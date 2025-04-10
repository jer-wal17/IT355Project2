import java.util.Random;
import java.util.Stack;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class Deck {
    private Stack<Card> cards;
    private Random random; // make a more complex pseudorandom number if that's one of the rules

    public Deck() {
        cards = new Stack<Card>();
        random = new Random();
    }

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

    // Standard Fisher-Yates shuffle
    private void fisherYatesShuffle(List<Card> list) {
        for (int i = list.size() - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            Collections.swap(list, i, j);
        }
    }

    // Simplified riffle shuffle
    private void riffleShuffle(List<Card> list) {
        // Split the deck roughly in half (with slight randomness)
        int splitPoint = list.size() / 2 + random.nextInt(5) - 2;

        List<Card> left = new ArrayList<>(list.subList(0, splitPoint));
        List<Card> right = new ArrayList<>(list.subList(splitPoint, list.size()));

        list.clear();

        // Interleave the cards with some randomness
        while (!left.isEmpty() || !right.isEmpty()) {
            // Prefer the pile with more cards remaining
            if (!left.isEmpty() && (right.isEmpty() || left.size() > right.size() || random.nextBoolean())) {
                list.add(left.remove(0));
            } else {
                list.add(right.remove(0));
            }
        }
    }

    public Card drawCard() {
        if(!cards.isEmpty()) {
            return cards.pop();
        } else {
            throw new java.util.EmptyStackException();  
        }
    }

    public void insertCard(Card newCard) {
        cards.push(newCard);
    }
}
