import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        boolean keepPlaying = true;
        while(keepPlaying) {
            System.out.println("Welcome to Blackjack!\nThe dealer stands on 17.\n");

            // create and shuffle deck
            Deck deck = DeckBuilder.createStandardDeck();
            deck.shuffle();

            // create hands and draw cards for them
            Hand playerHand = new Hand();
            Hand dealerHand = new Hand();
            for(int i = 0; i < 2; i++) {
                dealerHand.insertCard(deck.drawCard());
            }
            for(int i = 0; i < 2; i++) {
                playerHand.insertCard(deck.drawCard());
            }

            // print hands (only show the first card of the dealer)
            System.out.println("Revealed dealer card:");
            System.out.println(dealerHand.getCardAtIndex(0).getDisplayText() + "\n");
            System.out.println("Your cards:");
            printHand(playerHand);

            boolean exitApp = false;
            boolean revealDealer = false;

            // check if the player has blackjack
            int handValue = playerHand.getHandValue();
            if(handValue == 21) {
                System.out.println("Blackjack! You Win!");
                exitApp = true;
            }
            
            // game loop
            while(!(exitApp || revealDealer)) {
                System.out.println("Type 1 to hit and 2 to stand.");
                int choice = -1;
                if (in.hasNextInt()) {
                    choice = in.nextInt();
                }
                in.nextLine();
                switch(choice) {
                    case 1: // hit
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
                    case 2: // stand
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
                while(dealerHand.getHandValue() < 17) // stand on 17 or more
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
                    System.out.println("The dealer stands.\nYou lose!");
                }
                else if(handValue < playerHand.getHandValue()) {
                    System.out.println("The dealer stands.\nYou win!");
                }
                else if(handValue == playerHand.getHandValue()) {
                    System.out.println("The dealer stands.\nIt's a tie!");
                }
            }
            System.out.println("\nPress 0 to quit. Press anything else to continue.");
            keepPlaying = !(in.hasNextInt() && in.nextInt() == 0);
            in.nextLine();
            System.out.println("");
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
