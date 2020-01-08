
import java.util.*;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class AuctionTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class AuctionTest extends junit.framework.TestCase
{
    /**
     * Default constructor for test class AuctionTest
     */
    public AuctionTest()
    {
    }
    
    public void testEnterLot() {
        
        Auction aAuction = new Auction();
        
        assertEquals(false,aAuction.enterLot(null));
        assertEquals(true,aAuction.enterLot("Shoes"));
    }
    
    public void testGetNoBids() {
        
        Auction aAuction = new Auction();
        
        for (int i = 0; i < 4; i += 1) {
            aAuction.enterLot("Lot" + (i+1));
        }
        
        ArrayList<Lot> nullLots = aAuction.getNoBids();
        assertEquals(4,nullLots.size());
    }
    
    public void testGetLot() {

        Auction aAuction = new Auction();
        
        for (int i = 0; i < 4; i += 1) {
            aAuction.enterLot("Lot" + (i+1));
        }
        Lot testLot1 = aAuction.getLot(3);
        Lot testLot2 = aAuction.getLot(5);
        
        assertEquals(3,testLot1.getNumber());
        assertEquals(null,testLot2);
    }
    
    
    public void testParameterConstructor() {
        
        Auction aAuction1 = new Auction();
        
        for (int i = 0; i < 4; i += 1) {
            aAuction1.enterLot("Lot" + (i+1));
        }
        
        Person testPerson1 = new Person("test dummy1");
        
        boolean test1 = aAuction1.bidFor(3,testPerson1,100);
        
        Auction aAuction2 = new Auction(aAuction1);
        
        assertEquals(null,aAuction2.getLot(3));
    }
    
    public void testRemoveLot() {
        
        Auction aAuction = new Auction();
        
        for (int i = 0; i < 6; i += 1) {
            aAuction.enterLot("Lot" + (i+1));
        }
        
        assertEquals(true,aAuction.removeLot(5));
        assertEquals(null,aAuction.getLot(5));
    }
    
    public void testBidFor() {
        
        Person testPerson1 = new Person("test dummy1");
        Person testPerson2 = new Person("test dummy2");
        
        Auction aAuction = new Auction();
        
        for (int i = 0; i < 6; i += 1) {
            aAuction.enterLot("Lot" + (i+1));
        }
        
        boolean test1 = aAuction.bidFor(3,testPerson1,100);
        assertEquals(true,test1);
        
        boolean test2 = aAuction.bidFor(3,testPerson2,10);
        assertEquals(true,test2);
        
        boolean test3 = aAuction.bidFor(9,testPerson2,-10);
        assertEquals(false,test3);
        
        Lot tempLot = aAuction.getLot(3);
        assertEquals(100,tempLot.getHighestBid().getValue());
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }
}
