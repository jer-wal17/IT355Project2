import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        boolean keepPlaying = true;
        while(keepPlaying) {
            Deck deck = DeckBuilder.createStandardDeck();
            deck.shuffle();
            Hand playerHand = new Hand();
            Hand dealerHand = new Hand();

            for(int i = 0; i < 2; i++) {
                dealerHand.insertCard(deck.drawCard());
            }
            for(int i = 0; i < 2; i++) {
                playerHand.insertCard(deck.drawCard());
            }

            System.out.println("Revealed dealer card:");
            System.out.println(dealerHand.getCardAtIndex(0).getDisplayText() + "\n");
            System.out.println("Your cards:");
            printHand(playerHand);

            boolean exitApp = false;
            boolean revealDealer = false;

            int handValue = playerHand.getHandValue();
            if(handValue == 21) {
                System.out.println("Blackjack! You Win!");
                exitApp = true;
            }

            while(!(exitApp || revealDealer)) {
                System.out.println("Type 1 to hit and 2 to hold.");
                int choice = -1;
                if (in.hasNextInt()) {
                    choice = in.nextInt();
                }
                switch(choice) {
                    case 1:
                        playerHand.insertCard(deck.drawCard());
                        System.out.println("Your cards:");
                        printHand(playerHand);
                        handValue = playerHand.getHandValue();
                        if(handValue > 21) {
                            System.out.println("You busted!");
                            exitApp = true;
                        }
                        else if(handValue == 21) {
                            System.out.println("You got 21!");
                            revealDealer = true;
                        }
                        break;
                    case 2:
                        revealDealer = true;
                        System.out.println("");
                        break;
                    default:
                        System.out.println("Invalid choice.");
                        break;
                }
            }
            if(revealDealer) {
                System.out.println("Dealer cards:");
                printHand(dealerHand);
                Thread.sleep(1000);
                while(dealerHand.getHandValue() < 17)
                {
                    System.out.println("The dealer draws a card. \n");
                    Thread.sleep(1000);
                    dealerHand.insertCard(deck.drawCard());
                    System.out.println("Dealer cards:");
                    printHand(dealerHand);
                    Thread.sleep(1000);
                }
                handValue = dealerHand.getHandValue();
                if(handValue > 21) {
                    System.out.println("The dealer busted! You win!");
                }
                else if(handValue == 21 && dealerHand.getHandSize() == 2) {
                    System.out.println("The dealer has a blackjack! You lose!");
                }
                else if(handValue > playerHand.getHandValue()) {
                    System.out.println("You lose!");
                }
                else if(handValue < playerHand.getHandValue()) {
                    System.out.println("You win!");
                }
                else if(handValue == playerHand.getHandValue()) {
                    System.out.println("It's a tie!");
                }
            }
            System.out.println("\nPress 0 to quit. Press anything else to continue.");
            keepPlaying = !(in.hasNextInt() && in.nextInt() == 0);
            if(keepPlaying) {
                System.out.println("\nStarting new game!\n");
            }
        }
        in.close();
    }

    private static void printHand(Hand hand) {
        for(int i = 0; i < hand.getHandSize(); i++) {
            System.out.println(hand.getCardAtIndex(i).getDisplayText());
        }
        System.out.println(hand.getHandValue());
        System.out.println("");
    }
}
