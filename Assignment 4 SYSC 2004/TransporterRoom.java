import java.util.ArrayList;
import java.util.Random;
/**
 * Class TransporterRoom - an in-between room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Transporter Room" represents a place where a random room is chosen
 * for a player to go to.
 *
 * @author Ali Fahd
 * @version Nov. 9, 2019
 */
public class TransporterRoom extends Room
{
    //creates an array list to hold all rooms created
    private ArrayList<Room> rooms = new ArrayList<Room>();
    /**
     * Create a transporter room described "description". It has no exits,
     * it is just a room to decide which room to teleport the player too.
     * 
     * @param description The room's description.
     */
    public TransporterRoom(String description)
    {
        //creates the room
        super(description);
    }
    
    /**
     * Method addRoom just adds a created room in the game to the field rooms
     *
     * @param room - a room created in the game
     */
     public void addRoom(Room room)
     {
         //adds the room created to the array list rooms
         rooms.add(room);
     }
     
    /**
     * Returns a random room, independent of the direction parameter.
     *
     * @param direction Ignored.
     * @return A randomly selected room.
     */
     public Room getExit(String direction)
     {
         return findRandomRoom();
     }
    
    /**
     * Choose a random room.
     *
     * @return The room we end up in upon leaving this one.
     */
     private Room findRandomRoom()
     {
         //creates a random number generator
         Random r = new Random();
         //returns a random room by choosing a random index from among the array list of rooms
         return rooms.get(r.nextInt(rooms.size()));
     } 
}
