/**
 * The ClockDisplay class implements a digital clock display for a
 * 12 hour clock. The clock shows hours and minutes. The 
 * range of the clock is 12:00a.m. (midnight) to 11:59p.m. (one minute before 
 * midnight).
 * 
 * The clock display receives "ticks" (via the timeTick method) every minute
 * and reacts by incrementing the display. This is done in the usual clock
 * fashion: the hour increments when the minutes roll over to zero.
 * 
 * @author Michael Kölling and David J. Barnes
 * @version 2016.02.29
 * @author Ali Ahmad Fahd
 * @version 2019.09.21
 */
public class ClockDisplay12
{
    private NumberDisplay hours;
    private NumberDisplay minutes;
    private String displayString;    // simulates the actual display
    public static final String AM = "a.m.";
    public static final String PM = "p.m.";
    boolean isItAM = true;  //AM if true, PM if false
    
    /**
     * Constructor for ClockDisplay objects. This constructor 
     * creates a new clock set at 12:00a.m.
     */
    public ClockDisplay12()
    {
        hours = new NumberDisplay(12);
        minutes = new NumberDisplay(60);
        updateDisplay();
    }

    /**
     * Constructor for ClockDisplay objects. This constructor
     * creates a new clock set at the time specified by the 
     * parameters.
     */
    public ClockDisplay12(int hour, int minute, String amPm)
    {
        hours = new NumberDisplay(12);
        minutes = new NumberDisplay(60);
        setTime(hour, minute, amPm);
    }

    /**
     * This method should get called once every minute - it makes
     * the clock display go one minute forward.
     */
    public void timeTick()
    {
        minutes.increment();
        if(minutes.getValue() == 0) {  // it just rolled over!
            hours.increment();
        }
        if(hours.getValue() == 0) {  // it just rolled over!
            if (isItAM == true){
                isItAM = false;
            }
            else{
                isItAM = true;
            }
        }
        updateDisplay();
    }

    /**
     * Set the time of the display to the specified hour and
     * minute. Also sets am or pm.
     */
    public void setTime(int hour, int minute, String amPm)
    {
        hours.setValue(hour);
        minutes.setValue(minute);
        if (amPm.equals(PM)){
            isItAM = false;
        }
        else{
            isItAM = true;
        }
        updateDisplay();
    }

    /**
     * Return the current time of this display in the format HH:MM.
     */
    public String getTime()
    {
        return displayString;
    }
    
    /**
     * Update the internal string that represents the display.
     */
    private void updateDisplay()
    {
        if (isItAM == true){
            if (hours.getValue() == 0){
                displayString = "12:" + 
                            minutes.getDisplayValue()+AM;
            }
            else{
                displayString = hours.getValue() + ":" + 
                            minutes.getDisplayValue()+AM;
            }
        }
        
        else{
            if (hours.getValue() == 0){
                displayString = "12:" + 
                            minutes.getDisplayValue()+PM;
            }
            else{
                displayString = hours.getValue() + ":" + 
                            minutes.getDisplayValue()+PM;
            }
        }
    }
}
