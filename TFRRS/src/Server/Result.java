package Server;

import java.io.Serializable;
import java.util.Date;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import application.*;

public class Result implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String event;
    private String meet;
    private String inOrOut;
    private RunningTime time;
    private Float mark;
    private LocalDate when;

    public Result()
    {
    }

    /// for field events
    public Result(String event, String meet, String inOrOut, Float mark, LocalDate when)
    {
	this.event = event;
	this.meet = meet;
	this.inOrOut = inOrOut;
	this.mark = mark;
	this.when = when;
	this.time = null;
    }

    //// for running events
    public Result(String event, String meet, String inOrOut, RunningTime time, LocalDate when)
    {
	this.event = event;
	this.meet = meet;
	this.time = time;
	this.inOrOut = inOrOut;
	this.when = when;
	this.mark = (float) 0;

    }

    public String getMeet()
    {
	return meet;
    }

    public void setMeet(String meet)
    {
	this.meet = meet;
    }

    /**
     * @return the inOrOut
     */
    public String getInOrOut()
    {
	return inOrOut;
    }

    /**
     * @param inOrOut
     *            the inOrOut to set
     */
    public void setInOrOut(String inOrOut)
    {
	this.inOrOut = inOrOut;
    }

    /**
     * @return the event
     */
    public String getEvent()
    {
	return event;
    }

    /**
     * @param event
     *            the event to set
     */
    public void setEvent(String event)
    {
	this.event = event;
    }

    /**
     * @return the time
     */
    public RunningTime getTime()
    {
	return time;
    }

    /**
     * @param time
     *            the time to set
     */
    public void setTime(RunningTime time)
    {
	this.time = time;
    }

    /**
     * @return the mark
     */
    public float getMark()
    {
	return mark;
    }

    /**
     * @param mark
     *            the mark to set
     */
    public void setMark(float mark)
    {
	this.mark = mark;
    }

    /**
     * @return the when
     */
    public LocalDate getWhen()
    {
	return when;
    }

    /**
     * @param when
     *            the when to set
     */
    public void setWhen(LocalDate when)
    {
	this.when = when;
    }
}
