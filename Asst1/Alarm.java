
/**
 * The Alarm class implements a digital clock display for a
 * 12 hour clock that will act as teh Alarm time instead of the regular time.
 * The clock shows hours and minutes. The range of the clock is 12:00a.m. 
 * (midnight) to 11:59p.m. (one minute before midnight).
 * 
 * @author Ali Ahmad Fahd
 * @version 2019.09.21
 */



public class Alarm
{
    boolean alarmSet = false;   //determines if alarm is on or off
    public ClockDisplay12 display;  //field to access class ClockDisplay12
    
    /**
     * Constructor for objects of class Alarm. This constructor 
     * creates a new alarm clock set at 12:00a.m. and sets the alarm to 
     * turned off.
     */
    public Alarm()
    {
        display = new ClockDisplay12();
        alarmSet = false;
    }

    /**
     * Constructor for Alarm objects. This constructor
     * creates a new alarm clock set at the time specified by the 
     * parameters and turns the alarm on or off
     */
    public Alarm(int hour, int minute, String amPm, boolean alrmOn)
    {
        display = new ClockDisplay12(hour,minute,amPm);
        alarmSet = alrmOn;
    }
    
    /**
     * Set the time of the alarm display to the specified hour and
     * minute. Also sets am or pm.
     */
    public void setTime(int hour, int minute, String amPm)
    {
        display.setTime(hour, minute, amPm);
    }
    
    /**
     * Return the current time of this alarm display in the format HH:MM.
     */
    public String getTime()
    {
       return display.getTime(); 
    }
    
    /**
     * Method turns the alarm on.
     */
    public void turnOn()
    {
        alarmSet = true;
    }
    
    /**
     * Method turns the alarm off.
     */
    public void turnOff()
    {
        alarmSet = false;
    }
    
    /**
     * Method returns whether or not the alarm is on/off.
     */
    public boolean isSet()
    {
        return alarmSet;
    }
}
