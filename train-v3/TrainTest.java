import java.util.ArrayList;

/**
 * The test class TrainTest.
 *
 * @author  Lynn Marshall
 * @version May 2015
 */
public class TrainTest extends junit.framework.TestCase
{
    private Car car1, car2, car3, car4;
    private Train aTrain;
    /**
     * Default constructor for test class TrainTest
     */
    public TrainTest()
    {
        aTrain = new Train();
        
        car1 = new Car(1250, true);
        aTrain.addCar(car1);
        
        car2 = new Car(1300, false);
        aTrain.addCar(car2);
        
        car3 = new Car(1740, false);
        aTrain.addCar(car3);
        
        car4 = new Car(1890, true);
        aTrain.addCar(car4);
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    protected void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    protected void tearDown()
    {
    }
 
    public void testCreateEmptyTrain()
    {
        Train emptyTrain = new Train();
        
        /* Verify that a new train has no cars. */
        assertEquals(0, emptyTrain.cars().size());
    }
    
    public void testAddCar()
    {
        ArrayList<Car> cars = aTrain.cars();
        
        car4 = cars.get(0);
        
        /* Important - assertSame() does not compare the Car objects 
         * referred to by car1 and aCar to detemine if they are equal
         * (have the same state). It verifies that car1 an aCar refer to
         * the same object; i.e., that the Car (reference) retrieved by get(0)
         * is the first first that was added to the ArrayList.
         */
        assertSame(car1, car4);
        
        car4 = cars.get(1);
        assertSame(car2, car4);
        
        car4 = cars.get(2);
        assertSame(car3, car4);      
    }
        
    public void testIssueTicket()
    {        
        /* Book all the seats in the business-class car. */
        for (int i = 0; i <Car.BUSINESS_SEATS; i++) {
            assertTrue(aTrain.issueTicket(true));
        }
        for (int i = 0; i <Car.BUSINESS_SEATS; i++) {
            assertTrue(aTrain.issueTicket(true));
        }
        
        /* Attempt to book one more business-class seat, even
         * though they are all booked.
         */
        assertFalse(aTrain.issueTicket(true));        
 
        ArrayList<Car> cars = aTrain.cars();
        
        for (int i = 0; i < Car.BUSINESS_SEATS; i++) {
            assertTrue(cars.get(0).seats()[i].isBooked());
        }
        
        for (int i = 0; i < Car.ECONOMY_SEATS; i++) {
            assertFalse(cars.get(1).seats()[i].isBooked());
        }
        
        for (int i = 0; i < Car.ECONOMY_SEATS; i++) {
            assertFalse(cars.get(2).seats()[i].isBooked());
        }
        
        for (int i = 0; i < Car.BUSINESS_SEATS; i++) {
            assertTrue(cars.get(3).seats()[i].isBooked());
        }
        
        /* Book all the seats in the first economy-class car. */
        for (int i = 0; i <Car.ECONOMY_SEATS; i++) {
            assertTrue(aTrain.issueTicket(false));
        }
        
        cars = aTrain.cars();
        
        for (int i = 0; i < Car.BUSINESS_SEATS; i++) {
            assertTrue(cars.get(0).seats()[i].isBooked());
        }
        
        for (int i = 0; i < Car.ECONOMY_SEATS; i++) {
            assertTrue(cars.get(1).seats()[i].isBooked());
        }
        
        for (int i = 0; i < Car.ECONOMY_SEATS; i++) {
            assertFalse(cars.get(2).seats()[i].isBooked());
        }  
        
        for (int i = 0; i < Car.BUSINESS_SEATS; i++) {
            assertTrue(cars.get(3).seats()[i].isBooked());
        } 
        
        /* Book all the seats in the second economy-class car. */
        for (int i = 0; i <Car.ECONOMY_SEATS; i++) {
            assertTrue(aTrain.issueTicket(false));
        }
        
        /* check that all seats are now booked */
        for (int i = 0; i < Car.BUSINESS_SEATS; i++) {
            assertTrue(cars.get(0).seats()[i].isBooked());
        }
        
        for (int i = 0; i < Car.ECONOMY_SEATS; i++) {
            assertTrue(cars.get(1).seats()[i].isBooked());
        }
        
        for (int i = 0; i < Car.ECONOMY_SEATS; i++) {
            assertTrue(cars.get(2).seats()[i].isBooked());
        }
        
        for (int i = 0; i < Car.BUSINESS_SEATS; i++) {
            assertTrue(cars.get(3).seats()[i].isBooked());
        }
        
        /* Try to book another business class seat (fails)*/
        assertFalse(aTrain.issueTicket(true));
        /* Try to book another economy class seat (fails)*/
        assertFalse(aTrain.issueTicket(false));
    }
    
    public void testCancelTicket()
    {
        /* Book all the seats in the business-class car. */
        for (int i = 0; i <Car.BUSINESS_SEATS; i++) {
            assertTrue(aTrain.issueTicket(true));
        }
        
        for (int i = 0; i <(Car.BUSINESS_SEATS)/2; i++) {
            assertTrue(aTrain.issueTicket(true));
        }
        
        /* Book all the seats in the first economy-class car. */
        for (int i = 0; i <Car.ECONOMY_SEATS; i++) {
            assertTrue(aTrain.issueTicket(false));
        }
        
        ArrayList<Car> cars = aTrain.cars();
        
        assertTrue(aTrain.cancelTicket(1300, 4));
        assertFalse(cars.get(1).seats()[3].isBooked());
        
        assertTrue(aTrain.cancelTicket(1890, 4));
        assertFalse(cars.get(3).seats()[3].isBooked()); 
        
        /* Cancel ticket in a non-existent car. */
        assertFalse(aTrain.cancelTicket(1500, 7));
        
        /* Cancel ticket in a non-existent seat. */
        assertFalse(aTrain.cancelTicket(1300, 54));
        
        /* Cancel ticket for a seat that hasn't been booked. */
        assertFalse(aTrain.cancelTicket(1740, 21));
        assertFalse(cars.get(2).seats()[20].isBooked());
        
        assertFalse(aTrain.cancelTicket(1890, 21));
        assertFalse(cars.get(3).seats()[20].isBooked());
        
        /* Attempt to cancel the same ticket twice. */
        assertTrue(aTrain.cancelTicket(1250, 11));
        assertFalse(cars.get(0).seats()[10].isBooked());
        
        assertFalse(aTrain.cancelTicket(1250, 11));   
        assertFalse(cars.get(0).seats()[10].isBooked());         
    }
    
    public void testBookCancelTicket()
    {        
        ArrayList<Car> cars = aTrain.cars();
        assertEquals(4, cars.size());
        
        for (int i = 0; i <Car.BUSINESS_SEATS; i++) {
            assertTrue(aTrain.issueTicket(true));
        }
        
        for (int i = 0; i <(Car.BUSINESS_SEATS)/2; i++) {
            assertTrue(aTrain.issueTicket(true));
        }
        
        /* Book all the seats in the first economy-class car. */
        for (int i = 0; i <Car.ECONOMY_SEATS; i++) {
            assertTrue(aTrain.issueTicket(false));
        }
        
        //cancel three economy ticketes
        assertTrue(aTrain.cancelTicket(1300, 4));
        assertFalse(cars.get(1).seats()[3].isBooked());
        assertTrue(aTrain.cancelTicket(1300, 7));
        assertFalse(cars.get(1).seats()[6].isBooked());
        assertTrue(aTrain.cancelTicket(1300, 9));
        assertFalse(cars.get(1).seats()[8].isBooked());
        
        //book four economy tickets
        assertTrue(aTrain.issueTicket(false));
        assertTrue(cars.get(1).seats()[3].isBooked());
        assertTrue(aTrain.issueTicket(false));
        assertTrue(cars.get(1).seats()[6].isBooked());
        assertTrue(aTrain.issueTicket(false));
        assertTrue(cars.get(1).seats()[8].isBooked());
        assertTrue(aTrain.issueTicket(false));
        assertTrue(cars.get(2).seats()[0].isBooked());
        
        //cancel three business tickets
        assertTrue(aTrain.cancelTicket(1250, 4));
        assertFalse(cars.get(0).seats()[3].isBooked());
        assertTrue(aTrain.cancelTicket(1890, 7));
        assertFalse(cars.get(3).seats()[6].isBooked());
        assertTrue(aTrain.cancelTicket(1890, 9));
        assertFalse(cars.get(3).seats()[8].isBooked());
        
        //book four business tickets
        assertTrue(aTrain.issueTicket(true));
        assertTrue(cars.get(0).seats()[3].isBooked());
        assertTrue(aTrain.issueTicket(true));
        assertTrue(cars.get(3).seats()[6].isBooked());
        assertTrue(aTrain.issueTicket(true));
        assertTrue(cars.get(3).seats()[8].isBooked());
        assertTrue(aTrain.issueTicket(true));
        assertTrue(cars.get(3).seats()[15].isBooked());
    }
}
