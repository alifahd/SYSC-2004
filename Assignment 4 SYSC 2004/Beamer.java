/**
 * This class represents a beamer item which may be put
 * in a room in the game of Zuul. A beamer is a teleporter between rooms.
 * 
 * @author Ali Fahd
 * @version Nov. 9, 2019
 */
public class Beamer extends Item
{
    //variable tracks if the beamer is charged, starts off not
    boolean charged = false;
    /**
     * Constructor for objects of class Beamer.
     * 
     * @param name The name of the beamer item
     * @param description The description of the beamer item
     * @param weight The weight of the beamer item
     */
    public Beamer(String beamerName, String beamerDescription, double beamerWeight)
    {
        //makes a new beamer item
        super(beamerName, beamerDescription, beamerWeight);
    }
}
