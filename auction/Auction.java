import java.util.*;

/**
 * A simple model of an auction.
 * The auction maintains a list of lots of arbitrary length.
 *
 * @author David J. Barnes and Michael Kolling.
 * @version 2006.03.30
 *
 * @author (of AuctionSkeleton) Lynn Marshall
 * @version 2.0
 * 
 * @author <Ali Ahmad Fahd>
 * @version <Oct 5, 2019>
 * 
 */
public class Auction
{
    /** The list of Lots in this auction. */
    private ArrayList<Lot> lots;

    /** 
     * The number that will be given to the next lot entered
     * into this auction.  Every lot gets a new number, even if some lots have
     * been removed.  (For example, if lot number 3 has been deleted, we don't
     * reuse it.)
     */
    private int nextLotNumber;

    private boolean auctionOpen;
    
    /**
     * Create a new auction.
     */
    public Auction()
    {
        lots = new ArrayList<Lot>();
        nextLotNumber = 1;
        auctionOpen = true;
    }
    
    /**
     * Constructor creates a new auction starting with all the lots
     * that did not sell in the previous auction. If there was no previous
     * auction then it will act as the constructor above
     * 
     * * @param auction A previous auction.
     */
    public Auction(Auction auction)
    {
        if (auctionOpen == false){
            lots = auction.getNoBids();
            nextLotNumber = auction.nextLotNumber;
            auctionOpen = true;
        }else{
            lots = new ArrayList<Lot>();
            nextLotNumber = 1;
            auctionOpen = true;
        }
    }

    /**
     * Method enters a new lot into the auction and returns true or false
     * depending on if the method was successful in implementing the new 
     * lot.
     *
     * @param description A description of the lot.
     */
    public boolean enterLot(String description)
    {
        if ((auctionOpen == false) || (description == null)){
            return false;
        }else{
            lots.add(new Lot(nextLotNumber, description));
            nextLotNumber++;
            return true;
        }
    }

    /**
     * Method shows the full list of lots in this auction in the terminal.
     * Method checks to see if the list is empty first.
     */
    public void showLots()
    {
        if(lots.isEmpty()){
            System.out.println("\nThere are no lots.");
        }
        for(Lot lot : lots) {
            System.out.println("\n" + lot.toString());
        }
    }
    
    /**
     * Method allows bids to be made on a lot number. The method will
     * return true as long as the auction is open, the lot exists, 
     * the bidder exists, and the value is greater than 0.
     *
     * @param number The lot number being bid for.
     * @param bidder The person bidding for the lot.
     * @param value  The value of the bid.
     */
    public boolean bidFor(int lotNumber, Person bidder, long value)
    {
        Lot lot = getLot(lotNumber);
        if ((auctionOpen == false)||(lot == null)||(bidder == null)||
        (value < 0)){
            return false;
        }else if((lot.getHighestBid() != null)&&
            (value <= lot.getHighestBid().getValue())){
            System.out.println("Bid was not successful.\n" + lot); 
            return true;
        }else{
            lot.bidFor(new Bid(bidder,value));
            System.out.println("\nBid was successful.");
            System.out.println(lot + "\tBidder: " + bidder.getName());
            return true;
        }
    }


    /**
     * Method returns the lot info based on the lot number. Loops
     * through the list to find the matching lot number. If the 
     * lot does not exist it will return null.
     *
     * @param lotNumber The number of the lot to return.
     *
     * @return the Lot with the given number
     */
    public Lot getLot(int lotNumber)
    {
        for(Lot lot:lots){
            if(lot.getNumber() == lotNumber){
                return lot;
            }
        }
        return null;
    }
    
    /**
     * Method closes the auction and displays what items were sold at
     * what price and to who. It also displays if the item was sold or not.
     */
    public boolean close()
    {
        if(auctionOpen == true){
            System.out.println("\n");
            for(Lot lot: lots){
                if(lot.getHighestBid() != null){
                    System.out.println("\n" + lot + "\tBidder: " + 
                    lot.getHighestBid().getBidder().getName());
                }else{
                    System.out.print("\n" + lot + " - Did not sell");
                }
            }
            auctionOpen = false;
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * Method returns an ArrayList containing all the items that have 
     * no bids.
     * 
     * @return an ArrayList of the Lots which currently have no bids
     */
    public ArrayList<Lot> getNoBids()
    {
       ArrayList<Lot> noBids = new ArrayList();
       for(Lot lot:lots){
           if(lot.getHighestBid() == null){
               noBids.add(lot);
           }
       }
       return noBids;
    }
    
    /**
     * Method removes a specified lot with the given lot number if the 
     * auction is open and the lot has no bids. An Iterator object is used 
     * to search the list of lots and when it comes across a matching lot
     * number it removes it.
     * 
     * @param number The number of the lot to be removed.
     */
    public boolean removeLot(int number)
    {
        if((auctionOpen == false)||(getLot(number) == null)){
            return false;
        }
        Iterator<Lot> iterator = lots.iterator();
        while(iterator.hasNext()){
            Lot lot = iterator.next();
            if((lot.getNumber() == number) && (lot.getHighestBid() == null)){
                iterator.remove();
                return true;
            }
        }
        return false;
    }
}