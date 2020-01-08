import java.util.ArrayList;
import java.util.Random;

/**
 * Deck models a deck of 52 Anglo-American playing cards.
 * 
 * @author Lynn Marshall 
 * @version 1.1 October 11, 2012
 *
 * @author Ali Fahd 
 * @version 1.2 October 15, 2019
 *
 */
public class Deck
{
    /** 
     * The cards are stored in a linked-list implementation of the
     * List collection.
     */
    private ArrayList<Card> cards;
    
    /** Lowest ranking card (the ace). */
    private static final int ACE = 1;
    
    /** Highest ranking card (the king). */
    private static final int KING = 13;
    
    /** 
     * Total number cards in the deck (4 suits, each with 13 cards of 
     * different ranks).
     */ 
    private static final int TOTAL_CARDS = 52;
    
    /** 
     * Some constants that define the Strings for the various suits.
     */ 
    private static final String HEARTS = "hearts";
    private static final String DIAMONDS = "diamonds";
    private static final String CLUBS = "clubs";
    private static final String SPADES = "spades";

    /**
     * Constructs a new, unshuffled deck containing 52 playing cards.
     */
    public Deck()
    {
        cards = new ArrayList<Card> ();
        buildSuit(HEARTS);
        buildSuit(DIAMONDS);
        buildSuit(CLUBS);
        buildSuit(SPADES);
    }
    
    /**
     * Creates the 13 cards for the specified suit, and adds them
     * to this deck.
     */
    private void buildSuit(String suit)
    {
        for (int i = 0; i < 13; i++){          
            cards.add(new Card(suit, i+1));
        }
    }
 
    /**
     * Shuffles this deck of cards.
     */
    public void shuffle()
    {
        Random randomizer = new Random();
        int n;
        int m;
        for (int i = 0; i < 10000; i++){
            n = randomizer.nextInt(size());
            m = randomizer.nextInt(size());
            Card temp = cards.get(n);
            cards.set(n, cards.get(m));
            cards.set(m, temp);
        }
    }
 
    /**
     * Removes a card from this deck.
     */
    public Card dealCard()
    {
        return cards.remove(0);
    }
 
    /**
     * Determines if this deck is empty.
     */
    public boolean isEmpty()
    {
        if (size() == 0){
            return true;
        }
        return false;
    }
  
    /**
     * Returns the number of cards that are currently in the deck.
     */
    public int size()
    {
        return cards.size();
    }
}
