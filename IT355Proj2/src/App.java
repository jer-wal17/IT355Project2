import java.util.Scanner;
import java.util.logging.*;
import java.util.List;
import java.io.IOException;
import java.util.ArrayList;

public class App {
    private static Logger logger = Logger.getLogger(Class.class.getName());

    public static void main(String[] args) throws Exception {
        // Logging set up
        FileHandler fh = null;
        try {
            fh = new FileHandler("BlackjackLog.txt");
            fh.setFormatter(new SimpleFormatter());
            LogManager.getLogManager().reset();
            logger.addHandler(fh);
            logger.setLevel(Level.FINE);

            Scanner in = new Scanner(System.in);
            boolean keepPlaying = true;
            while (keepPlaying) {
                System.out.println("Welcome to Blackjack!\nThe dealer stands on 17.\n");

                // create and shuffle deck
                Deck deck = DeckBuilder.createStandardDeck();
                deck.shuffle();

                int playerCount = 0;
                while (playerCount < 1 || playerCount > 10) {
                    System.out.println("Enter the number of players (1-10):");
                    if (in.hasNextInt()) {
                        playerCount = in.nextInt();
                    } else {
                        System.out.println("Please enter a number between 1 and 10.");
                    }
                    in.nextLine();
                }
                // create hands and draw cards for them
                List<Hand> playerHands = new ArrayList<Hand>();
                for (int i = 1; i <= playerCount; i++) {
                    playerHands.add(new Hand("Player " + i));
                }
                Hand dealerHand = new Hand("Dealer");
                try {
                    for (int i = 0; i < 2; i++) {
                        dealerHand.insertCard(deck.drawCard());
                    }
                } catch (java.util.EmptyStackException e) {
                    handleEmptyDeck(deck);
                    dealerHand.insertCard(deck.drawCard());
                }
                for (Hand playerHand : playerHands) {
                    for (int i = 0; i < 2; i++) {
                        try {
                            playerHand.insertCard(deck.drawCard());
                        } catch (java.util.EmptyStackException e) {
                            handleEmptyDeck(deck);
                            playerHand.insertCard(deck.drawCard());
                        }
                    }
                }

                // only show the first card of the dealer
                System.out.println("Revealed dealer card:");
                System.out.println(dealerHand.getCardAtIndex(0).getDisplayText() + "\n");

                if (dealerHand.getHandValue() == 21) {
                    dealerHand.blackjack = true;
                    System.out.println("The dealer has a blackjack!");
                }

                // game loop
                for (int i = 0; i < playerHands.size(); i++) {
                    // check if the player has blackjack or can split
                    evaluateSpecialHands(playerHands, i, deck, in);
                    if (dealerHand.blackjack) {
                        // dealer has blackjack
                        continue;
                    }

                    Hand playerHand = playerHands.get(i);
                    printHand(playerHand);
                    while (!(playerHand.busted || playerHand.blackjack || playerHand.standing)) {
                        System.out.println("Type 1 to hit and 2 to stand.");
                        int choice = -1;
                        if (in.hasNextInt()) {
                            choice = in.nextInt();
                            if(choice < 1 || choice > 2) {
                                choice = -1;
                                System.out.println("Invalid choice. Valid options are 1 or 2");
                            }
                        }
                        in.nextLine();
                        switch (choice) {
                            case 1: // hit
                                logger.fine(playerHand.getIdentity() + " hit");
                                try {
                                    playerHand.insertCard(deck.drawCard());
                                } catch (java.util.EmptyStackException e) {
                                    handleEmptyDeck(deck);
                                    playerHand.insertCard(deck.drawCard());
                                }
                                printHand(playerHand);
                                int handValue = playerHand.getHandValue();
                                if (handValue > 21) {
                                    logger.info(playerHand.getIdentity() + " lost");
                                    System.out.println("You busted!");
                                    playerHand.busted = true;
                                } else if (handValue == 21) {
                                    System.out.println("You got 21!");
                                    playerHand.standing = true;
                                }
                                break;
                            case 2: // stand
                                logger.fine(playerHand.getIdentity() + " stood\n");
                                playerHand.standing = true;
                                break;
                            default:
                                System.out.println("Invalid choice.");
                                break;
                        }
                    }
                }

                /* Dealer's turn */
                printHand(dealerHand);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                while (dealerHand.getHandValue() < 17) // stand on 17 or more
                {
                    System.out.println("The dealer draws a card. \n");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    try {
                        dealerHand.insertCard(deck.drawCard());
                    } catch (java.util.EmptyStackException e) {
                        handleEmptyDeck(deck);
                        dealerHand.insertCard(deck.drawCard());
                    }
                    printHand(dealerHand);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                int handValue = dealerHand.getHandValue();
                if (handValue > 21) {
                    dealerHand.busted = true;
                    System.out.println("The dealer busted!");
                } else {
                    System.out.println("The dealer stands.");
                }
                printGameResults(dealerHand, playerHands);

                System.out.println("\nType 0 to quit. Type anything else to continue.");
                keepPlaying = !(in.hasNextInt() && in.nextInt() == 0);
                in.nextLine();
                System.out.println("");
            }
            in.close();
        } catch (IOException e) {
            System.err.println("Failed to initialize logging: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Critical error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (fh != null) {
                fh.close();
            }
        }
    }

    /**
    * Helper method for deck recovery that handles the case when a deck becomes empty.
    * When invoked, this method will:
    * - Print a notification message indicating the deck is being reshuffled
    * - Creates a new standard 52-card deck using {@link DeckBuilder#createStandardDeck()}
    * - Shuffles the newly created deck using {@link Deck#shuffle()}
    *
    * @param deck The empty deck that needs to be replenished (will be replaced with a new deck)
    * @return A new, shuffled standard deck to replace the empty one
    */
    private static void handleEmptyDeck(Deck deck) {
        System.out.println("Reshuffling empty deck...");
        deck = DeckBuilder.createStandardDeck();
        deck.shuffle();
    }

    private static void printHand(Hand hand) {
        System.out.println("");
        System.out.println(hand.getIdentity() + ":");
        for (int i = 0; i < hand.getHandSize(); i++) {
            System.out.println(hand.getCardAtIndex(i).getDisplayText());
        }
        System.out.println(hand.getHandValue());
        System.out.println("");
    }

    private static void evaluateSpecialHands(List<Hand> playerHands, int index, Deck deck, Scanner in) 
    {
        Hand playerHand = playerHands.get(index);
        int handValue = playerHand.getHandValue();
        if (handValue == 21) {
            playerHand.blackjack = true;
            printHand(playerHand);

            System.out.println(playerHand.getIdentity() + " has blackjack!");
        } else if (playerHand.getCardAtIndex(0).getValue() == playerHand.getCardAtIndex(1).getValue()) {
            // player can split
            printHand(playerHand);

            System.out.println(
                    "You have the opportunity to split your hand! Type 1 to split. Type anything else to ignore.");
            if (in.hasNextInt() && in.nextInt() == 1) {
                logger.info(playerHand.getIdentity() + " split");
                Hand newHand = new Hand(playerHand.getIdentity() + " split hand");
                playerHands.add(index + 1, newHand);
                newHand.insertCard(playerHand.getCardAtIndex(1));
                playerHand.removeCard(1);
                newHand.insertCard(deck.drawCard());
                playerHand.insertCard(deck.drawCard());
            }
            in.nextLine();
        }
    }

    private static void printGameResults(Hand dealerHand, List<Hand> playerHands) {
        int handValue = dealerHand.getHandValue();
        if (handValue > 21) {
            dealerHand.busted = true;
        }
        for(Hand playerHand : playerHands) {
            System.out.println(playerHand.getIdentity() + ":");
            if(playerHand.busted) {
                System.out.println("You busted! You lose!");
                logger.info(playerHand.getIdentity() + " lost");
            }
            else if(playerHand.blackjack) {
                System.out.println("You have a blackjack! You win!");
                logger.info(playerHand.getIdentity() + " won");
            }
            else if(dealerHand.busted) {
                System.out.println("The dealer busted! You win!");
                logger.info(playerHand.getIdentity() + " won");
            }
            else if(dealerHand.blackjack) {
                System.out.println("The dealer has a blackjack! You lose!");
                logger.info(playerHand.getIdentity() + " lost");
            }
            else {
                if (handValue > playerHand.getHandValue()) {
                    System.out.println("Your hand is lower than the dealer's. You lose!");
                    logger.info(playerHand.getIdentity() + " lost");
                } else if (handValue < playerHand.getHandValue()) {
                    System.out.println("Your hand is higher than the dealer's. You win!");
                    logger.info(playerHand.getIdentity() + " won");
                } else if (handValue == playerHand.getHandValue()) {
                    System.out.println("Your hand is as high as the dealer's. You tied!");
                    logger.info(playerHand.getIdentity() + " tied");
                }
            }
        }
    }
}
