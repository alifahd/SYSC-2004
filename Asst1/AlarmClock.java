
/**
 * Write a description of class AlarmClock here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class AlarmClock
{
    private ClockDisplay12 displayClock;//field to access class ClockDisplay12
    private Alarm displayAlarm;////field to access class Alarm

    /**
     * Constructor for objects of class AlarmClock. This constructor creates 
     * a new clock and alarm clock both set to midnight (12:00a.m.)
     */
    public AlarmClock()
    {
        displayClock = new ClockDisplay12();
        displayAlarm = new Alarm();
    }

    /**
     * Constructor for objects of class AlarmClock. This constructor creates 
     * a new clock and alarm clock both set to the parameters inputted. Also
     * sets the alarm to on or off.
     */
    public AlarmClock(int hourClock, int minuteClock, String amPmClock,
    int hourAlarm, int minuteAlarm, String amPmAlarm, boolean alrmOn)
    {
        displayClock = new ClockDisplay12(hourClock,minuteClock,amPmClock);
        displayAlarm = new Alarm(hourAlarm,minuteAlarm,amPmAlarm, alrmOn);
    }
    
    /**
     * Set the time of the clock display to the specified hour and
     * minute. Also sets am or pm.
     */
    public void setTime(int hour, int minute, String amPm)
    {
        displayClock.setTime(hour, minute, amPm);
    }
    
    /**
     * Set the time of the alarm display to the specified hour and
     * minute. Also sets am or pm.
     */
    public void setAlarmTime(int hour, int minute, String amPm)
    {
        displayAlarm.setTime(hour, minute, amPm);
    }
    
    /**
     * This method makes the clock display go one minute forward. It ouputs
     * a message if the clock time is the same as the alarm time, then turns
     * off alarm.
     */
    public void clockTick()
    {
        displayClock.timeTick();
        if (displayClock.getTime().equals(displayAlarm.getTime())&&
        (displayClock.isItAM == displayAlarm.display.isItAM)){
            System.out.println("RING RING RING");
            unsetAlarm();
        }
    }
    
    /**
     * This method turns on the alarm.
     */
    public void setAlarm()
    {
        displayAlarm.turnOn();
    }
    
    /**
     * This method turns off the alarm.
     */
    public void unsetAlarm()
    {
        displayAlarm.turnOff();
    }
    
    /**
     * Return the current time of the clock display in the format HH:MM.
     */
    public String getTime()
    {
       return displayClock.getTime(); 
    }
    
    /**
     * Return the current time of this alarm display in the format HH:MM.
     */
    public String getAlarmTime()
    {
       return displayAlarm.getTime(); 
    }
    
    /**
     * Method returns whether or not the alarm is on/off.
     */
    public boolean isAlarmSet()
    {
       return displayAlarm.isSet(); 
    }
}
