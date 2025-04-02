import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

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
            List<Hand> playerHands = new ArrayList<Hand>();
            playerHands.add(new Hand());
            Hand dealerHand = new Hand();
            for(int i = 0; i < 2; i++) {
                dealerHand.insertCard(deck.drawCard());
            }
            for(Hand playerHand : playerHands) {
                for(int i = 0; i < 2; i++) {
                    playerHand.insertCard(deck.drawCard());
                }
            }

            // print hands (only show the first card of the dealer)
            System.out.println("Revealed dealer card:");
            System.out.println(dealerHand.getCardAtIndex(0).getDisplayText() + "\n");

            boolean exitApp = false;
            boolean revealDealer = false;

            // check if the player has blackjack
            for(int i = 0; i < playerHands.size(); i++) {
                Hand playerHand = playerHands.get(i);
                int handValue = playerHand.getHandValue();
                if(handValue == 21) {
                    System.out.println("Your cards:");
                    printHand(playerHand);

                    System.out.println("Blackjack! You Win!");
                    exitApp = true;
                } else if(playerHand.getCardAtIndex(0).getRank() == playerHand.getCardAtIndex(1).getRank()) {
                    // player can split
                    System.out.println("Your cards:");
                    printHand(playerHand);

                    System.out.println("You have the opportunity to split your hand! Type 1 to split. Type anything else to ignore.");
                    if(in.hasNextInt() && in.nextInt() == 1) {
                        Hand newHand = new Hand();
                        playerHands.add(newHand);
                        newHand.insertCard(playerHand.getCardAtIndex(1));
                        playerHand.removeCard(1);
                        newHand.insertCard(deck.drawCard());
                        playerHand.insertCard(deck.drawCard());
                    }
                    in.nextLine();
                }
            }
            if(!exitApp && dealerHand.getHandValue() == 21) {
                System.out.println("The dealer has a blackjack! You lose!");
                exitApp = true;
            }
            
            // game loop
            while(!(exitApp || revealDealer)) {
                for(Hand playerHand : playerHands) {
                    System.out.println("Your cards:");
                    printHand(playerHand);

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
                            int handValue = playerHand.getHandValue();
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
                int handValue = dealerHand.getHandValue();
                if(handValue > 21) {
                    System.out.println("The dealer busted! You win!");
                }
                else if(handValue == 21 && dealerHand.getHandSize() == 2) {
                    System.out.println("The dealer has a blackjack! You lose!");
                }
                else {
                    for(Hand playerHand : playerHands) {
                        if(handValue > playerHand.getHandValue()) {
                            System.out.println("The dealer stands.\nYou lose!");
                        }
                        else if(handValue < playerHand.getHandValue()) {
                            System.out.println("The dealer stands.\nYou win!");
                        }
                        else if(handValue == playerHand.getHandValue()) {
                            System.out.println("The dealer stands.\nIt's a tie!");
                        }
                    }
                }
            }
            System.out.println("\nType 0 to quit. Type anything else to continue.");
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
