/**
 * The test class CarTest.
 *
 * @author  Lynn Marshall, SCE
 * @version 1.2 May 1st, 2015
 */
public class CarTest extends junit.framework.TestCase
{
    /**
     * Default constructor for test class CarTest
     */
    public CarTest()
    {
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
    
    public void testCreateBusinessCar()
    {
        Car aCar = new Car(1385, true);
        Seat[] seats = aCar.seats();
        
        /* Verify that the car has the right number of seats. */
        assertEquals(Car.BUSINESS_SEATS, seats.length);
        
        /* Verify that each seat has the correct number and price. */
        for (int i = 0; i < seats.length; i++) {
            assertEquals(i+1, seats[i].number());
            assertEquals(Car.BUSINESS_SEAT_COST, seats[i].price());
        }
    }
    
    public void testCreateEconomyCar()
    {
        Car aCar = new Car(1400, false);
        Seat[] seats = aCar.seats();
        
        /* Verify that the car has the right number of seats. */        
        assertEquals(Car.ECONOMY_SEATS, seats.length);
        
        /* Verify that each seat has the correct number and price. */       
        for (int i = 0; i < seats.length; i++) {
            assertEquals(i+1, seats[i].number());
            assertEquals(Car.ECONOMY_SEAT_COST, seats[i].price());
        }
    }    
    
    public void testID()
    {
         Car aCar;
         aCar= new Car(1385, true);
         assertEquals(1385, aCar.id());
         aCar = new Car(1400, false);
         assertEquals(1400, aCar.id());
    }
    
    public void testIsBusinessClass()
    {
         Car aCar;
         aCar = new Car(1385, true);
         assertTrue(aCar.isBusinessClass());
         aCar = new Car(1400, false);
         assertFalse(aCar.isBusinessClass()); 
    }
    
    public void testBookNextSeat()
    {
        Car aCar, anotherCar;
        aCar = new Car(1234, true);
        anotherCar = new Car(5678, false);
        
        Seat[] seats = aCar.seats();
        Seat[] anotherSeats = anotherCar.seats();
        
        /* Verify that no seats are booked. */
        for (int i = 0; i < seats.length; i++) {
            assertFalse(seats[i].isBooked());
        }
        
        for (int i = 0; i < anotherSeats.length; i++) {
            assertFalse(anotherSeats[i].isBooked());
        } 
        
        /* Verify that the seats are booked consecutively,
         * starting with Seat #1.
         */
        for (int i = 0; i < seats.length; i++) {
            seats = aCar.seats();
            assertFalse(seats[i].isBooked()); // not booked
            assertTrue(aCar.bookNextSeat()); // book it
            assertTrue(seats[i].isBooked()); // now booked
            if (i!=seats.length-1) {
                assertFalse(seats[i+1].isBooked()); // but next isn't
            }
        }
        
        for (int i = 0; i < anotherSeats.length; i++) {
            anotherSeats = anotherCar.seats();
            assertFalse(anotherSeats[i].isBooked()); // not booked
            assertTrue(anotherCar.bookNextSeat()); // book it
            assertTrue(anotherSeats[i].isBooked()); // now booked
            if (i!=anotherSeats.length-1) {
                assertFalse(anotherSeats[i+1].isBooked()); // but next isn't
            }
        }
        
        /* Try to book a seat now that all the seats have been booked. */
        assertFalse(aCar.bookNextSeat());
        assertFalse(anotherCar.bookNextSeat());
    }
    
    public void testCancelSeat()
    {
        Car aCar, anotherCar;
        aCar = new Car(1234, true);
        anotherCar = new Car(5678, false);
        
        Seat[] seats = aCar.seats();
        Seat[] anotherSeats = anotherCar.seats();
        
        /* Cancel seat 0. cancelSeat() should return false, as there
         * is no seat 0.
         */
        assertFalse(aCar.cancelSeat(0));
        assertFalse(anotherCar.cancelSeat(0));
        /* Try cancelling a seat whose number is one higher than 
         * the highest valid seat number (seats.length - 1). 
         * cancelSeat() should return false.
         */
        assertFalse(aCar.cancelSeat(seats.length));
        assertFalse(anotherCar.cancelSeat(anotherSeats.length));
        
        /* Try cancelling all the seats in the car, even though 
         * they haven't been booked. cancelSeat() should 
         * return false.
         */
        for (int i = 0; i < seats.length; i++) {
            assertFalse(aCar.cancelSeat(i+1));
        }
        for (int i = 0; i < anotherSeats.length; i++) {
            assertFalse(anotherCar.cancelSeat(i+1));
        }
        
        /* Book all the seats */
        for (int i = 0; i < seats.length; i++) {
            aCar.bookNextSeat();
        }
        for (int i = 0; i < anotherSeats.length; i++) {
            anotherCar.bookNextSeat();
        }
        
        /* Try cancelling all the seats in the car.
         */
        for (int i = 0; i < seats.length; i++) {
            assertTrue(aCar.cancelSeat(i+1));
        }
        for (int i = 0; i < anotherSeats.length; i++) {
            assertTrue(anotherCar.cancelSeat(i+1));
        } 
        
        /* In case seat numbers are off, try some more tests.
         */
        Car bCar,otherCar;
        bCar = new Car (4321,false);
        otherCar = new Car (8765,true);
        // book 2 seats
        assertTrue(bCar.bookNextSeat());
        assertTrue(bCar.bookNextSeat());
        
        assertTrue(otherCar.bookNextSeat());
        assertTrue(otherCar.bookNextSeat());
        
        // try to cancel the 3rd (not booked)
        assertFalse(bCar.cancelSeat(3));
        assertFalse(otherCar.cancelSeat(3));
        
        // try to cancel the 50th (not booked)
        assertFalse(bCar.cancelSeat(50));
        assertFalse(otherCar.cancelSeat(50));
        
        // cancel the 1st and 2nd (were both booked)
        assertTrue(bCar.cancelSeat(1));
        assertTrue(bCar.cancelSeat(2));
        
        assertTrue(otherCar.cancelSeat(1));
        assertTrue(otherCar.cancelSeat(2));
    }      
}
