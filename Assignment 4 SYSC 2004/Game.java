import java.util.Stack;
import java.util.ArrayList; // or java.util.*; and replace the above
/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kolling and David J. Barnes 
 * @version 2006.03.30
 * 
 * @author Lynn Marshall
 * @version A3 Solution
 * 
 * @author Ali Fahd
 * @version Nov. 9, 2019
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Room previousRoom;
    private Room chargedRoom;   //holds the room in which the beamer will teleport back to
    private TransporterRoom transportRoom;  //room for in between the tranportation
    private Beamer beamer;  //beamer object
    private Stack<Room> previousRoomStack;
    private Item itemHeld;  //holds the item currently held by the player
    private int takeCounter = -1;   //counter counts how many times the user has picked up an item
        
    /**
     * Create the game and initialise its internal map, as well
     * as the previous room (none) and previous room stack (empty).
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        chargedRoom = null; //defaults to no room
        previousRoom = null;
        previousRoomStack = new Stack<Room>();
        itemHeld = null;    //defaults to nothing
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, theatre, pub, lab, office;
        Item chair, bar, computer, computer2, tree, cookie;
        
        // create some items
        chair = new Item("chair", "a wooden chair",5);
        bar = new Item("bar","a long bar with stools",95.67);
        computer = new Item("PC","a PC",10);
        computer2 = new Item("Mac","a Mac",5);
        tree = new Item("tree","a fir tree",500.5);
        cookie = new Item("cookie", "chocolate chip cookie", 0.5);
        beamer = new Beamer("beamer", "a beamer", 10);
       
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theatre = new Room("in a lecture theatre");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        transportRoom = new TransporterRoom("transport room");
        
        //add the created rooms to an array list in the transportRoom object
        transportRoom.addRoom(outside);
        transportRoom.addRoom(theatre);
        transportRoom.addRoom(pub);
        transportRoom.addRoom(lab);
        transportRoom.addRoom(office);
        
        // put items in the rooms
        outside.addItem(tree);
        outside.addItem(tree);
        outside.addItem(beamer);
        theatre.addItem(chair);
        theatre.addItem(cookie);
        pub.addItem(bar);
        pub.addItem(cookie);
        lab.addItem(chair);
        lab.addItem(computer);
        lab.addItem(chair);
        lab.addItem(computer2);
        lab.addItem(beamer);
        office.addItem(chair);
        office.addItem(computer);
        office.addItem(cookie);        
        
        // initialise room exits
        outside.setExit("east", theatre); 
        outside.setExit("south", lab);
        outside.setExit("west", pub);

        theatre.setExit("west", outside);

        pub.setExit("east", outside);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);

        currentRoom = outside;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
        if (itemHeld == null){
           System.out.println("You are not holding anything.");
        }else{
           System.out.println("You are holding a " + itemHeld.getName() + ".");
        }
    }

    /**
     * Given a command, process (that is: execute) the command.
     * 
     * @param command The command to be processed
     * @return true If the command ends the game, false otherwise
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("look")) {
            look(command);
        }
        else if (commandWord.equals("eat")) {
            eat(command);
        }
        else if (commandWord.equals("back")) {
            back(command);
        }
        else if (commandWord.equals("stackBack")) {
            stackBack(command);
        }
        else if (commandWord.equals("take")) {
            take(command);
        }
        else if (commandWord.equals("drop")) {
            drop(command);
        }
        else if (commandWord.equals("charge")) {
            charge(command);
        }
        else if (commandWord.equals("fire")) {
            fire(command);
        }
        // else command not recognised.
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print a cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println(parser.getCommands());
    }

    /** 
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     * If we go to a new room, update previous room and previous room stack.
     * 
     * @param command The command to be processed
     */
    private void goRoom(Command command) 
    {
        //does just the "go" command, ie the teleportation
        if(!command.hasSecondWord()) {
            previousRoom = currentRoom; // store the previous room
            previousRoomStack.push(currentRoom); // and add to previous room stack
            //loops the random rooms until it lands on a different one from the current room
            do{
                currentRoom = transportRoom.getExit("Doesn't matter");
            }while(previousRoom.equals(currentRoom));
            //gets a description of the new room
            System.out.println(currentRoom.getLongDescription());
            if (itemHeld == null){
                System.out.println("You are not holding anything.");
            }else{
                System.out.println("You are holding a " + itemHeld.getName() + ".");
            }
        //does the go in a specific direction command
        }else{
    
            String direction = command.getSecondWord();
    
            // Try to leave current room.
            Room nextRoom = currentRoom.getExit(direction);
    
            if (nextRoom == null) {
                System.out.println("There is no door!");
            }
            else {
                previousRoom = currentRoom; // store the previous room
                previousRoomStack.push(currentRoom); // and add to previous room stack
                currentRoom = nextRoom;
                //outputs a description of the new room
                System.out.println(currentRoom.getLongDescription());
                if (itemHeld == null){
                    System.out.println("You are not holding anything.");
                }else{
                    System.out.println("You are holding a " + itemHeld.getName() + ".");
                }
            }
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * 
     * @param command The command to be processed
     * @return true, if this command quits the game, false otherwise
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
    /** 
     * "Look" was entered. Check the rest of the command to see
     * whether we really want to look.
     * 
     * @param command The command to be processed
     */
    private void look(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Look what?");
        }
        else {
            // output the long description of this room and the held item if any
            System.out.println(currentRoom.getLongDescription());
            if (itemHeld == null){
                System.out.println("You are not holding anything.");
            }else{
                System.out.println("You are holding a " + itemHeld.getName() + ".");
            }
        }
    }
    
    /** 
     * "Eat" was entered. Check the rest of the command to see
     * whether we really want to eat. Method removes the cookie from the 
     * game when eaten. NOTE: !!!Didn't know if you wanted unlimited 
     * cookies in the game so technically the player won't be able to 
     * pick up anything eventually when there is no food left in the 
     * game!!!
     * 
     * @param command The command to be processed
     */
    private void eat(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Eat what?");
        }else{
            if ((itemHeld == null) || (!itemHeld.getName().equals("cookie"))){
                System.out.println("You have no food to eat!");
            }else {
                // output that we have eaten
                System.out.println("You have eaten the cookie and are no longer hungry!");
                //user can now pick up other items if they could not previously
                takeCounter = 0;
                itemHeld = null;
            }
        }
    }
    
    /** 
     * "Back" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * 
     * @param command The command to be processed
     */
    private void back(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Back what?");
        }
        else {
            // go back to the previous room, if possible
            if (previousRoom==null) {
                System.out.println("No room to go back to.");
            } else {
                // go back and swap previous and current rooms,
                // and put current room on previous room stack
                Room temp = currentRoom;
                currentRoom = previousRoom;
                previousRoom = temp;
                previousRoomStack.push(temp);
                // and print description
                System.out.println(currentRoom.getLongDescription());
                if (itemHeld == null){
                    System.out.println("You are not holding anything.");
                }else{
                    System.out.println("You are holding a " + itemHeld.getName() + ".");
                }
            }
        }
    }
    
    /** 
     * "StackBack" was entered. Check the rest of the command to see
     * whether we really want to stackBack.
     * 
     * @param command The command to be processed
     */
    private void stackBack(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("StackBack what?");
        }
        else {
            // step back one room in our stack of rooms history, if possible
            if (previousRoomStack.isEmpty()) {
                System.out.println("No room to go stack back to.");
            } else {
                // current room becomes previous room, and
                // current room is taken from the top of the stack
                previousRoom = currentRoom;
                currentRoom = previousRoomStack.pop();
                // and print description
                System.out.println(currentRoom.getLongDescription());
                if (itemHeld == null){
                    System.out.println("You are not holding anything.");
                }else{
                    System.out.println("You are holding a " + itemHeld.getName() + ".");
                }
            }
        }
    }
    
    /** 
     * "Take..." was enetered and the method will check if the user
     * is eligible to pick up the desired item. Increases the counter
     * for items picked up as well.
     * 
     * @param command The command to be processed
     */
    private void take(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to take...
            System.out.println("Take what?");
        }else{
            //check to see if the player isn't already holding an item
            if(itemHeld == null){
                //Creates an array list of items in the room
                ArrayList<Item> arr = currentRoom.getItemsList();
                //loops through them to see if any items match
                for (int i = 0; i < arr.size(); i++){
                    if (arr.get(i).getName().equals(command.getSecondWord())){
                        itemHeld = arr.get(i);
                        currentRoom.removeItem(itemHeld);
                        i = arr.size();
                    }
                }
                //if the item doesn't match
                if (itemHeld == null){
                    System.out.println("That item is not in the room!");
                //if the item does match
                }else{
                    //make sure player has not exceeded the 5 pick up limit
                    if ((takeCounter < 6) && (takeCounter > -1)){
                        System.out.println("You have picked up a " + itemHeld.getName() + ".");
                        //won't count the cookie as a pick up turn since you need to eat first in order to pick up other items
                        if (!itemHeld.getName().equals("cookie")){
                            takeCounter++;  //adds 1 to counter
                            if (takeCounter == 5){
                               takeCounter = -1;   //sets counter back to -1 if they've picked up 5 items and they won't be able to pick up again untill they eat
                            }
                        }
                     //if the item picked is a cookie (won't affect counter)
                    }else if(itemHeld.getName().equals("cookie")){
                        System.out.println("You have picked up a " + itemHeld.getName() + ".");
                    //user needs to pick up and eat a cookie first before they can pick up anything else
                    }else{
                        //adds the item back to the room because they can't take it
                        currentRoom.addItem(itemHeld);
                        itemHeld = null;
                        System.out.println("You are too hungry to pick up anything. Why don't you try eating something first to get your energy up!");
                    }
                }
            }
            else{
                System.out.println("You are already holding an item!");
            }
        }
    }
    
    /** 
     * "Drop" was entered. Checks to see if the player has an item
     * an then drops it.
     * 
     * @param command The command to be processed
     */
    private void drop(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Drop what?");
        }else{
            if (itemHeld == null){
                // output that there is no item
                System.out.println("You have nothing to drop.");
            }
            else {
                //if a person drops a beamer the charge goes away
                if (itemHeld.getName().equals("beamer")){
                    chargedRoom = null;
                }
                //outputs what the player has dropped and resets the string variable
                System.out.println("You have dropped a " + itemHeld.getName() + ".");
                currentRoom.addItem(itemHeld);
                itemHeld = null;
            }
        }
    }
    
    /** 
     * "Charge" was entered. Checks to see if the player is holding a beamer
     * and will then charge the beamer with the current room if not already
     * charged.
     * 
     * @param command The command to be processed
     */
    private void charge(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Charge what?");
        }else{
            //checks if player is holding a beamer
            if ((itemHeld == null)||(!itemHeld.getName().equals("beamer"))){
                System.out.println("You are not holding a beamer!");
            //if player does have a beamer
            }else{
                //checks to make sure it's not already charged
                if (chargedRoom == null){
                    //sets the charged room to the current room player is in
                    chargedRoom = currentRoom;
                    //lets the user know the beamer is charged
                    System.out.println("The beamer has been charged with " + currentRoom.getShortDescription());
                }else{
                    System.out.println("The beamer is already charged!");
                }
            }
        }
    }
    
    /** 
     * "Fire" was entered. Checks to see if the player is holding a beamer
     * and will then fire the beamer and go back to the room it was charged
     * in.
     * 
     * @param command The command to be processed
     */
    private void fire(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Fire what?");
        }else{
            //checks if player is holding a beamer
            if ((itemHeld == null)||(!itemHeld.getName().equals("beamer"))){
                System.out.println("You are not holding a beamer!");
            //checks if beamer is charged
            }else if (chargedRoom == null){
                System.out.println("The beamer is not charged!");
            //fires beamer
            }else{
                previousRoom = currentRoom; // store the previous room
                previousRoomStack.push(currentRoom); // and add to previous room stack
                currentRoom = chargedRoom;  //sends player to room beamer was charged in
                //outputs description of new room
                System.out.println(currentRoom.getLongDescription());
                if (itemHeld == null){
                    System.out.println("You are not holding anything.");
                }else{
                    System.out.println("You are holding a " + itemHeld.getName() + ".");
                }
                chargedRoom = null; //beamer has not charged in a room
            }
        }
    }
}
