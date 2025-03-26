import java.util.Random;
import java.util.Stack;

public class Deck {
    private Stack<Card> cards;

    public Deck() {
        cards = new Stack<Card>();
    }

    public void shuffle() {
        Random random = new Random();
        // brute force, remove and insert cards x amount of times
        int x = 200;
        for(int i = 0; i < x; i++) {
            int randomInt = random.nextInt(cards.size());
            Card shuffledCard = cards.get(randomInt);
            cards.remove(randomInt);
            randomInt = random.nextInt(cards.size());
            cards.insertElementAt(shuffledCard, randomInt);
        }
    }

    public Card drawCard() {
        return cards.pop();
    }

    public void insertCard(Card newCard) {
        cards.push(newCard);
    }
}
