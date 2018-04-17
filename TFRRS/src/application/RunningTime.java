package application;

import java.util.ArrayList;
import java.util.Collections;

public class RunningTime
{
	Integer hour;
	Integer minute;
	Float second;

	public RunningTime(String time)
	{
		ArrayList<String> timeArr = new ArrayList<String>();
		Collections.addAll(timeArr, time.split(":"));
		switch (timeArr.size()) {
		case 2:
			this.second = Float.parseFloat(timeArr.get(0) + "." + timeArr.get(1));
			this.minute = 0;
			this.hour = 0;
			break;
		case 3:
			this.second = Float.parseFloat(timeArr.get(1) + "." + timeArr.get(2));
			this.minute = Integer.parseInt(timeArr.get(0));
			this.hour = 0;
			break;
		case 4:
			this.second = Float.parseFloat(timeArr.get(2) + "." + timeArr.get(3));
			this.minute = Integer.parseInt(timeArr.get(1));
			this.hour = Integer.parseInt(timeArr.get(0));
			break;
		}
	}

	/**
	 * @return the hour
	 */
	public int getHour()
	{
		return hour;
	}

	/**
	 * @param hour
	 *            the hour to set
	 */
	public void setHour(int hour)
	{
		this.hour = hour;
	}

	/**
	 * @return the minute
	 */
	public int getMinute()
	{
		return minute;
	}

	/**
	 * @param minute
	 *            the minute to set
	 */
	public void setMinute(int minute)
	{
		this.minute = minute;
	}

	/**
	 * @return the second
	 */
	public float getSecond()
	{
		return second;
	}

	/**
	 * @param second
	 *            the second to set
	 */
	public void setSecond(float second)
	{
		this.second = second;
	}

	@Override
	public String toString()
	{
		String h;
		String m;
		String s;
		if (hour != null)
		{
			if (hour == 0)
			{
				h = "";
			} else if (hour >= 10)
			{
				h = hour.toString();
			} else
			{
				h = "0" + hour.toString();
			}
			if (minute == 0)
			{
				m = "";
			} else if (minute >= 10)
			{
				m = minute.toString();
			} else
			{
				m = "0" + minute.toString();
			}
			if (second == 0)
			{
				s = "";
			} else if (second <= 10)
			{
				s = "0" + second.toString();
			} else
			{
				s = second.toString();
			}

			if (h.equals(""))
			{
				if (m.equals("") && s.equals(""))
				{
					return "Did not finish";
				} else if (m.equals(""))
				{
					return s;
				} else
				{
					return m + ":" + s;
				}

			} else
			{
				return h + ":" + m + ":" + s;
			}
		}
		else
		{
			return "No Time";
		}
	}

}
